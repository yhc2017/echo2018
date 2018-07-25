package com.echo.quick.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.echo.quick.activities.R;
import com.echo.quick.adapter.ListShowAdapter;
import com.echo.quick.pojo.Words;

import java.util.ArrayList;

/**
 * Created by HUAHUA on 2018/7/25.
 */



public class ReadingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading, container, false);
        return view;
    }


}
