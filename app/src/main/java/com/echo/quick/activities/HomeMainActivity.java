package com.echo.quick.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import com.echo.quick.contracts.LoginContract;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.Words_Status;
import com.echo.quick.presenters.HomePresenterImpl;
import com.echo.quick.presenters.LoginPresenterImpl;
import com.echo.quick.presenters.OnlineWordPresenterImpl;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.MyPlanDialog;
import com.echo.quick.utils.NetUtils;
import com.echo.quick.utils.SPUtils;
import com.echo.quick.utils.ToastUtils;

import org.json.JSONException;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import zhy.com.highlight.HighLight;
import zhy.com.highlight.interfaces.HighLightInterface;
import zhy.com.highlight.position.OnBottomPosCallback;
import zhy.com.highlight.position.OnTopPosCallback;
import zhy.com.highlight.shape.CircleLightShape;
import zhy.com.highlight.shape.RectLightShape;

/**
 * Class name: HomeMainActivity
 * Specific description :新首页界面
 * 创建人: HUAHUA
 * @Time : 2018/8/24 19:32
 * 修改人：
 * @Time :
 * @since ：[quick|home]
 */

public class HomeMainActivity extends AppCompatActivity implements View.OnClickListener,HomeContract.IHomeView{
    private HighLight mHightLight;
    SharedPreferences sharedPreferences;
    private ImageView mimUserSetting,mimTor;
    private TextView mtvSettingPlan,mtvUserName,mtvUser_plan,mtvUserPlanTime;
    private TextView mtvWordbox,mtvPlanWay,mtvAllWords,mtvTodayWord;
    private TextView mtvOverDay,mtvNewWordsNum,mtvReviewWordsNum,mtvUnfamiliarWord;
    private LinearLayout mllUnfamiliarWordEnter;
    private Button mbtStartStudy;
    private ProgressBar mpgAllWord,mpgWord;

    private App app;

    HomeContract.IHomePresenter homePresenter;
    LoginContract.ILoginPresenter loginPresenter;

    private ActivityReceiver activityReceiver;

    Handler mHandler = null;
    Runnable mRunnable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        app = (App)getApplicationContext();

        // 创建BroadcastReceiver
        activityReceiver = new ActivityReceiver();
        // 创建IntentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(App.UPDATE_ACTION);
        registerReceiver(activityReceiver, filter);

        if(NetUtils.isConnected(HomeMainActivity.this)){
            Toast.makeText(this, "网络已连接", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "网络未连接，请连接再操作", Toast.LENGTH_SHORT).show();
        }

        homePresenter = new HomePresenterImpl(this);
        loginPresenter = new LoginPresenterImpl(this);


        setinitView();
        initData();
        setEvent();
        setdate();

        sharedPreferences = getSharedPreferences("is_first_in_data",MODE_PRIVATE);
        boolean isFirstIn = sharedPreferences.getBoolean("isFirstIn",true);
        if (isFirstIn) {
            showNextTipViewOnCreated();
            // 结束引导页面前，将状态改为false,下次启动的时候，判断不是第一次启动，就跳过引导页面
            sharedPreferences = getSharedPreferences("is_first_in_data",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstIn", false);
            editor.apply();
        }else {
            LogUtils.d("已非第一次安装");
        }
    }


