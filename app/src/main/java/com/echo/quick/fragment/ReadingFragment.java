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



public class ReadingFragment extends Fragment {
    TextView mtv_title;
    TextView mtv_content;
    String mtitle = "";
    String mContent = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading, container, false);
        mtv_title = (TextView)view.findViewById(R.id.tv_title);
        mtv_content = (TextView)view.findViewById(R.id.tv_pager);
        setData();
        return view;
    }

    public void setData(){
//        mtv_title.setText(pager.mtitle);
        String content = getArguments().getString("content");
        LogUtils.d(content);
        mtv_content.setText(content);
    }






}
