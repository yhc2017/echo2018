package com.echo.quick.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.echo.quick.activities.R;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HUAHUA on 2018/8/1.
 */

public class MyPlanDialog extends Dialog{
    private NiceSpinner mniceSpinner1,mniceSpinner2;
    private RadioGroup mradioGroup;
    private RadioButton mbt1,mbt2;

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
        setView();
    }
    /**
     * Method name : setView
     * Specific description :初始化页面和数据
     *@return void
     */
    public void setView(){

        mniceSpinner1 = (NiceSpinner) findViewById(R.id.nice_spinner);
        mniceSpinner2 = (NiceSpinner) findViewById(R.id.spinner_pick);
        mradioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        mbt1 = (RadioButton) findViewById(R.id.rb_study);
        mbt2 = (RadioButton) findViewById(R.id.rb_restudy);
        mbt1.setChecked(true);//默认复习优先
        List<String> dataset1 = new LinkedList<>(Arrays.asList("四级", "六级", "雅思", "出国(G)","出国(K)", "出国(B)", "出国(A)", "出国(D)"));
        mniceSpinner1.attachDataSource(dataset1);
        List<String> dataset2 = new LinkedList<>(Arrays.asList("2018年6月", "2018年12月", "2019年6月", "2019年12月", "2020年6月"));
        mniceSpinner2.attachDataSource(dataset2);

    }

    }
