package com.echo.quick.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.echo.quick.activities.R;
import com.echo.quick.activities.StrangeWordsListActivity;
import com.echo.quick.activities.WordsActivity;
import com.echo.quick.adapter.ListShowAdapter;
import com.echo.quick.pojo.Words;
import com.echo.quick.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUAHUA on 2018/7/23.
 */

public class StrangeTwoListFragment extends Fragment {
    RelativeLayout mrlct4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strage_words_two, container, false);
        return view;
    }

    /**
     * Method name : onActivityCreated
     * Specific description :在onActivityCreated中通过getActivity()获取到Fragment关联的Activity，在onActivityCreated中为按钮添加监听事件。
     *@param  savedInstanceState Bundle
     *@return void
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intview();
        setEvent();
    }

    /**
     * Method name : intview
     * Specific description :初始化页面控件，由于是fragment需要getActivity()方法来findViewById()
     *@return void
     */
    public void intview(){
        mrlct4 = (RelativeLayout)getActivity().findViewById(R.id.rl_ct4);
    }

    /**
     * Method name : setEvent
     * Specific description :页面控件设置监听
     *@return void
     */
    public void setEvent(){
        MyListener listener = new MyListener();
        mrlct4.setOnClickListener(listener);
    }

    /**
     * Class name : MyListener
     * Specific description :实现接口OnClickListener，跳转需要intent = new Intent(getActivity(), WordsActivity.class);
     *@return void
     */
    public class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Intent intent = null;
            switch (id) {
                case R.id.rl_ct4:
                    intent = new Intent(getActivity(), WordsActivity.class);
                    startActivity(intent);
                break;
                default:
                    break;
            }
        }
    }
}
