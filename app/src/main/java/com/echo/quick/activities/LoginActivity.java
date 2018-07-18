package com.echo.quick.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.quick.utils.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文件名：LoginActivity
 * 创建人：周少侠
 * 创建时间：2018/7/17 15:02
 * 类描述：移植于最后一哩项目
 * 
 * 修改人：
 * 修改时间：
 * 修改内容：
 * 
**/

public class LoginActivity extends AppCompatActivity {
    private TextView iv_register;
    private ImageView login_back;

    private EditText ed_loginID, ed_loginPwd;
    private Button bt_login;

    private String address = "http://192.168.43.89:8080/userLogi";  // 请求验证的地址
    private String loginTel = "";    // 获取到的用户登录电话号码
    private String loginPwd = "";   // 获取到的用户登录密码


    private String registerTel = "";
    private String registerPwd = "";
    private boolean registerState = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        initViews();
        setEvents();
        initInfo();
        
    }


    //所有按钮实例成对象
    public void initViews() {
        iv_register = (TextView) findViewById(R.id.iv_register);
        login_back = (ImageView) findViewById(R.id.login_back);
        ed_loginID = (EditText) findViewById(R.id.login_id);
        ed_loginPwd = (EditText) findViewById(R.id.login_pwd);
        bt_login = (Button) findViewById(R.id.logbtn);
    }

    //所有的对按钮的事件进行监听
    public void setEvents() {
        //匿名方法
        MyListener listener = new MyListener();
        iv_register.setOnClickListener(listener);
        login_back.setOnClickListener(listener);
        bt_login.setOnClickListener(listener);
    }
    // 保留，可优化，提高用户体验--20170414
    private void initInfo() {
        SharedPreferences pref = getSharedPreferences("registerData", MODE_PRIVATE);
        registerState = pref.getBoolean("registerState", false);
        registerTel = pref.getString("registerTel", "");
        registerPwd = pref.getString("registerPwd", "");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (registerState) {
                    ed_loginID.setText(registerTel);
                    ed_loginPwd.setText(registerPwd);
                } else {
                    ed_loginID.setText("");
                    ed_loginPwd.setText("");
                }
            }
        });


    }
    //选择触发的事件
    public class MyListener implements View.OnClickListener { /*用接口的方式*/
        public void onClick(View v) {
            Intent intent = null;
            int id = v.getId();   /*得到v的id付给id*/
            switch (id) {
                case R.id.iv_register:{
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    Log.d("登录页面", "跳转注册页面 ");
                    finish();
                    break;
                }
                case R.id.login_back:{
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Log.d("登录页面", "返回主界面 ");
                    finish();
                    break;
                }

                // 登录按钮事件处理
                case R.id.logbtn:{
                    loginTel = ed_loginID.getText().toString();
                    loginPwd = ed_loginPwd.getText().toString();

                    if(loginTel.equals("") || loginPwd.equals("")) {
                        Toast.makeText(LoginActivity.this, R.string.infoEmpty, Toast.LENGTH_SHORT).show();
                    } else {
//                    Toast.makeText(LoginActivity.this, loginTel + "," + loginPwd, Toast.LENGTH_SHORT).show();
                        // 发起网络请求进行验证
                        sendRequestWithOkHttp(address, new okhttp3.Callback(){
                            /**
                             * Called when the request could not be executed due to cancellation, a connectivity problem or
                             * timeout. Because networks can fail during an exchange, it is possible that the remote server
                             * accepted the request before the failure.
                             *
                             * @param call
                             * @param e
                             */
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("MainActivity","出现异常");
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "获取登录信息失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String responseData = response.body().string(); //得到返回具体内容

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            parseJSONWithGson(responseData); // 解析数据
                                            Log.d("data", responseData);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }

    private void sendRequestWithOkHttp(String address, okhttp3.Callback callback) {

        //*******
        OkHttpClient client = new OkHttpClient(); //创建 OkHttpClient实例
        RequestBody requestBody = new FormBody.Builder().add("tel", loginTel).add("pwd", loginPwd).build();
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
        //*******

    }

    private void parseJSONWithGson(String jsonData) throws JSONException {

//********
        Gson gson = new Gson();
        User appList = new User();
        appList = gson.fromJson(jsonData, new TypeToken<User>()
        {}.getType());

        int code = appList.getCode();

        if (code == 201) {
            Toast.makeText(LoginActivity.this, R.string.login_onUser, Toast.LENGTH_SHORT).show();
            ed_loginID.setText("");
        }
        else if (code == 202) {
            Toast.makeText(LoginActivity.this, R.string.login_pwdError, Toast.LENGTH_SHORT).show();
            ed_loginPwd.setText("");
        }
        else if(code == 200){
            Toast.makeText(LoginActivity.this, R.string.login_succeed, Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = getSharedPreferences("registerData", MODE_PRIVATE).edit();
            editor.putBoolean("registerState", true);
            Gson g = new Gson();
            String jsonStr = g.toJson(appList); //将对象转换成Json
            Log.d("jsonStr", jsonStr);
            editor.putString("KEY_PEOPLE_DATA", jsonStr) ; //存入json串
            editor.putInt("userId", appList.getUserId());
            editor.apply();
            editor.commit(); //提交
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            Log.d("登录页面", "返回主界面 ");
            finish();
        }
//********
    }

    protected void onStart() {
        super.onStart();

    }


    private String judgeProvider(LocationManager locationManager) {
        List<String> prodiverlist = locationManager.getProviders(true);
        if(prodiverlist.contains(LocationManager.NETWORK_PROVIDER)){
            return LocationManager.NETWORK_PROVIDER;//网络定位
        }else if(prodiverlist.contains(LocationManager.GPS_PROVIDER)) {
            return LocationManager.GPS_PROVIDER;//GPS定位
        }else{
            Toast.makeText(LoginActivity.this, "没有可用的位置提供器",Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    protected void onDestroy() {

        super.onDestroy();
    }
}
