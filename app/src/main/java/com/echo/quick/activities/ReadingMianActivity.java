package com.echo.quick.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.echo.quick.adapter.ListShowAdapter;
import com.echo.quick.adapter.SampleWordsAdapter;
import com.echo.quick.adapter.StrangeFragmentAdapter;
import com.echo.quick.pojo.Words;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUAHUA on 2018/7/27.
 */

public class ReadingMianActivity extends AppCompatActivity{
    private StrangeFragmentAdapter adapter;
    private List<String> mList;
    RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_home);

        listView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(linearLayoutManager);
        SampleWordsAdapter adapter2 = new SampleWordsAdapter(ReadingMianActivity.this,initList(),2);
        listView.setAdapter(adapter2);
    }

    /**
     * Method name : initList
     * Specific description :加载数据
     *@param
     *@param
     *@return
     */
    public List initList(){
        mList = new ArrayList();
        for (int i = 0; i < 20; i++) {
            mList.add("2017年四级真题A卷");
        }
        return mList;
    }
}
