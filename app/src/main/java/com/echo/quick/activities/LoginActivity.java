package com.echo.quick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.ToastUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

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

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    private TextView iv_register;
    private TextView iv_pwdforgive;
    private ImageView login_back;

    @Pattern(regex = "^\\d{11}$",messageResId=R.string.re_iphone_number_hint)
    @Order(1)
    private EditText ed_loginID;

    @Password(min = 6, scheme = Password.Scheme.ANY,messageResId =R.string.password)
    @Order(2)
    private EditText ed_loginPwd;

    private Button bt_login;

    protected Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        validator = new Validator(this);
        validator.setValidationListener(this);

        initViews();
        setEvents();
        
    }


    //所有按钮实例成对象
    public void initViews() {
        iv_register = (TextView) findViewById(R.id.iv_register);
        iv_pwdforgive = (TextView) findViewById(R.id.iv_pwdforgive);
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
        iv_pwdforgive.setOnClickListener(listener);
        login_back.setOnClickListener(listener);
        bt_login.setOnClickListener(listener);
    }

    @Override
    public void onValidationSucceeded() {
        //校验成功后进行登录操作
        String loginTel = ed_loginID.getText().toString();       //登录账号
        String loginPwd = ed_loginPwd.getText().toString();      //登录密码

        ToastUtils.showShort(LoginActivity.this, loginTel+loginPwd);
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

    //选择触发的事件
    public class MyListener implements View.OnClickListener { /*用接口的方式*/
        public void onClick(View v) {

            Intent intent = null;
            int id = v.getId();   /*得到v的id付给id*/

            switch (id) {
                case R.id.iv_register:
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    LogUtils.d("登录页面", "跳转注册页面 ");
                    finish();
                    break;

                case R.id.login_back:
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LogUtils.d("登录页面", "返回主界面 ");
                    finish();
                    break;

                case R.id.iv_pwdforgive:

                    break;

                // 登录按钮事件处理
                case R.id.logbtn:
                    //通过校验后，在校验成功里进行登录操作
                    validator.validate();

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