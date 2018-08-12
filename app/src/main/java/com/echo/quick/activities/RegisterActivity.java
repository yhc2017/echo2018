package com.echo.quick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.quick.contracts.RegisterContract;
import com.echo.quick.presenters.RegisterPresenterImpl;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.ToastUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

/**
 * 文件名：RegisterActivity
 * 创建人：周少侠
 * 创建时间：2018/7/17 15:15
 * 类描述：
 *
 * 修改人：
 * 修改时间：
 * 修改内容：
 *
**/

public class RegisterActivity  extends AppCompatActivity implements Validator.ValidationListener,RegisterContract.IRegisterView {

    private ImageView iv_register_back;

    @Pattern(regex = "^\\d{11}$",messageResId=R.string.re_iphone_number_hint)
    @Order(1)
    private EditText ed_tel;

    @Password(min =6, scheme = Password.Scheme.ANY,messageResId =R.string.password)
    @Order(2)
    private EditText ed_pwd;

    @ConfirmPassword(messageResId =R.string.second_password)
    @Order(3)
    private EditText ed_sure_pwd;

    @NotEmpty(messageResId=R.string.resigter_user_name_hint)
    @Length(max=8, messageResId=R.string.resigter_user_name_length_hint)
    @Order(4)
    private EditText ed_name;

    String sex = "男";

    private Button btn_regidter;
    private RadioGroup register_sex_group;
    private RadioButton man,nv;
    private TextView register_sex_name;

    protected Validator validator;
    private RegisterContract.IRegister register;


    //当表单信息验证通过后设为true
    private Boolean isOk = false;

    //执行事件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);


        validator = new Validator(this);
        validator.setValidationListener(this);
        register = new RegisterPresenterImpl(this);


        initViews();
        setEvents();

    }




    //所有按钮实例成对象
    public void initViews() {

        iv_register_back = (ImageView)findViewById(R.id.register_back);

        ed_tel = (EditText) findViewById(R.id.register_iphone);
        ed_pwd = (EditText) findViewById(R.id.register_password);
        ed_sure_pwd = (EditText) findViewById(R.id.register_repassword);
        ed_name = (EditText) findViewById(R.id.register_uname);
        register_sex_name = (TextView)findViewById(R.id.register_sex_name);
        btn_regidter = (Button) findViewById(R.id.register_next_btn);

        register_sex_group = (RadioGroup)findViewById(R.id.register_sex_group);

    }


    //所有的对按钮的事件进行监听
    public void setEvents() {

        //匿名方法
        MyListener listener = new MyListener();

        iv_register_back.setOnClickListener(listener);

        btn_regidter.setOnClickListener(listener);  // 注册按钮

        // 当焦点失去时，进行表单验证
        ed_sure_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    validator.validate();
                }

            }
        });

        register_sex_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                validator.validate();
                switch (i){
                    case R.id.register_sex_nan:
                        sex = "男";
                        register_sex_name.setText("少侠");
                        break;

                    case R.id.register_sex_nv:
                        sex = "女";
                        register_sex_name.setText("女侠");
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        isOk = true;
    }


    /***
     * 验证失败的处理
     */
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

    }

    @Override
    public void onRegisterResult(final Boolean result, int code) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(result) {
                    ToastUtils.showLong(RegisterActivity.this, "注册成功");
                    startActivity(new Intent(RegisterActivity.this, InitPlanActivity.class));
                    finish();
                }else {
                    ToastUtils.showLong(RegisterActivity.this, "同一个号码只能注册一次");
                }
            }
        });
    }

    //选择触发的事件
    public class MyListener implements  View.OnClickListener { /*用接口的方式*/
        public void onClick(View v) {
            Intent intent = null;
            int id = v.getId();   /*得到v的id付给id*/
            switch (id) {
                //返回按钮
                case R.id.register_back:
//                    intent = new Intent(RegisterActivity.this, MainActivity.class);
//                    startActivity(intent);
                    LogUtils.d("注册页面跳转首页");
                    finish();
                    break;

                //注册按钮
                case R.id.register_next_btn:

                    if (isOk) {
                        String tel = ed_tel.getText().toString();
                        String pwd = ed_pwd.getText().toString();
                        String surePwd = ed_sure_pwd.getText().toString();
                        String name = ed_name.getText().toString();

                        if (tel.equals("") || pwd.equals("") || surePwd.equals("") || name.equals("")) {
                            ToastUtils.showShort(RegisterActivity.this, R.string.RegInfoNotFull);
                        } else {

                            validator.validate();
                            register.doRegister(tel, pwd, name, sex);

                      }
                    }
                    break;

                default:
                    break;
            }
        }
    }

}

