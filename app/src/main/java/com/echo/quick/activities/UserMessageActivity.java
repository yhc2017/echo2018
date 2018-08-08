package com.echo.quick.activities;

import android.content.Intent;
import android.hardware.camera2.params.RggbChannelVector;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Class name: UserMessageActivity
 * Specific description :用户信息修改和退出登录
 * 创建人: HUAHUA
 * @Time : 2018/8/8
 * 修改人：
 * @Time :
 * @since ：[quick|user]
 */

public class UserMessageActivity extends AppCompatActivity{

    private EditText et_name,et_password;
    private TextView tv_mod_user_ok,tv_sex_rs,tv_id;
    private Button exitbtn;
    private RadioGroup rg_sex;
    private RadioButton rb_man,rb_woman;
    private ImageView iv_user_message_back;
    private MyListener listener;
    private RelativeLayout rl_modify_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message2);
        initView();
        initData();
    }

    /**
     * Method name : initView
     * Specific description :实例化控件和绑定事件
     *@return void
     */
    private void initView() {
        tv_id = (TextView) findViewById(R.id.tv_id);
        iv_user_message_back = (ImageView) findViewById(R.id.iv_user_message_back);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_name);
        tv_mod_user_ok = (TextView) findViewById(R.id.et_name);
        tv_sex_rs = (TextView) findViewById(R.id.et_name);;
        exitbtn = (Button) findViewById(R.id.exitbtn);
        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        rb_man = (RadioButton) findViewById(R.id.rb_man);
        rb_woman = (RadioButton) findViewById(R.id.rb_woman);
        rl_modify_pwd = (RelativeLayout) findViewById(R.id.rl_modify_pwd);

        listener = new MyListener();
        iv_user_message_back.setOnClickListener(listener);
        tv_mod_user_ok.setOnClickListener(listener);
        exitbtn.setOnClickListener(listener);
        rl_modify_pwd.setOnClickListener(listener);

        rg_sex.setOnCheckedChangeListener(new RGListener());
    }
    
    /**
     * Method name : initData
     * Specific description :初始化数据
     *@return 
     */
    public void initData(){
//        tv_id.setText("");//手机账号
//        et_name.setText("");//昵称
    }

    /**
     * class name : MyListener
     * Specific description :事件处理
     */
    public class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_user_message_back:
                    startActivity(new Intent(UserMessageActivity.this,HomeActivity.class));
                    finish();
                    break;
                case R.id.exitbtn:
                    //退出登录
                    break;
                case R.id.tv_mod_user_ok:
                    //提交修改
                    break;
                case R.id.rl_modify_pwd:
                    startActivity(new Intent(UserMessageActivity.this,ModifyPasswordActivity.class));
                    finish();
                    break;
                    default:
                        break;
            }
        }
    }

    /**
     * Class name : RGListener
     * Specific description :对radioGroup选中改变的监听
     */
    public class RGListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int id) {
            switch (id){
                case R.id.register_sex_nan:
                    tv_sex_rs.setText("少侠");
                    break;

                case R.id.register_sex_nv:
                    tv_sex_rs.setText("女侠");
                    break;

                default:
                    break;
            }
        }
    }


}
