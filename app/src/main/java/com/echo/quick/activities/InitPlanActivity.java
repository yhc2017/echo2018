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
            Object o = SPUtils.get(App.getContext(), "wordsBox", "");
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
}
