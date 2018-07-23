package com.echo.quick.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.echo.quick.activities.R;
import com.echo.quick.adapter.ListShowAdapter;
import com.echo.quick.pojo.Words;
import com.echo.quick.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUAHUA on 2018/7/23.
 */

public class StrangeTwoListFragment extends Fragment {
    private List<Words> mList;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strage_words_two, container, false);
        mList = new ArrayList<Words>();
        listView = (ListView) view.findViewById(R.id.two_list);
        ListShowAdapter adapter = new ListShowAdapter(getContext(),R.layout.item_strage_words_two,initList());
        listView.setAdapter(adapter);
        return view;
    }
    //加载数据
    private List<Words> initList() {
        for (int i = 0; i <20 ; i++) {
            Words words = new Words("quick", "/kuki/", "adj.快的，很快的");
            mList.add(words);
        }
        LogUtils.d(mList.toString());
        return mList;
    }
}
