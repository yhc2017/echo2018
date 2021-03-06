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
 * Class name: HomeActivity
 * Specific description :首页控件重新换血
 * 创建人: HUAHUA
 * @Time :1.0 , 2018/8/5 13:28
 * 修改人：
 * @Time :
 * @since ：[产品|模块版本]
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener,HomeContract.IHomeView{

    private HighLight mHightLight;
    SharedPreferences sharedPreferences;
    private TextView tv_way,tv_user_name,tv_from,tv_obtion,tv_over_day,im_setting,tv_word_finish,tv_word_obtion,tv_word_over,tv_word_num;
    private ProgressBar my_word_plan_progressbar;
    private Button bt_start_study;
    private ImageView im_tor;
    private App app;
    int datenum2 = 0;
    //线程
    Handler mHandler = null;
    Runnable mRunnable = null;

    HomeContract.IHomePresenter homePresenter;
    LoginContract.ILoginPresenter loginPresenter;

    private ActivityReceiver activityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ActivityManager.getInstance().addActivity(this);
        app = (App)getApplication();

        // 创建BroadcastReceiver
        activityReceiver = new ActivityReceiver();
        // 创建IntentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(App.UPDATE_ACTION);
        registerReceiver(activityReceiver, filter);

        if(NetUtils.isConnected(HomeActivity.this)){
            Toast.makeText(this, "网络已连接", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "网络未连接，请连接再操作", Toast.LENGTH_SHORT).show();
        }

        homePresenter = new HomePresenterImpl(this);
        loginPresenter = new LoginPresenterImpl(this);

        //初始化
        initView();
        initData();
        setData();

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
                im_tor.setImageResource(R.drawable.ic_tor_girl);
            }else {
                im_tor.setImageResource(R.drawable.ic_tor_boy);
            }
            updateUserName();
           setData();
        } catch (Exception e) {
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
        tv_over_day = (TextView)findViewById(R.id.tv_over_day);

        //绑定监听
        im_setting.setOnClickListener(this);
        tv_word_finish.setOnClickListener(this);
        tv_word_over.setOnClickListener(this);
        bt_start_study.setOnClickListener(this);
        im_tor.setOnClickListener(this);

    }

    /**
     * Method name : initData
     * Specific description :数据初始化
     *@return void
     */
    public void initData(){
        String sex = app.getSex();
        if (sex.equals("女")){
            im_tor.setImageResource(R.drawable.ic_tor_girl);
        }else {
            im_tor.setImageResource(R.drawable.ic_tor_boy);
        }
        //今日目标单词数默认值
        System.out.printf("=================每日的目标数："+0);
        tv_word_obtion.setText(""+0);
        //因为
        OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
        onlineWordPresenter.getOnlineSprintType();
        onlineWordPresenter.GetAllWordTopicInfo();
    }

    /**
     * Method name : setdate
     * Specific description :塞数据
     *@return HashMap 希望不是个错误
     */
    @SuppressLint("SetTextI18n")
    @Override
    public HashMap<String, String> setData(){

        //需要上传到服务器的数据
        final HashMap<String, String> map = new HashMap<>();

        IWordsStatusDao statusDao = new WordsStatusImpl();
        int allWords = 3500;
        String topicId = "12";
        try{
            tv_from.setText(SPUtils.get(App.getContext(), "box","四级").toString().substring(0, 2));
            tv_obtion.setText(SPUtils.get(App.getContext(), "plan","2018").toString());
            tv_way.setText(SPUtils.get(App.getContext(), "planType","复习优先").toString());
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
            tv_word_obtion.setText(SPUtils.get(App.getContext(), "dateNum",datenum2).toString());
        }catch (Exception e){
            e.printStackTrace();
            tv_from.setText("四级");
            tv_obtion.setText("2018-12");
        }

        //距离结束天数
        String a = SPUtils.get(App.getContext(), "plan","2018").toString();
        try {
            int daynum = homePresenter.calculateEndNum(a);
            tv_over_day.setText(""+daynum);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //完成单词数
        int overWords = statusDao.selectCountByStatusAndTopicId("grasp", app.getTopicId());
        tv_word_finish.setText(overWords+"");
        tv_word_over.setText(statusDao.selectCountByStatusAndTopicId("review", app.getTopicId())+"");
        
        //词库单词数量
        tv_word_num.setText(overWords+"/"+allWords);
        //进度数
        my_word_plan_progressbar.setMax(allWords);
        my_word_plan_progressbar.setProgress(overWords);

        map.put("userId", app.getUserId());
        map.put("topicId", topicId);
        map.put("endTime", tv_obtion.getText().toString()+"-25");
        if(tv_way.getText().toString().equals("复习优先")){
            map.put("model", "211");
        }else {
            map.put("model", "210");
        }
        return map;
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

        mHightLight = new HighLight(HomeActivity.this)//
                .autoRemove(false)
                .enableNext()
                .setOnLayoutCallback(new HighLightInterface.OnLayoutCallback() {
                    @Override
                    public void onLayouted() {
                        //界面布局完成添加tipview
                        mHightLight.addHighLight(R.id.im_setting,R.layout.info_plan,new OnBottomPosCallback(60),new RectLightShape())
                                .addHighLight(R.id.tv_word_over,R.layout.info_word_book,new OnBottomPosCallback(5),new CircleLightShape())
                                .addHighLight(R.id.bt_start_study,R.layout.info_star,new OnTopPosCallback(),new RectLightShape())
                                .addHighLight(R.id.im_tor,R.layout.info_login,new OnBottomPosCallback(60),new CircleLightShape());
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

    @Override
    public void onClick(View view) {
        Intent intent;
        MyPlanDialog myPlanDialog = new MyPlanDialog(HomeActivity.this);
        switch (view.getId()){
            case R.id.tv_word_over:
                intent = new Intent(HomeActivity.this, StrangeWordsListActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_word_finish:
                intent = new Intent(HomeActivity.this, StrangeWordsListActivity.class);
                startActivity(intent);
                break;

            case R.id.bt_start_study:
                if(NetUtils.isConnected(HomeActivity.this)){
                    if(app.getUserId().equals("111") || app.getUserId().equals("")){
                        Toast.makeText(this, "请注册登录，以便于我们更好的为您服务（暂不支持未登录操作）", Toast.LENGTH_SHORT).show();
                    }else {
                        String planType = SPUtils.get(App.getContext(), "planType", "复习优先").toString();
                        ToastUtils.showLong(HomeActivity.this, planType);
                        if(planType.equals("复习优先")) {
                            getWordStatus(false);
                        } else {
                            getWordStatus(true);
                        }
                    }
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
                myPlanDialog = new MyPlanDialog(HomeActivity.this);
                myPlanDialog.show();
                break;

            default:
                break;

        }
    }

    public void toUser(Context context){

        if(app.getUserId().equals("111") || app.getUserId() == null) {
            startActivity(new Intent(context, LoginActivity.class));
        }else {
            startActivity(new Intent(context, UserMessageActivity.class));
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
                final HashMap<String, String> map = new HashMap<>();
                map.put("userId", app.getUserId());
                map.put("topicId", app.getTopicId());
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
            Intent intent = new Intent(HomeActivity.this, WordsActivity.class);
            startActivity(intent);
        }else {
            popWindow(HomeActivity.this, learn);
        }
    }

    @Override
    public void updateUserName(){
        if(!app.getNickName().equals("请登录")){
            tv_user_name.setText(app.getNickName());
        }else {
            tv_user_name.setText("未登录");

        }
    }

    @Override
    public void addPlanResult(final Boolean result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(result){
                    ToastUtils.showLong(HomeActivity.this, "计划制定完成");
                    setData();
                }else {
                    ToastUtils.showLong(HomeActivity.this, "计划制定出现小问题，请留意网络状态");
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


    protected void onPause() {
        super.onPause();
        try {
            if(mHandler != null)
            mHandler.removeCallbacks(mRunnable);
            setData();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void updatePlan() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                //刷新界面,从这个方法获取HashMap貌似可以，但不知会不会是个错误
//                HashMap<String, String> map = setdate();
//                OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
//                onlineWordPresenter.postToAddWordPlan(map);
//            }
//        });
    }

    public class ActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新界面,从这个方法获取HashMap貌似可以，但不知会不会是个错误
            HashMap<String, String> map = setData();
            OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl(HomeActivity.this);
            onlineWordPresenter.postToAddWordPlan(map);
            IWordsStatusDao wordsStatusDao = new WordsStatusImpl();
            if(wordsStatusDao.selectCountByStatusAndTopicId("all", app.getTopicId()) == 0) {
                HashMap<String, String> map2 = new HashMap<>();
                map2.put("userId", app.getUserId());
                map2.put("topicId", app.getTopicId());
                onlineWordPresenter.postToGetTopicIdWords(map2, false);
            }
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
