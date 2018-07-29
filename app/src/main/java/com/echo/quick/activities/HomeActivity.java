package com.echo.quick.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.echo.quick.contracts.HomeContract;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,HomeContract.IHomeView{

    private TextView tv_user_name;

    private ImageView roundImageView_home,roundImageView_user_setting;

    private LinearLayout Lordinario;
    private LinearLayout Lsprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //初始化
        initView();

        try {
            updateUserName(true);
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
                startActivity(new Intent(HomeActivity.this, ReadingMianActivity.class));

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

    public void updateUserName(Boolean isExist) throws JSONException {
        if(isExist){
            Object o2 = "请先登录";
            Object o = SPUtils.get(App.getContext(), "UserInfo", o2);
            JSONObject object = new JSONObject(o.toString());
            tv_user_name.setText(object.getString("nickname"));
        }
    }
}
