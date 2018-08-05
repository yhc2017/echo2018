package com.echo.quick.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.quick.contracts.HomeContract;
import com.echo.quick.utils.App;
import com.echo.quick.utils.MyPlanDialog;
import com.echo.quick.utils.NetUtils;
import com.echo.quick.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class name: HomeActivity
 * Specific description :首页控件重新换血
 * 创建人: HUAHUA
 * @Time :1.0 , 2018/8/5 13:28
 * 修改人：
 * @Time :
 * @since ：[产品|模块版本]
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener,HomeContract.IHomeView{

    private TextView tv_user_name,tv_from,tv_obtion,im_setting,tv_word_finish,tv_word_obtion,tv_word_over,tv_word_num;
    private ProgressBar my_word_plan_progressbar;
    private Button bt_start_study;
    private ImageView im_tor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        if(NetUtils.isConnected(HomeActivity.this)){
            Toast.makeText(this, "网络已连接", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "网络未连接，请连接再操作", Toast.LENGTH_SHORT).show();
        }

        //初始化
        initView();
        setdate();

    }

    protected void onResume() {
        super.onResume();
        try {
            updateUserName();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method name : initView
     * Specific description :控件初始化和监听绑定
     *@return  void
     */
    public void initView(){

        tv_user_name = (TextView)findViewById(R.id.tv_user_name);
        tv_from = (TextView)findViewById(R.id.tv_from);
        tv_obtion = (TextView)findViewById(R.id.tv_obtion);
        im_setting = (TextView)findViewById(R.id.im_setting);
        tv_word_finish = (TextView)findViewById(R.id.tv_word_finish);
        tv_word_obtion = (TextView)findViewById(R.id.tv_word_obtion);
        tv_word_over = (TextView)findViewById(R.id.tv_word_over);
        tv_word_num = (TextView)findViewById(R.id.tv_word_num);
        my_word_plan_progressbar = (ProgressBar)findViewById(R.id.my_word_plan_progressbar);
        bt_start_study = (Button) findViewById(R.id.bt_start_study);
        im_tor = (ImageView)findViewById(R.id.im_tor);

        //绑定监听
        im_setting.setOnClickListener(this);
        tv_word_finish.setOnClickListener(this);
        bt_start_study.setOnClickListener(this);
        im_tor.setOnClickListener(this);

    }

    /**
     * Method name : setdate
     * Specific description :塞数据
     *@return void
     */
    public void setdate(){
        tv_from.setText("四级");
        tv_obtion.setText("2018年12月");
        //词库单词数量
        tv_word_num.setText(String.valueOf(3500));
        //完成单词数
        tv_word_finish.setText(String.valueOf(630));
        //今日目标单词数
        tv_word_obtion.setText(String.valueOf(20));
        //超前学习单词数
        tv_word_over.setText(String.valueOf(10));
        //进度数
        my_word_plan_progressbar.setProgress(50);

    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){

            case R.id.tv_word_finish:
                intent = new Intent(HomeActivity.this, StrangeWordsListActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.bt_start_study:
                if(NetUtils.isConnected(HomeActivity.this)){
                    Toast.makeText(this, "网络已连接", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this, WordsActivity.class));
                }else {
                    Toast.makeText(this, "网络未连接，请连接再操作", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.im_tor:
                toUser(this);
                break;

            case R.id.tv_user_name:
                toUser(this);
                break;

            case R.id.im_setting:
                System.out.println("设置计划点击成功");
                MyPlanDialog myPlanDialog = new MyPlanDialog(HomeActivity.this);
                myPlanDialog.show();
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

    public void updateUserName() throws JSONException {
        if(true){
            Object o2 = "未登录";
            Object o = SPUtils.get(App.getContext(), "UserInfo", o2);
            if(o != o2){
                JSONObject object = new JSONObject(o.toString());
                tv_user_name.setText(object.getString("nickname"));
            }else {
                tv_user_name.setText("未登录");
            }

        }
    }
}
