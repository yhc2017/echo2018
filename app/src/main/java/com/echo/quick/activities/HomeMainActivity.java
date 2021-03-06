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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.common.Constants;
import com.echo.quick.common.PreferenceConstants;
import com.echo.quick.common.PreferenceManager;
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

import static com.echo.quick.common.PreferenceConstants.USERLOGIN;

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
    private static final String TAG = HomeMainActivity.class.getName();
    private HighLight mHightLight;
    SharedPreferences sharedPreferences;
    private ImageView mimUserSetting,mimTor;
    private TextView mtvSettingPlan,mtvUserName,mtvUser_plan,mtvUserPlanTime;
    private TextView mtvWordbox,mtvPlanWay,mtvAllWords,mtvTodayWord;
    private TextView mtvOverDay,mtvNewWordsNum,mtvReviewWordsNum,mtvUnfamiliarWord;
    private LinearLayout mllUnfamiliarWordEnter;
    private Button mbtStartStudy;
    private ProgressBar mpgAllWord,mpgWord;
    OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl(HomeMainActivity.this);
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
        ActivityManager.getInstance().addActivity(this);
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
        setData();

        /**
         * 这是起始动画的部分，判断是不是第一次安装，是的话就演示引导页，否则不出现
         */
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
            String sex = (String) SPUtils.get(App.getContext(),PreferenceConstants.USERSEX,"男");
            if (sex.equals("女")){
                mimTor.setImageResource(R.drawable.ic_tor_girl);
            }else {
                mimTor.setImageResource(R.drawable.ic_tor_boy);
            }
            updateUserName();
            updatePlan();
            setData();
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
                    if(PreferenceManager.getInstance().get(USERLOGIN,"false").equals("false")){
                        Toast.makeText(HomeMainActivity.this, "请注册登录，以便于我们更好的为您服务（暂不支持未登录操作）", Toast.LENGTH_SHORT).show();
                    }else{
                        myPlanDialog = new MyPlanDialog(HomeMainActivity.this);
                        myPlanDialog.show();
                    }
                    break;

                case R.id.ll_unfamiliar_word_enter:
                    intent = new Intent(HomeMainActivity.this, StrangeWordsListActivity.class);
                    startActivity(intent);
                    break;

                case R.id.bt_start_study:
                    //开始背单词
                    if(NetUtils.isConnected(HomeMainActivity.this)){
                        if(PreferenceManager.getInstance().get(USERLOGIN,"false").equals("false")){
                            Toast.makeText(HomeMainActivity.this, "请注册登录，以便于我们更好的为您服务（暂不支持未登录操作）", Toast.LENGTH_SHORT).show();
                        }else {
                            String planType = SPUtils.get(App.getContext(), PreferenceConstants.PLAN_TYPE, "复习优先").toString();
//                            ToastUtils.showLong(HomeMainActivity.this, planType);
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
        String sex = (String) SPUtils.get(App.getContext(),PreferenceConstants.USERSEX,"男");
        if (sex.equals("女")){
            mimTor.setImageResource(R.drawable.ic_tor_girl);
        }else {
            mimTor.setImageResource(R.drawable.ic_tor_boy);
        }
        //因为
        OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
        onlineWordPresenter.getOnlineSprintType();
        onlineWordPresenter.GetAllWordTopicInfo();
    }

    @Override
    public void updatePlan() {
        //刷新界面,从这个方法获取HashMap貌似可以，但不知会不会是个错误
        onlineWordPresenter.postToAddWordPlan(setData());
        IWordsStatusDao wordsStatusDao = new WordsStatusImpl();
        String userId = (String) SPUtils.get(App.getContext(),PreferenceConstants.USERPHONE,"");
        String topicId = (String) SPUtils.get(App.getContext(),PreferenceConstants.LEXICON_ID,"");
        if(wordsStatusDao.selectCountByStatusAndTopicId("review", topicId) == 0) {
            HashMap<String, String> map = new HashMap<>();
            map.put("userId", userId);
            map.put("topicId", topicId);
            onlineWordPresenter.postToGetTopicIdWords(map, false);
        }
    }

    @Override
    public void updateUserName() {
        if(PreferenceManager.getInstance().get(USERLOGIN,"false").equals("true")){
            String userName = (String) SPUtils.get(App.getContext(),PreferenceConstants.USERNAME,"未登录");
            mtvUserName.setText(userName);
        }else {
           mtvUserName.setText("未登录");
        }
    }

    @Override
    public void addPlanResult(final Boolean result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(PreferenceManager.getInstance().get(USERLOGIN,"false").equals("true")){
                    if(result){
                        ToastUtils.showShort(HomeMainActivity.this, "计划制定完成");
                        setData();
                    }else {
                        ToastUtils.showShort(HomeMainActivity.this, "计划制定出现问题，请重新制定当前计划");
                    }
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
                    boolean isLogin = "true".equals(SPUtils.get(App.getContext(),PreferenceConstants.USERLOGIN,"false"));
                    loginPresenter.allWordInfo(isLogin);
                } catch (JSONException e) {
                    e.printStackTrace();
                    finish();
                }
            }
        });
    }


    @SuppressLint("SetTextI18n")
    @Override
    public HashMap<String, String> setData(){
        //需要上传到服务器的数据
        final HashMap<String, String> map = new HashMap<>();
        //这里也需要存到数据库
        IWordsStatusDao statusDao = new WordsStatusImpl();
        //从这个轻量级数据库中获取用户的当前计划信息
        String userPhone = (String) SPUtils.get(App.getContext(),PreferenceConstants.USERPHONE,"");
        String planName = (String) SPUtils.get(App.getContext(),PreferenceConstants.CURRENT_PLAN_LEXICON,"未选择词库");
        String planTime = (String) SPUtils.get(App.getContext(),PreferenceConstants.PLAN_TIME,"通过时间");
        Integer wordAllCount = (Integer) SPUtils.get(App.getContext(),PreferenceConstants.LEXICON_ALLCOUNT,0);
        String planType = (String) SPUtils.get(App.getContext(),PreferenceConstants.PLAN_TYPE,"");

        String topicId = (String) SPUtils.get(App.getContext(),PreferenceConstants.LEXICON_ID,"");
        LogUtils.d(TAG, "1.HomeMainActivity->电话-计划词库-时间-模式-词库id> "+userPhone+"-"+planName+"-"+planTime+"-"+planType+"-"+topicId);

        //判断用户是否登录，如果登录就设置页面的信息
        try{
            if("true".equals(PreferenceManager.getInstance().get(PreferenceConstants.USERLOGIN,"flase"))){

                //设置单词词库
                mtvUser_plan.setText("通过"+planName);
                //设置单词完成计划时间
                mtvUserPlanTime.setText("计划完成时间："+planTime);
            }else{
                //将词库和计划时间设置为空
                mtvUser_plan.setText("");
                mtvUserPlanTime.setText("");
            }

            //设置单词练习的计划，复习优先或学习优先
            mtvPlanWay.setText(planType);
            app.setTopicId(topicId);

            LogUtils.d(TAG, "2.HomeMainActivity->电话-计划词库-时间-模式-词库id> "+userPhone+"-"+planName+"-"+planTime+"-"+planType+"-"+topicId);

            //todo 今日目标单词数 2018-12-08 TanzJ 没有增加？

        }catch (Exception e){
            e.printStackTrace();
            if((PreferenceManager.getInstance().get(USERLOGIN,"false")).equals("false")){
                mtvUser_plan.setText("通过"+planName);
                //设置单词完成计划时间
                mtvUserPlanTime.setText("计划完成时间："+planTime);
            }else{
                mtvUser_plan.setText("通过"+planName);
                //设置单词完成计划时间
                mtvUserPlanTime.setText("计划完成时间："+planTime);
            }
        }


        //新学习单词数量，复习单词数量，不熟悉单词数量的数据绑定
        try {
            int dayNum = homePresenter.calculateEndNum(planTime);
            mtvOverDay.setText(dayNum+"天");
            mtvWordbox.setText(planName);

            Log.d(TAG, "setData: mtvNewWordsNum:"+statusDao.selectCountByStatusAndTopicIdToday("All", topicId)+" topicId"+topicId);
            //新学习单词数量
            mtvNewWordsNum.setText(statusDao.selectCountByStatusAndTopicIdToday("All", topicId)+"");
            //今日复习
            mtvReviewWordsNum.setText(statusDao.selectCountByStatusAndTopicId("review", topicId)+"");
            //不熟悉的单词
            mtvUnfamiliarWord.setText(statusDao.selectCountByStatusAndTopicId("new", topicId)+"");

        } catch (ParseException e) {
            Log.d(TAG, "setData------->: "+e);
        }

        //完成单词数
        int finishWords = statusDao.selectCountByStatusAndTopicId(PreferenceConstants.GRASPWORDNUMBER, topicId);
        int todayFinishWords = statusDao.selectCountByStatusAndTopicIdToday(PreferenceConstants.GRASPWORDNUMBER, topicId);
        //每日目标数
        String todayPlanNumber = SPUtils.get(App.getContext(), PreferenceConstants.DATEPLANNUM, 0).toString();
        //        tv_word_finish.setText(overWords+"");
//        tv_word_over.setText(statusDao.selectCountByStatusAndTopicId("review", app.getTopicId())+"");
        int todayInt =Integer.parseInt(todayPlanNumber);
        if(todayInt>=todayFinishWords){
            mtvTodayWord.setText(todayFinishWords+"/"+ todayPlanNumber);
        }else{
            mtvTodayWord.setText(todayFinishWords+"/"+ todayFinishWords);
        }
        //总体进度的数字显示
        mtvAllWords.setText(finishWords+"/"+wordAllCount);
//        mtvTodayWord.setText(todayOverWords+"/"+ today);

        //进度条添加数据
        mpgAllWord.setMax(wordAllCount);
        mpgAllWord.setProgress(finishWords);
        if(todayFinishWords>=Integer.parseInt(todayPlanNumber)){
            mpgWord.setMax(todayFinishWords);
        }else{
            mpgWord.setMax(Integer.parseInt(todayPlanNumber));
        }
        mpgWord.setProgress(todayFinishWords);
        //进度数
//        my_word_plan_progressbar.setMax(allWords);
//        my_word_plan_progressbar.setProgress(overWords);

        map.put("userId", userPhone);
        map.put("topicId", topicId);
        //一定要日期形式，不然添加不了计划时间
        map.put("endTime", planTime+"-12");

        //判断应该学习模式的状态码应该是哪个,注意一定要去字符串空格trim()
        if("复习优先".equals(mtvPlanWay.getText().toString().trim())){
            map.put("model", Constants.REVIEW_FIRST);
        }else {
            map.put("model", Constants.LEARN_FIRST);
        }
        return map;
    }

    /**
     * Specific description :用于处理背完单词以后的界面刷新
     * 创建人: HUAHUA
     * @Time :1.0 , 2018/11/26 20:19
     */
    public class ActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新界面,从这个方法获取HashMap貌似可以，但不知会不会是个错误
            HashMap<String, String> map = setData();
            //添加单词计划
            onlineWordPresenter.postToAddWordPlan(map);
            Log.d(TAG, "IntentAction: "+intent.getAction());
            IWordsStatusDao wordsStatusDao = new WordsStatusImpl();
            String userId = (String) SPUtils.get(App.getContext(),PreferenceConstants.USERPHONE,"");
            String topicId = (String) SPUtils.get(App.getContext(),PreferenceConstants.LEXICON_ID,"");
            //刷新界面的同时，如果本地词库待复习的单词数量为0，则发送请求去获取单词
            if(wordsStatusDao.selectCountByStatusAndTopicId("review", topicId) == 0) {
                HashMap<String, String> map2 = new HashMap<>();
                map2.put("userId", userId);
                map2.put("topicId", topicId);
                boolean isLogin = "true".equals(SPUtils.get(App.getContext(),PreferenceConstants.USERLOGIN,"false"));
                onlineWordPresenter.postToGetTopicIdWords(map2, isLogin);
            }
        }
      }


    /**
     * Method name : getWordStatus
     * Specific description :用于判断单词的状态
     */
    public void getWordStatus(Boolean isLearn){

        IWordsStatusDao statusDao = new WordsStatusImpl();
        String topicId = (String) SPUtils.get(App.getContext(),PreferenceConstants.LEXICON_ID,"");
        List<Words_Status> wordLearn = statusDao.selectByStatusAndTopicId("learn_", topicId);
        List<Words_Status> wordReview = statusDao.selectByStatusAndTopicId("review", topicId);
        if(wordLearn.size() != 0){
            if(isLearn){
                wordLearn.addAll(wordReview);
                app.setStatusList(wordLearn);
            }else {
                wordReview.addAll(wordLearn);
                app.setStatusList(wordReview);
            }
            Intent intent = new Intent(HomeMainActivity.this, WordsActivity.class);
            startActivity(intent);
        }else {
            popWindow(HomeMainActivity.this, isLearn);
        }
    }

    /**
     * Method name : popWindow
     * Specific description :开始背单词的按钮
     */
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

                final HashMap<String, String> map = new HashMap<>();
                String userId = (String) SPUtils.get(App.getContext(),PreferenceConstants.USERPHONE,"");
                String topicId = (String) SPUtils.get(App.getContext(),PreferenceConstants.LEXICON_ID,"");
                map.put("userId", userId);
                map.put("topicId", topicId);
                map.put("needNum", SPUtils.get(App.getContext(), "dateNum", 0).toString());
                onlineWordPresenter.getDynamicWordInfo(map);

                //封装的Dialog
                final ProgressDialog progressDialog = new ProgressDialog(HomeMainActivity.this);
                progressDialog.setIcon(R.drawable.boy);
                progressDialog.setTitle("请稍等");
                progressDialog.setMessage("正在加载中");
                progressDialog.show();
                mHandler = new Handler();

                //@author:@TanzJ
                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        IWordsStatusDao iWordsStatusDao = new WordsStatusImpl();
                        String topicId = (String) SPUtils.get(App.getContext(),PreferenceConstants.LEXICON_ID,"");
                        if(iWordsStatusDao.selectCountByStatusAndTopicId("learn_", topicId) != 0){
                            progressDialog.dismiss();
                            String planType = SPUtils.get(App.getContext(), PreferenceConstants.PLAN_TYPE, "复习优先").toString();
                            if("复习优先".equals(planType)) {
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

    /**
     * Method name : toUser（）
     * Specific description :用于判断用户是否登录，如果有登录就跳转到用户的信息界面
     */
    public void toUser(Context context){

        if(PreferenceManager.getInstance().get(USERLOGIN,"false").equals("false")) {
            startActivity(new Intent(context, LoginActivity.class));

        }else {
            startActivity(new Intent(context, UserMessageActivity.class));
        }

    }

    /**
     * Method name : onDestroy（）
     * Specific description :退出这个页面的时候，所要做的一些操作
     */
    protected void onDestroy(){
        super.onDestroy();
        //注销广播
        unregisterReceiver(activityReceiver);
//        //保存用户界面的信息
//        SPUtils.put(App.getContext(), PreferenceConstants.USERPHONE, app.getUserId());
//        SPUtils.put(App.getContext(), "nickname", app.getNickName());
//        SPUtils.put(App.getContext(), "sex", app.getSex());
    }
}
