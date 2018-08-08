package com.echo.quick.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.echo.quick.contracts.HomeContract;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.Words_Status;
import com.echo.quick.presenters.HomePresenterImpl;
import com.echo.quick.presenters.OnlineWordPresenterImpl;
import com.echo.quick.utils.App;
import com.echo.quick.utils.MyPlanDialog;
import com.echo.quick.utils.NetUtils;
import com.echo.quick.utils.SPUtils;
import com.echo.quick.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

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

    private TextView tv_way,tv_user_name,tv_from,tv_obtion,im_setting,tv_word_finish,tv_word_obtion,tv_word_over,tv_word_num;
    private ProgressBar my_word_plan_progressbar;
    private Button bt_start_study;
    private ImageView im_tor;
    private App app;
    int datenum2 = 0;
//线程
    Handler mHandler = null;
    Runnable mRunnable = null;

    HomeContract.IHomePresenter homePresenter;

    private ActivityReceiver activityReceiver1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        app = (App)getApplication();

        // 创建BroadcastReceiver
        activityReceiver1 = new ActivityReceiver();
        // 创建IntentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(app.UPDATE_ACTION);
        registerReceiver(activityReceiver1, filter);

        if(NetUtils.isConnected(HomeActivity.this)){
            Toast.makeText(this, "网络已连接", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "网络未连接，请连接再操作", Toast.LENGTH_SHORT).show();
        }

        homePresenter = new HomePresenterImpl(this);

        //初始化
        initView();
        initData();
        setdate();
    }

    protected void onResume() {
        super.onResume();
        try {
            updateUserName();
            setdate();
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
        tv_way = (TextView)findViewById(R.id.tv_way);

        //绑定监听
        im_setting.setOnClickListener(this);
        tv_word_finish.setOnClickListener(this);
        bt_start_study.setOnClickListener(this);
        im_tor.setOnClickListener(this);

    }

    /**
     * Method name : initData
     * Specific description :数据初始化
     *@return void
     */
    public void initData(){
        //今日目标单词数默认值
        try {
            datenum2 = homePresenter.calMyPlanNmu("2018-12", 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.printf("=================每日的目标数："+datenum2);
        tv_word_obtion.setText(""+datenum2);
    }

    /**
     * Method name : setdate
     * Specific description :塞数据
     *@return void
     */
    public void setdate(){
        IWordsStatusDao statusDao = new WordsStatusImpl();
        //完成单词数
        int overWords = statusDao.selectCount("review_grasp");
        int allWords = 3500;
        try{
            tv_from.setText(SPUtils.get(App.getContext(), "box","四级").toString().substring(0, 2));
            tv_obtion.setText(SPUtils.get(App.getContext(), "plan","2018").toString());
            tv_way.setText(SPUtils.get(App.getContext(), "planType","复习优先").toString());
            Object o = SPUtils.get(App.getContext(), "wordsBox", "");
            JSONArray jsonArray = JSONArray.parseArray(o.toString());
            String i = SPUtils.get(App.getContext(), "boxPosition", "1").toString();
            com.alibaba.fastjson.JSONObject object = jsonArray.getJSONObject(Integer.parseInt(i));
            allWords = Integer.valueOf(object.getString("wordAllCount"));
            //今日目标单词数
            tv_word_obtion.setText(SPUtils.get(App.getContext(), "dateNum",datenum2).toString());
        }catch (Exception e){
            e.printStackTrace();
            tv_from.setText("四级");
            tv_obtion.setText("2018年12月");
        }

        //完成单词数
        tv_word_finish.setText(String.valueOf(overWords));

        //超前学习单词数
        tv_word_over.setText(String.valueOf(0));
        //词库单词数量
        tv_word_num.setText(overWords+"/"+allWords);
        //进度数
        my_word_plan_progressbar.setMax(allWords);
        my_word_plan_progressbar.setProgress(overWords);

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
                    getWordStatus(true);
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
            startActivity(new Intent(context, UserMessageActivity.class));
        }

    }

    public void popWindow(final Context context, final Boolean learn){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("今日份已完成");
//        final EditText editText;
//        builder.setView(editText = new EditText(AthleticsActivity.this));
        builder.setIcon(R.drawable.boy);
        builder.setNeutralButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("继续下一组", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
                final HashMap<String, String> map = new HashMap<>();
                map.put("userId", app.getUserId());
                map.put("topicId", "17");
                String planType = SPUtils.get(App.getContext(), "planType", "复习优先").toString();
                if(planType.equals("复习优先")) {
                    onlineWordPresenter.getOnlineWordReviewOrLearn(map, "review");
                } else {
                    onlineWordPresenter.getOnlineWordReviewOrLearn(map, "learn");
                }
                final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
                progressDialog.setIcon(R.drawable.boy);
                progressDialog.setTitle("请稍等");
                progressDialog.setMessage("正在加载中");
                progressDialog.show();
                mHandler = new Handler();
                mRunnable = new Runnable() {
                    @Override
                    public void run()
                    {

                        IWordsStatusDao iWordsStatusDao = new WordsStatusImpl();
                        if(iWordsStatusDao.selectByStatus("").size() >10){
                            progressDialog.dismiss();
                            getWordStatus(learn);
                        }

                    }
                };
                mHandler.postDelayed(mRunnable, 2000);
            }
        });
        builder.show();
    }


    public void getWordStatus(Boolean learn){

        IWordsStatusDao statusDao = new WordsStatusImpl();
        List<Words_Status> wordLearn = statusDao.selectByStatus("");
        List<Words_Status> wordReview = statusDao.selectByStatus("review");
        if(statusDao.selectCount("") != 0){
            if(learn){
                wordLearn.addAll(wordReview);
                app.setStatusList(wordLearn);
            }else {
                wordReview.addAll(wordLearn);
                app.setStatusList(wordReview);
            }
            Intent intent = new Intent(HomeActivity.this, WordsActivity.class);
            startActivity(intent);
        }else {
            popWindow(HomeActivity.this, learn);
        }
    }

    public void updateUserName() throws JSONException {
        if(true){
            Object o2 = "未登录";
            Object o = SPUtils.get(App.getContext(), "UserInfo", o2);
            if(o != o2){
                JSONObject object = new JSONObject(o.toString());
                tv_user_name.setText(object.getString("nickname"));
                app.setUserId(object.getString("userId"));
            }else {
                tv_user_name.setText("未登录");
            }

        }
    }


    protected void onPause() {
        super.onPause();
        try {
            mHandler.removeCallbacks(mRunnable);
            setdate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void updatePlan() {
        setdate();
    }

    public class ActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setdate();
        }
    }
}
