package com.echo.quick.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.echo.quick.activities.R;

import org.angmarch.views.NiceSpinner;

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

        mbt1.setChecked(true);//默认复习优先
        dataset1 = new LinkedList<>(Arrays.asList("四级", "六级", "雅思", "出国(G)","出国(K)", "出国(B)", "出国(A)", "出国(D)"));
        mniceSpinner1.attachDataSource(dataset1);
        dataset2 = new LinkedList<>(Arrays.asList("2018年6月", "2018年12月", "2019年6月", "2019年12月", "2020年6月"));
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
                    dismiss();
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
