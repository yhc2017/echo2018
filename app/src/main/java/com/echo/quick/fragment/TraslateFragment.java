package com.echo.quick.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.echo.quick.activities.R;
import com.echo.quick.utils.LogUtils;

/**
 * Created by HUAHUA on 2018/7/25.
 */



public class TraslateFragment extends Fragment {
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading, container, false);
        textView = (TextView)view.findViewById(R.id.tv_pager);
        textView.setText(R.string.reading_example_chinese);
        setData();
        return view;
    }

    public void setData(){
//        mtv_title.setText(pager.mtitle);
        String content = getArguments().getString("traslate");
        LogUtils.d(content);
        textView.setText(content);
    }

}
