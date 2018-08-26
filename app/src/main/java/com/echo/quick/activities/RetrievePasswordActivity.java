package com.echo.quick.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
/**
 * Class name: RetrievePasswordActivity
 * Specific description :<功能的详细描述>
 * 创建人: HUAHUA
 * @Time :1.0 , 2018/8/26 16:29
 * 修改人：
 * @Time :
 * @since ：[quick|模块版本]
 */
public class RetrievePasswordActivity extends AppCompatActivity {
    private EditText metIphone,metNewPwd,metConfirmPwd;
    private Button mbtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        initView();

    }

    /**
     * Method name : initView
     * Specific description :初始化控件
     *@return void
     */
    private void initView() {
        metIphone = (EditText)findViewById(R.id.et_iphone);
        metNewPwd = (EditText)findViewById(R.id.et_new_pwd);
        metConfirmPwd = (EditText)findViewById(R.id.et_confirm_pwd);
        mbtLogin = (Button) findViewById(R.id.bt_login);
    }

}