    protected void onResume() {
        super.onResume();
        try {
            String sex = app.getSex();
            if (sex.equals("女")){
                mimTor.setImageResource(R.drawable.ic_tor_girl);
            }else {
                mimTor.setImageResource(R.drawable.ic_tor_boy);
            }
            updateUserName();
            setdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onPause() {
        super.onPause();
        try {
            if(mHandler != null)
                mHandler.removeCallbacks(mRunnable);
            updatePlan();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    /**
     * Method name :showNextKnownTipView
     * Specific description :用于引导页
     *    当界面布局完成显示next模式提示布局
     *    显示方法必须在onLayouted中调用
     *    适用于Activity及Fragment中使用
     *    可以直接在onCreated方法中调用
     * @return void
     */
    public  void showNextTipViewOnCreated(){

        mHightLight = new HighLight(HomeMainActivity.this)//
                .autoRemove(false)
                .enableNext()
                .setOnLayoutCallback(new HighLightInterface.OnLayoutCallback() {
                    @Override
                    public void onLayouted() {
                        //界面布局完成添加tipview
                        mHightLight.addHighLight(R.id.tv_setting_plan,R.layout.info_plan,new OnBottomPosCallback(60),new CircleLightShape())
                                .addHighLight(R.id.ll_unfamiliar_word_enter,R.layout.info_word_book,new OnBottomPosCallback(5),new CircleLightShape())
                                .addHighLight(R.id.bt_start_study,R.layout.info_star,new OnTopPosCallback(),new RectLightShape())
                                .addHighLight(R.id.im_user_setting,R.layout.info_login,new OnBottomPosCallback(60),new CircleLightShape());
                        //然后显示高亮布局
                        mHightLight.show();
                    }
                })
                .setClickCallback(new HighLight.OnClickCallback() {
                    @Override
                    public void onClick() {
//                        Toast.makeText(HomeActivity.this, "go!", Toast.LENGTH_SHORT).show();
                        mHightLight.next();
                    }
                });
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
        //进度条
        mpgAllWord = (ProgressBar) findViewById(R.id.pg_all_word);
        mpgWord = (ProgressBar) findViewById(R.id.pg_word);
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

    @Override
    public void onClick(View view) {

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
                    toUser(HomeMainActivity.this);
                    break;
                case R.id.tv_setting_plan:
                    myPlanDialog = new MyPlanDialog(HomeMainActivity.this);
                    myPlanDialog.show();
                    break;
                case R.id.ll_unfamiliar_word_enter:

                    break;
                case R.id.bt_start_study:
                    //开始背单词
                    if(NetUtils.isConnected(HomeMainActivity.this)){
                        if(app.getUserId().equals("111") || app.getUserId().equals("")){
                            Toast.makeText(HomeMainActivity.this, "请注册登录，以便于我们更好的为您服务（暂不支持未登录操作）", Toast.LENGTH_SHORT).show();
                        }else {
                            String planType = SPUtils.get(App.getContext(), "planType", "复习优先").toString();
                            ToastUtils.showLong(HomeMainActivity.this, planType);
                            if(planType.equals("复习优先")) {
                                getWordStatus(false);
                            } else {
                                getWordStatus(true);
                            }
                        }
                    }else {
                        Toast.makeText(HomeMainActivity.this, "网络未连接，请连接再操作", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method name : initData
     * Specific description :数据初始化
     *@return void
     */
    public void initData(){
        String sex = app.getSex();
        if (sex.equals("女")){
            mimTor.setImageResource(R.drawable.ic_tor_girl);
        }else {
            mimTor.setImageResource(R.drawable.ic_tor_boy);
        }
        //今日目标单词数默认值
        mtvTodayWord.setText(0+"/"+0);
        //因为
        OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
        onlineWordPresenter.getOnlineSprintType();
        onlineWordPresenter.GetAllWordTopicInfo();
    }

    @Override
    public void updatePlan() {
//刷新界面,从这个方法获取HashMap貌似可以，但不知会不会是个错误
        HashMap<String, String> map = setdate();
        OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl(HomeMainActivity.this);
        onlineWordPresenter.postToAddWordPlan(map);
        IWordsStatusDao wordsStatusDao = new WordsStatusImpl();
        if(wordsStatusDao.selectCountByStatusAndTopicId("review", app.getTopicId()) == 0) {
            HashMap<String, String> map2 = new HashMap<>();
            map2.put("userId", app.getUserId());
            map2.put("topicId", app.getTopicId());
            onlineWordPresenter.postToGetTopicIdWords(map2, false);
        }
    }

    @Override
    public void updateUserName() {
        if(!app.getNickName().equals("请登录")){
            mtvUserName.setText(app.getNickName());
        }else {
            mtvUserName.setText("未登录");
        }
    }

    @Override
    public void addPlanResult(final Boolean result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(result){
                    ToastUtils.showLong(HomeMainActivity.this, "计划制定完成");
                    setdate();
                }else {
                    ToastUtils.showLong(HomeMainActivity.this, "计划制定出现小问题，请留意网络状态");
                }
            }
        });
    }

    @Override
    public void overWordInfo() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    loginPresenter.allWordInfo(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    finish();
                }
            }
        });
    }

    /**
     * Method name : setdate
     * Specific description :塞数据
     *@return HashMap 希望不是个错误
     */
    @SuppressLint("SetTextI18n")
    @Override
    public HashMap<String, String> setdate(){

        //需要上传到服务器的数据
        final HashMap<String, String> map = new HashMap<>();

        IWordsStatusDao statusDao = new WordsStatusImpl();
        int allWords = 3500;
        String topicId = "12";
        String plan = SPUtils.get(App.getContext(), "box", "未选择词库").toString();
        String time = SPUtils.get(App.getContext(), "plan","2019-06-25").toString();
        String way = SPUtils.get(App.getContext(), "planType","学习优先").toString();
        try{
            //设置单词词库
            mtvUser_plan.setText(plan);
            //设置单词完成计划时间
            mtvUserPlanTime.setText("计划完成时间："+time+"-12");
            //设置单词练习的计划，复习优先或学习优先
            mtvPlanWay.setText(way);
            Object o = SPUtils.get(App.getContext(), "wordsBox", "");
            assert o != null;
            JSONArray jsonArray = JSONArray.parseArray(o.toString());
            int i = Integer.valueOf(SPUtils.get(App.getContext(), "boxPosition", "1").toString());
            com.alibaba.fastjson.JSONObject object = jsonArray.getJSONObject(i);
            allWords = Integer.valueOf(object.getString("wordAllCount"));
            topicId = object.getString("topicId");
            app.setTopicId(topicId);
            //存储当前选择的topicId
            SPUtils.put(App.getContext(), "topicId", topicId);
            //今日目标单词数

        }catch (Exception e){
            e.printStackTrace();
            mtvUser_plan.setText("四级");
        }

        //距离结束天数
        String a = SPUtils.get(App.getContext(), "plan","2018").toString();
        try {
            int daynum = homePresenter.calEndNum(a);
            mtvOverDay.setText(daynum+"天");
            mtvWordbox.setText(plan);
            mtvNewWordsNum.setText(statusDao.selectCountByStatusAndTopicIdToday("All", app.getTopicId())+"");
            mtvReviewWordsNum.setText(statusDao.selectCountByStatusAndTopicId("review", app.getTopicId())+"");
            mtvUnfamiliarWord.setText(statusDao.selectCountByStatusAndTopicId("new", app.getTopicId())+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //完成单词数
        int overWords = statusDao.selectCountByStatusAndTopicId("grasp", app.getTopicId());
        int todayOverWords = statusDao.selectCountByStatusAndTopicIdToday("grasp", app.getTopicId());
        String today = SPUtils.get(App.getContext(), "dateNum", 0).toString();
        //        tv_word_finish.setText(overWords+"");
//        tv_word_over.setText(statusDao.selectCountByStatusAndTopicId("review", app.getTopicId())+"");

        //词库单词数量
        mtvAllWords.setText(overWords+"/"+allWords);
        mtvTodayWord.setText(todayOverWords+"/"+ today);
        //进度条添加数据
        mpgAllWord.setMax(allWords);
        mpgAllWord.setProgress(overWords);
        mpgWord.setMax(Integer.parseInt(today));
        mpgWord.setProgress(todayOverWords);
        //进度数
//        my_word_plan_progressbar.setMax(allWords);
//        my_word_plan_progressbar.setProgress(overWords);

        map.put("userId", app.getUserId());
        map.put("topicId", topicId);
        map.put("endTime", "2018-12-25");
        if(mtvPlanWay.getText().toString().equals("复习优先")){
            map.put("model", "211");
        }else {
            map.put("model", "210");
        }
        return map;
    }

    public class ActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新界面,从这个方法获取HashMap貌似可以，但不知会不会是个错误
            HashMap<String, String> map = setdate();
            OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl(HomeMainActivity.this);
            onlineWordPresenter.postToAddWordPlan(map);
            IWordsStatusDao wordsStatusDao = new WordsStatusImpl();
            if(wordsStatusDao.selectCountByStatusAndTopicId("review", app.getTopicId()) == 0) {
                HashMap<String, String> map2 = new HashMap<>();
                map2.put("userId", app.getUserId());
                map2.put("topicId", app.getTopicId());
                onlineWordPresenter.postToGetTopicIdWords(map2, false);
            }
        }
    }

    public void getWordStatus(Boolean learn){

        IWordsStatusDao statusDao = new WordsStatusImpl();
        List<Words_Status> wordLearn = statusDao.selectByStatusAndTopicId("learn_", app.getTopicId());
        List<Words_Status> wordReview = statusDao.selectByStatusAndTopicId("review", app.getTopicId());
        if(wordLearn.size() != 0){
            if(learn){
                wordLearn.addAll(wordReview);
                app.setStatusList(wordLearn);
            }else {
                wordReview.addAll(wordLearn);
                app.setStatusList(wordReview);
            }
            Intent intent = new Intent(HomeMainActivity.this, WordsActivity.class);
            startActivity(intent);
        }else {
            popWindow(HomeMainActivity.this, learn);
        }
    }

    public void popWindow(final Context context, final Boolean learn){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("获取单词进行练习");
//        final EditText editText;
//        builder.setView(editText = new EditText(AthleticsActivity.this));
        builder.setIcon(R.drawable.boy);
        builder.setNeutralButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("冲鸭！皮卡丘", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
//                final HashMap<String, String> map = new HashMap<>();
//                map.put("userId", app.getUserId());
//                map.put("topicId", app.getTopicId());
//                String planType = SPUtils.get(App.getContext(), "planType", "复习优先").toString();
//                if(planType.equals("复习优先")) {
//                    onlineWordPresenter.getOnlineWordReviewOrLearn(map, "review");
//                } else {
//                    onlineWordPresenter.getOnlineWordReviewOrLearn(map, "learn");
//                }
                final HashMap<String, String> map = new HashMap<>();
                map.put("userId", app.getUserId());
                map.put("topicId", app.getTopicId());
                map.put("needNum", SPUtils.get(App.getContext(), "dateNum", 0).toString());
                onlineWordPresenter.getDynamicWordInfo(map);
                final ProgressDialog progressDialog = new ProgressDialog(HomeMainActivity.this);
                progressDialog.setIcon(R.drawable.boy);
                progressDialog.setTitle("请稍等");
                progressDialog.setMessage("正在加载中");
                progressDialog.show();
                mHandler = new Handler();
//                mRunnable = ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(new Runnable() {
//                    @Override
//                    public void run()
//                    {
//
//                        IWordsStatusDao iWordsStatusDao = new WordsStatusImpl();
//                        if(iWordsStatusDao.selectCountByStatusAndTopicId("learn_", app.getTopicId()) != 0){
//                            progressDialog.dismiss();
//                            String planType = SPUtils.get(App.getContext(), "planType", "复习优先").toString();
//                            if(planType.equals("复习优先")) {
//                                getWordStatus(false);
//                            } else {
//                                getWordStatus(true);
//                            }
//                        }
//
//                    }
//                });
                mRunnable = new Runnable() {
                    @Override
                    public void run()
                    {

                        IWordsStatusDao iWordsStatusDao = new WordsStatusImpl();
                        if(iWordsStatusDao.selectCountByStatusAndTopicId("learn_", app.getTopicId()) != 0){
                            progressDialog.dismiss();
                            String planType = SPUtils.get(App.getContext(), "planType", "复习优先").toString();
                            if(planType.equals("复习优先")) {
                                getWordStatus(false);
                            } else {
                                getWordStatus(true);
                            }
                        }

                    }
                };
                mHandler.postDelayed(mRunnable, 2000);
            }
        });
        builder.show();
    }

    public void toUser(Context context){

        if(app.getUserId().equals("111") || app.getUserId() == null) {
            startActivity(new Intent(context, LoginActivity.class));
        }else {
            startActivity(new Intent(context, UserMessageActivity.class));
        }

    }

    protected void onDestroy(){
        super.onDestroy();
        //注销广播
        unregisterReceiver(activityReceiver);
        SPUtils.put(App.getContext(), "userId", app.getUserId());
        SPUtils.put(App.getContext(), "nickname", app.getNickName());
        SPUtils.put(App.getContext(), "sex", app.getSex());
    }
}
