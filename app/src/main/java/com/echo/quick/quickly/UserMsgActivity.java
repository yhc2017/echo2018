package com.echo.quick.quickly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.echo.quick.quickly.utils.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserMsgActivity extends AppCompatActivity {
    private TextView mtv_re,mtv_or,tv_name,re_usernum,order_usernum;
    private ImageView mMo_usermessage;
    private ImageView user_back;
    User user;
    private Button bt_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        try {
            SharedPreferences sPreferences = getSharedPreferences("registerData", MODE_PRIVATE);
            String jsonData = sPreferences.getString("KEY_PEOPLE_DATA", null);
            Log.d("jsondata", jsonData);
            Gson gson = new Gson();
            user = gson.fromJson(jsonData, new TypeToken<User>()
            {}.getType());
            initview();
            setEnet();
        }catch (Exception e){
            Intent intent  = new Intent(UserMsgActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    //实例化控件
    public void initview(){
        mtv_re =(TextView) findViewById(R.id.tv_re);
        mtv_or = (TextView)findViewById(R.id.tv_or);
        tv_name = (TextView)findViewById(R.id.tv_name);
        re_usernum = (TextView)findViewById(R.id.re_usernum);
        order_usernum = (TextView)findViewById(R.id.order_usernum);
        bt_exit = (Button)findViewById(R.id.bt_exit);
        user_back = (ImageView)findViewById(R.id.user_back);
    }

    void  setEnet(){
        tv_name.setText(user.getNickname());
        re_usernum.setText("98");
        order_usernum.setText("98");
        //接单按钮
        mtv_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(UserMsgActivity.this, ListShowActivity.class);
//                startActivity(intent);
            }
        });

        //发单按钮
        mtv_or.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(UserMsgActivity.this, ListShowActivity.class);
//                startActivity(intent);
            }
        });

        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("registerData", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                editor.commit();
                finish();
            }
        });

        user_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

}
