package com.echo.quick.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.quick.contracts.HomeContract;
import com.echo.quick.utils.App;
import com.echo.quick.utils.MyPlanDialog;
import com.echo.quick.utils.NetUtils;
import com.echo.quick.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,HomeContract.IHomeView{

    private TextView tv_user_name;

    private ImageView roundImageView_home,roundImageView_user_setting,mim_setting,mim_date;

    private LinearLayout Lordinario;
    private LinearLayout Lsprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        if(NetUtils.isConnected(HomeActivity.this)){
            Toast.makeText(this, "网络已连接", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "网络未连接，请连接再操作", Toast.LENGTH_SHORT).show();
        }

        //初始化
        initView();

    }

    protected void onResume() {
        super.onResume();
        try {
            updateUserName();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initView(){

        tv_user_name = (TextView)findViewById(R.id.tv_user_name);

        roundImageView_home = (ImageView)findViewById(R.id.roundImageView_home);
        roundImageView_user_setting = (ImageView)findViewById(R.id.roundImageView_user_setting);

        Lordinario = (LinearLayout)findViewById(R.id.L_ordinario);
        Lsprint = (LinearLayout)findViewById(R.id.L_sprint);

        mim_setting = (ImageView)findViewById(R.id.im_setting);
        mim_date = (ImageView)findViewById(R.id.im_date);

        mim_setting.setOnClickListener(this);
        mim_date.setOnClickListener(this);

        tv_user_name.setOnClickListener(this);

        roundImageView_home.setOnClickListener(this);
        roundImageView_user_setting.setOnClickListener(this);

        Lordinario.setOnClickListener(this);
        Lsprint.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.L_ordinario:
                startActivity(new Intent(HomeActivity.this, StrangeWordsListActivity.class));
                break;

            case R.id.L_sprint:
                if(NetUtils.isConnected(HomeActivity.this)){
                    Toast.makeText(this, "网络已连接", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this, ReadingMianActivity.class));
                }else {
                    Toast.makeText(this, "网络未连接，请连接再操作", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.roundImageView_home:
                toUser(this);
                break;

            case R.id.roundImageView_user_setting:
                toUser(this);
                break;

            case R.id.tv_user_name:
                toUser(this);
                break;

            case R.id.im_setting:
                System.out.println("设置计划点击成功");
                MyPlanDialog myPlanDialog = new MyPlanDialog(HomeActivity.this);
                myPlanDialog.show();
                break;

            case R.id.im_date:
                System.out.println("查看打卡点击成功");
                break;

            default:
                break;

        }
    }

    public void toUser(Context context){

        if(1 == 1){
            startActivity(new Intent(context, LoginActivity.class));
        }else {
            startActivity(new Intent(context, UserMsgActivity.class));
        }

    }

    public void updateUserName() throws JSONException {
        if(true){
            Object o2 = "请先登录";
            Object o = SPUtils.get(App.getContext(), "UserInfo", o2);
            if(o != o2){
                JSONObject object = new JSONObject(o.toString());
                tv_user_name.setText(object.getString("nickname"));
            }else {
                tv_user_name.setText("请先登录");
            }

        }
    }
}
