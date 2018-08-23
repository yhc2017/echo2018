package com.echo.quick.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.quick.contracts.LoginContract;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.model.dao.impl.WordsLogImpl;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsLogDao;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.presenters.LoginPresenterImpl;
import com.echo.quick.presenters.OnlineWordPresenterImpl;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.ToastUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

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

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener,LoginContract.ILoginView {

    private TextView iv_register;
    private TextView iv_pwdforgive,tv_round_sent,tv_round_sent_tra;
    private ImageView login_back;

    @Pattern(regex = "^\\d{11}$",messageResId=R.string.re_iphone_number_hint)
    @Order(1)
    private EditText ed_loginID;

    @Password(min = 6, scheme = Password.Scheme.ANY,messageResId =R.string.password)
    @Order(2)
    private EditText ed_loginPwd;

    private Button bt_login;

    protected Validator validator;
    private LoginContract.ILoginPresenter loginPresenter;
    private OnlineWordContract.OnlineWordPresenter onlineWordPresenter;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        app = (App)getApplicationContext();
        initViews();
        setEvents();

        validator = new Validator(this);
        validator.setValidationListener(this);

        loginPresenter = new LoginPresenterImpl(this);
        onlineWordPresenter = new OnlineWordPresenterImpl(this);

        if(!app.getUserId().equals("111")){
            ed_loginID.setText(app.getUserId());
        }
        
    }


    //所有按钮实例成对象
    public void initViews() {
        iv_register = (TextView) findViewById(R.id.iv_register);
        iv_pwdforgive = (TextView) findViewById(R.id.iv_pwdforgive);
        login_back = (ImageView) findViewById(R.id.login_back);
        ed_loginID = (EditText) findViewById(R.id.login_id);
        ed_loginPwd = (EditText) findViewById(R.id.login_pwd);
        bt_login = (Button) findViewById(R.id.logbtn);
        tv_round_sent = (TextView) findViewById(R.id.tv_round_sent);
        tv_round_sent_tra = (TextView) findViewById(R.id.tv_round_sent_tra);
        //随机设置一个名言
        String[] arr = getResources().getStringArray(R.array.entities);
        String[] arr_tra = getResources().getStringArray(R.array.entities_tra);
        //产生0-(arr.length-1)的整数值,也是数组的索引
        int index=(int)(Math.random()*arr.length);
        String rand = arr[index];
        String rand_tra = arr_tra[index];
        tv_round_sent.setText(rand);
        tv_round_sent_tra.setText(rand_tra);
    }

    //所有的对按钮的事件进行监听
    public void setEvents() {
        //匿名方法
        MyListener listener = new MyListener();
        iv_register.setOnClickListener(listener);
        iv_pwdforgive.setOnClickListener(listener);
        login_back.setOnClickListener(listener);
        bt_login.setOnClickListener(listener);
        ed_loginID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    new Thread() {
                        public void run() {
                            try {
                                loginPresenter.detectionAndRestoration(ed_loginID.getText().toString());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        //校验成功后进行登录操作
        final String loginTel = ed_loginID.getText().toString();       //登录账号
        final String loginPwd = ed_loginPwd.getText().toString();      //登录密码
        //调用登录方法
        new Thread() {
            public void run() {
                try {
                    loginPresenter.doLogin(loginTel, loginPwd);
                    //获取服务器的库
                    onlineWordPresenter.GetAllWordTopicInfo();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onClearText() {
        ed_loginID.setText("");
        ed_loginPwd.setText("");
    }


    @Override
    public void onLoginResult(Boolean result, final String code) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (code) {
                    case "200":
//                    loginPresenter.detectionAndRestoration(app.getUserId());
                        ToastUtils.showLong(LoginActivity.this, app.getTopicId() + app.getUserId());
                        IWordsLogDao iWordsLogDao = new WordsLogImpl();
                        IWordsStatusDao iWordsStatusDao = new WordsStatusImpl();
                        if (iWordsLogDao.detectionEmpty() && iWordsStatusDao.detectionEmpty()) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("userId", app.getUserId());
                            map.put("topicId", app.getTopicId());
                            onlineWordPresenter.postToGetTopicIdWords(map, true);
                        } else {
                            ToastUtils.showLong(LoginActivity.this, "登录成功");
                            finish();
                        }
                        break;
                    //成功获取
                    case "500":
                        ToastUtils.showLong(LoginActivity.this, "登录成功");
                        finish();
                        break;
                    default:
                        ToastUtils.showLong(LoginActivity.this, "error，请检查账号或密码是否正确");
                        break;
                }
            }
        });
    }

    @Override
    public void overWordInfo() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    loginPresenter.allWordInfo(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                    finish();
                }
            }
        });
    }

    //选择触发的事件
    public class MyListener implements View.OnClickListener { /*用接口的方式*/
        public void onClick(View v) {

            int id = v.getId();   /*得到v的id付给id*/

            switch (id) {
                case R.id.iv_register:

                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    LogUtils.d("登录页面", "跳转注册页面 ");
                    finish();
                    break;

                case R.id.login_back:
                    LogUtils.d("登录页面", "返回主界面 ");
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                    break;

                case R.id.iv_pwdforgive:

                    break;

                // 登录按钮事件处理
                case R.id.logbtn:
                    //通过校验后，在校验成功里进行登录操作
                    validator.validate();
                    ToastUtils.showLong(LoginActivity.this, "请稍后");
//                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
//                    progressDialog.setIcon(R.drawable.boy);
//                    progressDialog.setTitle("请稍等");
//                    progressDialog.setMessage("正在加载中");
//                    progressDialog.show();
                    break;

                default:
                    break;
            }
        }
    }


    protected void onDestroy() {
        super.onDestroy();

    }
}
