package com.echo.quick.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.activities.R;
import com.echo.quick.common.PreferenceConstants;
import com.echo.quick.contracts.HomeContract;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.Lexicon;
import com.echo.quick.presenters.HomePresenterImpl;
import com.echo.quick.presenters.OnlineWordPresenterImpl;

import org.angmarch.views.NiceSpinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class name: MyPlanDialog
 * Specific description :我的计划的对话框
 * 创建人: HUAHUA
 * @version :1.0 , 2018/8/2 10:55
 * 修改人：茹韶燕
 * @version : 增加了获取值以及事件处理
 * @since ：[产品|模块版本]
 */

public class MyPlanDialog extends Dialog{
    private NiceSpinner mniceSpinner1,mniceSpinner2;
    private RadioGroup mradioGroup;
    private Button button_ok,button_cancel;
    MyLisenter lisenter;
    List<String> dataset1,dataset2;
    private HomeContract.IHomePresenter homePresenter;
    Object o;

    public MyPlanDialog(@NonNull Context context) {
        super(context);
    }

    public MyPlanDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyPlanDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myplan_setting_dialog);
        //设置点击空白处不可以关掉dialog
        setCanceledOnTouchOutside(false);
        homePresenter = new HomePresenterImpl();
        setView();
    }
    /**
     * Method name : setView
     * Specific description :初始化页面 和 数据 以及 事件监听
     *@return void
     */
    private void setView(){
        button_ok = (Button) findViewById(R.id.bt_ok);
        button_cancel = (Button) findViewById(R.id.bt_cancel);
        mniceSpinner1 = (NiceSpinner) findViewById(R.id.nice_spinner);
        mniceSpinner2 = (NiceSpinner) findViewById(R.id.spinner_pick);
        mradioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        RadioButton mbt1 = (RadioButton) findViewById(R.id.rb_study);
        RadioButton mbt2 = (RadioButton) findViewById(R.id.rb_restudy);

        mbt2.setChecked(true);//默认复习优先、

        dataset1 = getLexiconName();
        mniceSpinner1.attachDataSource(dataset1);
//        mniceSpinner1.setEnabled(false);
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
        mniceSpinner2.attachDataSource(dataset2);

        lisenter = new MyLisenter();
        button_ok.setOnClickListener(lisenter);
        button_cancel.setOnClickListener(lisenter);

    }

    /**
     * Class name : MyLisenter
     * Specific description :事件处理
     */
    public class MyLisenter implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.bt_ok :
                    HashMap hs = getMyValue();
                    LogUtils.d("我的计划====词库:"+hs.get("wordbox")+",目标时间:"+hs.get("plan") + ",选中框的值:"+hs.get("plantype"));
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
                    dismiss();
                    Intent intent = new Intent();
                    intent.setAction("com.zjx.action.UPDATE_ACTION");
                    getContext().sendBroadcast(intent);
                    break;
                case R.id.bt_cancel:
                    dismiss();
                    default:
                        break;
            }
        }
    }

    /**
     * Method name : getMyValue
     * Specific description :获取所有选择的值
     *@return hs HashMap
     */

    private HashMap getMyValue(){
        HashMap hs = new HashMap();

        //词库类型
        int selectedIndex1 = mniceSpinner1.getSelectedIndex();
        String datapick1 = dataset1.get(selectedIndex1);
        hs.put("box", datapick1);
        hs.put("topicId", getLexiconMessage(datapick1).topicId);
        Integer allWords = Integer.valueOf(getLexiconMessage(datapick1).wordAllCount);
        hs.put("allWords",allWords );

        //目标时间
        int selectedIndex2 = mniceSpinner2.getSelectedIndex();
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
