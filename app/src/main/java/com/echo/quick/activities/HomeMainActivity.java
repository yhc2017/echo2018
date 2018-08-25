package com.echo.quick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.echo.quick.utils.App;
import com.echo.quick.utils.MyPlanDialog;
import com.echo.quick.utils.NetUtils;
import com.echo.quick.utils.SPUtils;
import com.echo.quick.utils.ToastUtils;
import com.echo.quick.utils.TypefaceUtil;

/**
 * Class name: HomeMainActivity
 * Specific description :新首页界面
 * 创建人: HUAHUA
 * @Time : 2018/8/24 19:32
 * 修改人：
 * @Time :
 * @since ：[quick|home]
 */

public class HomeMainActivity extends AppCompatActivity {
    private ImageView mimUserSetting,mimTor;
    private TextView mtvSettingPlan,mtvUserName,mtvUser_plan,mtvUserPlanTime;
    private TextView mtvWordbox,mtvPlanWay,mtvAllWords,mtvTodayWord;
    private TextView mtvOverDay,mtvNewWordsNum,mtvReviewWordsNum,mtvUnfamiliarWord;
    private LinearLayout mllUnfamiliarWordEnter;
    private Button mbtStartStudy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        TypefaceUtil.replaceFont(this, "assets/yuehei.ttf");
        setinitView();
        setEvent();
    }

    /**
     * Method name : setinitView
     * Specific description :初始化控件
     *@return void
     */
    private void setinitView() {
        mimUserSetting = (ImageView)findViewById(R.id.im_user_setting);
        mimTor = (ImageView)findViewById(R.id.im_tor);
        mtvUserName = (TextView) findViewById(R.id.tv_user_name);
        mtvUser_plan = (TextView) findViewById(R.id.tv_user_plan);
        mtvUserPlanTime = (TextView) findViewById(R.id.tv_user_plan_time);
        mtvWordbox = (TextView) findViewById(R.id.tv_wordbox);
        mtvPlanWay = (TextView) findViewById(R.id.tv_plan_way);
        mtvAllWords = (TextView) findViewById(R.id.tv_all_words);
        mtvTodayWord = (TextView) findViewById(R.id.tv_today_word);
        mtvOverDay = (TextView) findViewById(R.id.tv_over_day);
        mtvNewWordsNum = (TextView) findViewById(R.id.tv_new_words_num);
        mtvReviewWordsNum = (TextView) findViewById(R.id.tv_review_words_num);
        mtvUnfamiliarWord = (TextView) findViewById(R.id.tv_unfamiliar_word);
        mtvSettingPlan = (TextView) findViewById(R.id.tv_setting_plan);
        mllUnfamiliarWordEnter = (LinearLayout)findViewById(R.id.ll_unfamiliar_word_enter);
        mbtStartStudy = (Button)findViewById(R.id.bt_start_study);
    }

    /**
     * Method name : setEvent
     * Specific description :控件的事件监听
     *@return void
     */
    private void setEvent() {
        MyListener listener = new MyListener();
        mimUserSetting.setOnClickListener(listener);
        mtvSettingPlan.setOnClickListener(listener);
        mllUnfamiliarWordEnter.setOnClickListener(listener);
        mbtStartStudy.setOnClickListener(listener);
    }

    /**
     * Class name : MyListener
     * Specific description :本界面控件事件处理的内部类
     */
    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = null;
            MyPlanDialog myPlanDialog = new MyPlanDialog(HomeMainActivity.this);
            switch (view.getId()) {
                case R.id.im_user_setting:
                    //用户登录或修改信息
                    break;
                case R.id.tv_setting_plan:
                    System.out.println("设置计划点击成功");
                    myPlanDialog = new MyPlanDialog(HomeMainActivity.this);
                    myPlanDialog.show();
                    break;
                case R.id.ll_unfamiliar_word_enter:
                    intent = new Intent(HomeMainActivity.this, UserMessageActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_start_study:
                    //开始背单词
                    break;
                default:
                    break;
            }
        }
    }
}
