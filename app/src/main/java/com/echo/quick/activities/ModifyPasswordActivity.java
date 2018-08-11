package com.echo.quick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.quick.presenters.RegisterPresenterImpl;
import com.echo.quick.utils.App;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.HashMap;
import java.util.List;

/**
 * Class name: ModifyPasswordActivity
 * Specific description :<功能的详细描述>
 * 创建人: HUAHUA
 * @Time : 2018/8/8
 * 修改人：
 * @Time :
 * @since ：[quick|user]
 */

public class ModifyPasswordActivity extends AppCompatActivity implements Validator.ValidationListener{
    private ImageView ic_back;

    @NotEmpty(messageResId=R.string.password)
    @Order(1)
    private EditText et_before_pwd;

    @Password(min =6, scheme = Password.Scheme.ANY,messageResId =R.string.new_password)
    @Order(2)
    private EditText et_new_pwd;

    @ConfirmPassword(messageResId =R.string.new_password_2)
    @Order(3)
    private EditText et_confirm_pwd;

    private TextView tv_mod_user_ok;

    protected Validator validator;

    private MyListener listener;

    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message_modify);
        app = (App)getApplicationContext();
        validator = new Validator(this);
        validator.setValidationListener(this);

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

    @Override
    public void onValidationSucceeded() {
        //检验通过
        final HashMap<String, String> map = new HashMap<>();
        map.put("userId", app.getUserId());
        map.put("oldPwd", et_before_pwd.getText().toString());
        map.put("newPwd", et_new_pwd.getText().toString());

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
                    finish();
                    break;
                case R.id.tv_mod_user_ok:
                    //提交修改
                    validator.validate();
                    break;


                default:
                    break;
            }
        }
    }

}
