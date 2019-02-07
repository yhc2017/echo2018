package com.echo.quick.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.echo.quick.contracts.UserMessageContract;
import com.echo.quick.presenters.UserMessagePresenterImpl;
import com.echo.quick.utils.App;
import com.echo.quick.utils.ToastUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.HashMap;
import java.util.List;

/**
 * Class name: RetrievePasswordActivity
 * Specific description :<功能的详细描述>
 * 创建人: HUAHUA
 * @Time :1.0 , 2018/8/26 16:29
 * 修改人：
 * @Time :
 * @since ：[quick|模块版本]
 */
public class RetrievePasswordActivity extends AppCompatActivity implements Validator.ValidationListener,UserMessageContract.IUserMessageView{

    private EditText metIphone;

    @Password(min = 6, scheme = Password.Scheme.ANY,messageResId =R.string.new_password)
    @Order(1)
    private EditText metNewPwd;

    @ConfirmPassword(messageResId =R.string.new_password_2)
    @Order(2)
    private EditText metConfirmPwd;

    private Button mbtLogin;

    private App app;

    private UserMessageContract.IUserMessagePresenter presenter;

    protected Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        ActivityManager.getInstance().addActivity(this);
        validator = new Validator(this);
        validator.setValidationListener(this);

        app = (App)App.getContext();

        presenter = new UserMessagePresenterImpl(this);

        initView();

        metIphone.setText(app.getUserId());


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
        mbtLogin.setOnClickListener(new MyListener());
    }

    @Override
    public void updateInfoResult(final Boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(res){
                    ToastUtils.showLong(RetrievePasswordActivity.this, "修改成功");
                }else{
                    ToastUtils.showLong(RetrievePasswordActivity.this, "异常情况，无法完成操作");
                }
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
//检验通过
        final HashMap<String, String> map = new HashMap<>();
        map.put("userId", metIphone.getText().toString());
        map.put("newPwd", metNewPwd.getText().toString());
        presenter.postUserForgetPwd(map);
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

    private class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.bt_login:
                    validator.validate();
                    break;
                default:
                    break;
            }
        }
    }
}
