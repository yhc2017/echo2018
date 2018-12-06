package com.echo.quick.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.quick.common.PreferenceConstants;
import com.echo.quick.common.PreferenceManager;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.contracts.UserMessageContract;
import com.echo.quick.pojo.Words_Log;
import com.echo.quick.pojo.Words_Status;
import com.echo.quick.presenters.OnlineWordPresenterImpl;
import com.echo.quick.presenters.UserMessagePresenterImpl;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.SPUtils;
import com.echo.quick.utils.ToastUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.List;

/**
 * Class name: UserMessageActivity
 * Specific description :用户信息修改和退出登录
 * 创建人: HUAHUA
 * @Time : 2018/8/8
 * 修改人：
 * @Time :
 * @since ：[quick|user]
 */

public class UserMessageActivity extends AppCompatActivity implements UserMessageContract.IUserMessageView,Validator.ValidationListener{

    @NotEmpty(messageResId=R.string.resigter_user_name_hint)
    @Length(max=8, messageResId=R.string.resigter_user_name_length_hint)
    @Order(1)
    private EditText et_name;
    private TextView tv_mod_user_ok,tv_sex_rs,tv_id;
    private Button exitbtn;
    private RadioGroup rg_sex;
    private RadioButton rb_man,rb_woman;
    private ImageView iv_user_message_back;
    private MyListener listener;
    private RelativeLayout rl_modify_pwd;
    private App app;
    private UserMessageContract.IUserMessagePresenter presenter;
    protected Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message2);
        app = (App)getApplicationContext();
        presenter = new UserMessagePresenterImpl(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
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
        tv_mod_user_ok = (TextView) findViewById(R.id.tv_mod_user_ok);
        tv_sex_rs = (TextView) findViewById(R.id.tv_sex_rs);
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
        tv_id.setText(app.getUserId());//手机账号
        et_name.setText(app.getNickName());//昵称
        if(app.getSex().equals("男")){
            rb_man.setChecked(true);
        }else {
            rb_woman.setChecked(true);
        }
    }

    @Override
    public void updateInfoResult(final Boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(res){
                    ToastUtils.showLong(UserMessageActivity.this, "更新成功");
                    initData();
                }else{
                    ToastUtils.showLong(UserMessageActivity.this, "异常情况，无法完成操作");
                    initData();
                }
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("userId", app.getUserId());
        map.put("nickname", et_name.getText().toString());
        if(tv_sex_rs.getText().toString().equals("少侠")) {
            map.put("sex", "男");
        }
        else {
            map.put("sex", "女");
        }
        presenter.postToUpdate(map);
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
     * class name : MyListener
     * Specific description :事件处理
     */
    public class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_user_message_back:
                    finish();
                    break;
                case R.id.exitbtn:
                    //退出登录
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserMessageActivity.this);
                    builder.setTitle("退出登录");
                    builder.setIcon(R.drawable.ic_back);
                    builder.setNeutralButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           LogUtils.d("点击返回按钮");
                        }
                    });

                    builder.setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            LogUtils.d( "清除数据");
                            PreferenceManager.getInstance().put(PreferenceConstants.USERLOGIN,"false");
                            String a = (String) PreferenceManager.getInstance().get(PreferenceConstants.USERLOGIN,"");
                            System.out.printf("UserMessageAcitity类："+a);

                            OnlineWordContract.OnlineWordPresenter online = new OnlineWordPresenterImpl();
                            //发送数据
                            online.postOnlineWordsLog();
                            SPUtils.clear(App.getContext());
                            LitePal.deleteAll(Words_Status.class);
                            LitePal.deleteAll(Words_Log.class);
                            app.setUserId("111");
                            app.setNickName("请登录");

                            startActivity(new Intent(UserMessageActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
                    builder.show();
                    break;

                case R.id.tv_mod_user_ok:
                    validator.validate();
                    //提交修改
                    break;
                case R.id.rl_modify_pwd:
                    Intent intent = new Intent(UserMessageActivity.this, ModifyPasswordActivity.class);
                    if(getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null){
                        startActivity(intent);
                    }else {
                        ToastUtils.showLong(UserMessageActivity.this, "异常");
                    }
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
                case R.id.rb_man:
                    tv_sex_rs.setText("少侠");
                    break;

                case R.id.rb_woman:
                    tv_sex_rs.setText("女侠");
                    break;

                default:
                    break;
            }
        }
    }


}
