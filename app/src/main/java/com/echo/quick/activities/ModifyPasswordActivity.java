package com.echo.quick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class name: ModifyPasswordActivity
 * Specific description :<功能的详细描述>
 * 创建人: HUAHUA
 * @Time : 2018/8/8
 * 修改人：
 * @Time :
 * @since ：[quick|user]
 */

public class ModifyPasswordActivity extends AppCompatActivity{
    private ImageView ic_back;
    private EditText et_before_pwd,et_new_pwd,et_confirm_pwd;
    private TextView tv_mod_user_ok;
    private MyListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message_modify);
        initView();
    }

    /**
     * Method name : initView
     * Specific description :实例化控件和绑定事件
     *@return void
     */
    private void initView() {
        ic_back = (ImageView) findViewById(R.id.ic_back);
        et_before_pwd = (EditText) findViewById(R.id.et_before_pwd);
        et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
        et_confirm_pwd = (EditText) findViewById(R.id.et_confirm_pwd);
        tv_mod_user_ok = (TextView) findViewById(R.id.tv_mod_user_ok);

        listener = new MyListener();
        tv_mod_user_ok.setOnClickListener(listener);
        ic_back.setOnClickListener(listener);

    }

    /**
     * Method name :
     * Specific description :事件处理
     *@return
     */
    public class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ic_back:
                    startActivity(new Intent(ModifyPasswordActivity.this,UserMessageActivity.class));
                    finish();
                    break;
                case R.id.tv_mod_user_ok:
                    //提交修改
                default:
                    break;
            }
        }
    }

}
