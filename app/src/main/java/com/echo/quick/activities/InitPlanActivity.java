package com.echo.quick.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.contracts.HomeContract;
import com.echo.quick.presenters.HomePresenterImpl;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.SPUtils;

import org.angmarch.views.NiceSpinner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InitPlanActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_plan;
    private NiceSpinner mniceSpinner3,mniceSpinner4;
    List<String> dataset1,dataset2;
    private RadioGroup mradioGroup;
    private HomeContract.IHomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_plan);
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
        dataset1 = new ArrayList<>();
        try {
            Object o = getWordBox();
            JSONArray jsonArray_wordsBox = JSONArray.parseArray(o.toString());
            for(int i = 0; i < jsonArray_wordsBox.size(); i++){
                JSONObject object = jsonArray_wordsBox.getJSONObject(i);
                dataset1.add(object.getString("topicName"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        mniceSpinner3.attachDataSource(dataset1);
        int year = homePresenter.getYear();
        int mouth = homePresenter.getMouth();
        String str1="";
        String str2="";
        String str3="";
        String str4="";
        if (mouth < 6){
            str1 = year+"-"+6;
            str2 = year+"-"+12;
            str3 = (year+1)+"-"+6;
            str4 = (year+1)+"-"+12;
        }else {
            str1 = year+"-"+12;
            str2 = (year+1)+"-"+6;
            str3 = (year+1)+"-"+12;
            str4 = (year+2)+"-"+6;
        }
        dataset2 = new LinkedList<>(Arrays.asList(str1, str2, str3, str4));
        mniceSpinner4.attachDataSource(dataset2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_plan:
                HashMap hs = getMyValue();
                LogUtils.d("我的计划====词库:"+hs.get("wordbox")+",目标时间:"+hs.get("plan") + ",选中框的值:"+hs.get("plantype"));
                //选择的词库
                SPUtils.put(App.getContext(), "box", hs.get("wordbox"));
                SPUtils.put(App.getContext(), "boxPosition", hs.get("position")+"");
                //选择的计划完成日期
                SPUtils.put(App.getContext(), "plan", hs.get("plan"));
                //选择的背单词模式
                SPUtils.put(App.getContext(), "planType", hs.get("plantype"));

                try {
                    //存入目标数
                    int datenum = homePresenter.calMyPlanNmu(hs.get("plan").toString(), (Integer) hs.get("position"));
                    System.out.printf("=================每日的目标数："+datenum);
                    SPUtils.put(App.getContext(), "dateNum", datenum);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setAction("com.zjx.action.UPDATE_ACTION");
                sendBroadcast(intent);
                startActivity(new Intent(InitPlanActivity.this, HomeActivity.class));
                finish();
                break;

            default:

                break;

        }
    }

    private HashMap getMyValue(){
        HashMap hs = new HashMap();

        //词库类型
        int selectedIndex1 = mniceSpinner3.getSelectedIndex();
        String datapick1 = dataset1.get(selectedIndex1);
        hs.put("wordbox", datapick1);
        hs.put("position", selectedIndex1);

        //目标时间
        int selectedIndex2 = mniceSpinner4.getSelectedIndex();
        String datapick2 = dataset2.get(selectedIndex2);
        hs.put("plan", datapick2);

        //选中框的值
        int checkedRadioButtonId = mradioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)findViewById(checkedRadioButtonId);
        String plantype = radioButton.getText().toString();
        hs.put("plantype", plantype);

        return hs;
    }

    private Object getWordBox(){
        Object o = SPUtils.get(App.getContext(), "wordsBox", "[\n" +
                "    {\n" +
                "        \"topicId\": \"12\",\n" +
                "        \"topicName\": \"六级必备词汇\",\n" +
                "        \"tableName\": \"word_12\",\n" +
                "        \"wordAllCount\": 2087,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"11\",\n" +
                "        \"topicName\": \"四级必备词汇\",\n" +
                "        \"tableName\": \"word_11\",\n" +
                "        \"wordAllCount\": 13527,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"140\",\n" +
                "        \"topicName\": \"高考大纲词汇\",\n" +
                "        \"tableName\": \"word_140\",\n" +
                "        \"wordAllCount\": 3874,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"141\",\n" +
                "        \"topicName\": \"高考常用短语词汇\",\n" +
                "        \"tableName\": \"word_141\",\n" +
                "        \"wordAllCount\": 362,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"13\",\n" +
                "        \"topicName\": \"考研必备词汇\",\n" +
                "        \"tableName\": \"word_13\",\n" +
                "        \"wordAllCount\": 5475,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"14\",\n" +
                "        \"topicName\": \"TOEFL必备词汇\",\n" +
                "        \"tableName\": \"word_14\",\n" +
                "        \"wordAllCount\": 4883,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"16\",\n" +
                "        \"topicName\": \"GRE考试必备词汇\",\n" +
                "        \"tableName\": \"word_16\",\n" +
                "        \"wordAllCount\": 14992,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"15\",\n" +
                "        \"topicName\": \"雅思必备词汇\",\n" +
                "        \"tableName\": \"word_15\",\n" +
                "        \"wordAllCount\": 4541,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"17\",\n" +
                "        \"topicName\": \"四级高频词汇\",\n" +
                "        \"tableName\": \"word_cet4_high\",\n" +
                "        \"wordAllCount\": 685,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"18\",\n" +
                "        \"topicName\": \"真题阅读词汇\",\n" +
                "        \"tableName\": \"word_paper_reading\",\n" +
                "        \"wordAllCount\": 65,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    }\n" +
                "]");
        return o;
    }
}