package com.echo.quick.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.common.Constants;
import com.echo.quick.common.PreferenceConstants;
import com.echo.quick.common.PreferenceManager;
import com.echo.quick.contracts.HomeContract;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.Lexicon;
import com.echo.quick.presenters.HomePresenterImpl;
import com.echo.quick.presenters.LoginPresenterImpl;
import com.echo.quick.presenters.OnlineWordPresenterImpl;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.SPUtils;
import com.google.gson.internal.LinkedTreeMap;

import org.angmarch.views.NiceSpinner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InitPlanActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_plan;
    private NiceSpinner mniceSpinner3,mniceSpinner4;
    List<String> dataset1,dataset2;
    private RadioGroup mradioGroup;
    private HomeContract.IHomePresenter homePresenter;
    OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_plan);
        ActivityManager.getInstance().addActivity(this);
        homePresenter = new HomePresenterImpl();
        initView();
        initData();
    }

    private void initView(){
        btn_plan = (Button)findViewById(R.id.btn_plan);
        mniceSpinner3 = (NiceSpinner)findViewById(R.id.nice_spinner1);
        mniceSpinner4 = (NiceSpinner)findViewById(R.id.nice_spinner2);
        mradioGroup = (RadioGroup)findViewById(R.id.init_radiogroup);
        btn_plan.setOnClickListener(this);
    }

    private void initData(){
        dataset1 = getLexiconName();
        mniceSpinner3.attachDataSource(dataset1);
        int year = homePresenter.getYear();
        int mouth = homePresenter.getMouth();
        String str1="";
        String str2="";
        String str3="";
        String str4="";
        if (mouth < 6){
            str1 = year+"-"+06;
            str2 = year+"-"+12;
            str3 = (year+1)+"-"+06;
            str4 = (year+1)+"-"+12;
        }else {
            str1 = year+"-"+12;
            str2 = (year+1)+"-"+06;
            str3 = (year+1)+"-"+12;
            str4 = (year+2)+"-"+06;
        }
        dataset2 = new LinkedList<>(Arrays.asList(str1, str2, str3, str4));
        mniceSpinner4.attachDataSource(dataset2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_plan:
                Map hs = getMyValue();
                LogUtils.d("我的计划====词库id:"+hs.get("topicId")+",词库名称:"+hs.get("box")+",目标时间:"+hs.get("plan") + ",选中框的值:"+hs.get("planType"));
                //选中词库
                SPUtils.put(App.getContext(), PreferenceConstants.CURRENT_PLAN_LEXICON, hs.get("box"));
                //选择的计划完成日期
                SPUtils.put(App.getContext(), PreferenceConstants.PLAN_TIME, hs.get("plan"));
                //选择的背单词模式
                SPUtils.put(App.getContext(), PreferenceConstants.PLAN_TYPE, hs.get("planType"));
                //存词库的id
                SPUtils.put(App.getContext(), PreferenceConstants.LEXICON_ID, hs.get("topicId"));
                //存词库的数量
                SPUtils.put(App.getContext(), PreferenceConstants.LEXICON_ALLCOUNT, hs.get("allWords"));

                try {
                    //存入目标数
                    int datenum = homePresenter.calMyPlanNmu(hs.get("plan").toString(), (Integer)hs.get("allWords"));
                    System.out.printf("=================每日的目标数："+datenum);
                    SPUtils.put(App.getContext(), "dateNum", datenum);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //这里才真的登录成功
                PreferenceManager.getInstance().put(PreferenceConstants.USERLOGIN,"true");
                String isLogin = (String)SPUtils.get(App.getContext(),PreferenceConstants.USERLOGIN,"false");
                LogUtils.d("注册的计划页面: isLogin:"+isLogin);
                //这里上传注册的计划信息
//                onlineWordPresenter.postToAddWordPlan(setPlanData());
                Intent intent = new Intent();
                intent.setAction("com.zjx.action.UPDATE_ACTION");
                sendBroadcast(intent);
                startActivity(new Intent(InitPlanActivity.this, HomeMainActivity.class));
                finish();
                break;

            default:

                break;

        }
    }


    /**
     * setPlanData()
     * 用于设置计划的数据的进入
     * @return
     */
    public HashMap<String, String> setPlanData() {

        //需要上传到服务器的数据
        final HashMap<String, String> map = new HashMap<>();
        //传用户手机号
        String userphone = (String) SPUtils.get(App.getContext(), PreferenceConstants.USERPHONE,"");
        //传用户计划词库id
        String topicId = (String) SPUtils.get(App.getContext(), PreferenceConstants.USERPHONE,"");
        //传用户计划的时间
        String planTime = (String) SPUtils.get(App.getContext(), PreferenceConstants.PLAN_TIME,"");
        //传用户计划的模式
        String planType = (String) SPUtils.get(App.getContext(), PreferenceConstants.PLAN_TYPE,"复习优先");
        //全部放入hashmap
        map.put("userId", userphone);
        map.put("topicId", topicId);
        map.put("endTime", planTime);

        //判断应该学习模式的状态码应该是哪个
        if("复习优先".equals(planType)){
            map.put("model", String.valueOf(Constants.REVIEW_FIRST));
        }else {
            map.put("model", String.valueOf(Constants.LEARN_FIRST));
        }
        return map;

    }


    private HashMap getMyValue(){
        HashMap hs = new HashMap();

        //词库类型
        int selectedIndex1 = mniceSpinner3.getSelectedIndex();
        String datapick1 = dataset1.get(selectedIndex1);
        hs.put("box", datapick1);
        hs.put("topicId", getLexiconMessage(datapick1).topicId);
        Integer allWords = Integer.valueOf(getLexiconMessage(datapick1).wordAllCount);
        hs.put("allWords",allWords );

        //目标时间
        int selectedIndex2 = mniceSpinner4.getSelectedIndex();
        String datapick2 = dataset2.get(selectedIndex2);
        hs.put("plan", datapick2);

        //选中框的值
        int checkedRadioButtonId = mradioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)findViewById(checkedRadioButtonId);
        String planType = radioButton.getText().toString();
        hs.put("planType", planType);

        return hs;
    }


    /**
     * 用于查找词库的信息
     * @param topicName
     * @return
     */
    private Lexicon getLexiconMessage(String topicName){
        Map lexiconList = new HashMap<>();
        lexiconList = SPUtils.getMap(App.getContext(), PreferenceConstants.LEXICON);
        if (lexiconList.isEmpty()){
            OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
            onlineWordPresenter.GetAllWordTopicInfo();
            lexiconList = SPUtils.getMap(App.getContext(), PreferenceConstants.LEXICON);
        }
        Map lexiconMap = (Map) lexiconList.get(topicName);
        String topicId = (String) lexiconMap.get("topicId");
        String topicName2 = (String) lexiconMap.get("topicName");
        String tableName = (String) lexiconMap.get("tableName");
        String wordAllCount = (String) lexiconMap.get("wordAllCount");
        Lexicon lexicon = new Lexicon(topicId,topicName2,tableName,wordAllCount);
        return lexicon;
    }

    /**
     * 得到所有的词库名称列表
     */
    private  List getLexiconName(){
        List lexiconNameList = new ArrayList<>();
        lexiconNameList = SPUtils.getDataList(App.getContext(), PreferenceConstants.LEXICONNAME);
        if (0==lexiconNameList.size()){
            OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
            onlineWordPresenter.GetAllWordTopicInfo();
            lexiconNameList = SPUtils.getDataList(App.getContext(), PreferenceConstants.LEXICONNAME);
        }else {
            lexiconNameList.add("空词库");
        }
        return lexiconNameList;
    }

}
