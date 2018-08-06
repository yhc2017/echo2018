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
import com.echo.quick.contracts.HomeContract;
import com.echo.quick.presenters.HomePresenterImpl;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
    private RadioButton mbt1,mbt2;
    private Button button_ok,button_cancel;
    MyLisenter lisenter;
    List<String> dataset1,dataset2;
    HomeContract.IHomePresenter homePresenter;

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
    public void setView(){
        button_ok = (Button) findViewById(R.id.bt_ok);
        button_cancel = (Button) findViewById(R.id.bt_cancel);
        mniceSpinner1 = (NiceSpinner) findViewById(R.id.nice_spinner);
        mniceSpinner2 = (NiceSpinner) findViewById(R.id.spinner_pick);
        mradioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        mbt1 = (RadioButton) findViewById(R.id.rb_study);
        mbt2 = (RadioButton) findViewById(R.id.rb_restudy);

        mbt1.setChecked(true);//默认复习优先、

//        List<String> wordsBox = new ArrayList<>();
        dataset1 = new ArrayList<>();
        try {
            Object o = SPUtils.get(App.getContext(), "wordsBox", "");
            JSONArray jsonArray = JSONArray.parseArray(o.toString());
            for(int i = 0; i < jsonArray.size(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                dataset1.add(object.getString("topicName"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        mniceSpinner1.attachDataSource(dataset1);
        int year = homePresenter.getYear();
        int mouth = homePresenter.getMouth();
        String str1="";
        String str2="";
        String str3="";
        String str4="";
        if (mouth < 6){
            str1 = year+"年"+6+"月";
            str2 = year+"年"+12+"月";
            str3 = (year+1)+"年"+6+"月";
            str4 = (year+1)+"年"+12+"月";
        }else {
            str1 = year+"年"+12+"月";
            str2 = (year+1)+"年"+6+"月";
            str3 = (year+1)+"年"+12+"月";
            str4 = (year+2)+"年"+6+"月";
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
     *@return void
     */
    public class MyLisenter implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.bt_ok :
                    HashMap hs = getMyValue();
                    LogUtils.d("我的计划====词库:"+hs.get("wordbox")+",目标时间:"+hs.get("plan") + ",选中框的值:"+hs.get("plantype"));
                    SPUtils.put(App.getContext(), "box", hs.get("wordbox"));
                    SPUtils.put(App.getContext(), "boxPosition", hs.get("position")+"");
                    SPUtils.put(App.getContext(), "plan", hs.get("plan"));
                    SPUtils.put(App.getContext(), "planType", hs.get("plantype"));
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

    public HashMap getMyValue(){
        HashMap hs = new HashMap();

        //词库类型
        int selectedIndex1 = mniceSpinner1.getSelectedIndex();
        String datapick1 = dataset1.get(selectedIndex1);
        hs.put("wordbox", datapick1);
        hs.put("position", selectedIndex1);

        //目标时间
        int selectedIndex2 = mniceSpinner2.getSelectedIndex();
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
