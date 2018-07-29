package com.echo.quick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.echo.quick.adapter.ListShowAdapter;
import com.echo.quick.adapter.SampleWordsAdapter;
import com.echo.quick.adapter.StrangeFragmentAdapter;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.pojo.Words;
import com.echo.quick.presenters.OnlineWordPresenterImpl;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by HUAHUA on 2018/7/27.
 */

public class ReadingMianActivity extends AppCompatActivity{
    private SampleWordsAdapter adapter2;
    private SampleWordsAdapter.OnItemClickListener listener;
    private List<String> mList;
    RecyclerView listView;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_home);
        app = (App)getApplication();
        listView = (RecyclerView)findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(linearLayoutManager);
        adapter2 = new SampleWordsAdapter(ReadingMianActivity.this,initList(),2);
        //列表子项的点击监听
        adapter2.setOnItemClickListener(getListener());
        listView.setAdapter(adapter2);
    }

    /**
     * Method name : initList
     * Specific description :加载数据
     *@return mList List
     */
    public List initList(){

        mList = app.getPagerList();
        for (int i=0;i<1;i++) {
            LogUtils.d(mList+"");
        }
        return mList;
    }

    /**
     * Method name : getListen()
     * Specific description :监听recycleview的item子项的点击事件
     *@return listener SampleWordsAdapter.OnItemClickListener
     */
    private SampleWordsAdapter.OnItemClickListener getListener() {
        listener = new SampleWordsAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                String paperDate = "CTE";
                try {
                    paperDate =  mList.get(position);
                    OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
                    final HashMap<String, String> map = new HashMap<>();
                    map.put("userId", "111");
                    map.put("paperDate", paperDate);
                    map.put("paperType", "A");
                    app.setList(onlineWordPresenter.getOnlineSprint(map));
                    onlineWordPresenter.getOnlineSprintType();
                }catch (Exception e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(ReadingMianActivity.this, WordsActivity.class);
                intent.putExtra("CTE", paperDate);
                startActivity(intent);

            }
        };

        return listener;
    }
}
