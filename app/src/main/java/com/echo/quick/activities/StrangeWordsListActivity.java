package com.echo.quick.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.echo.quick.adapter.StrangeFragmentAdapter;
import com.echo.quick.fragment.StrangeListFragment;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.WordList;
import com.echo.quick.pojo.Words_Status;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class StrangeWordsListActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragmentList;
    ArrayList<String> titleDatas;
    private TabLayout tabs;
    private ViewPager viewPager;
    private StrangeFragmentAdapter adapter;
    private List<Words_Status> newList = new ArrayList<>();
    private List<Words_Status> studyList = new ArrayList<>();
    private List<Words_Status> studyList2 = new ArrayList<>();
    private List<Words_Status> reviewList = new ArrayList<>();
    private List<Words_Status> graspList = new ArrayList<>();
    private ActivityReceiver activityReceiver;
    StrangeListFragment fragment1;

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strange_words);
        initView();
        // 创建BroadcastReceiver
        activityReceiver = new ActivityReceiver();
        // 创建IntentFilter
        IntentFilter filter = new IntentFilter();
        filter.addAction(App.CTL_ACTION);
        registerReceiver(activityReceiver, filter);

        viewPager = (ViewPager)findViewById(R.id.vp_coupon);
        tabs = (TabLayout)findViewById(R.id.tl_coupon);
        viewPager.setAdapter(adapter);
//        viewPager.setOnPageChangeListener(new PagerListener());
        viewPager.setOffscreenPageLimit(0);
        //为TabLayout设置ViewPager
        tabs.setupWithViewPager(viewPager);
        //使用ViewPager的适配器
        tabs.setTabsFromPagerAdapter(adapter);

    }
    private void initView() {
        titleDatas=new ArrayList<>();
        titleDatas.add("学习中");
        titleDatas.add("待复习");
        titleDatas.add("生词本");
        titleDatas.add("熟悉本");
        fragmentList = new ArrayList<Fragment>();
        addData();
        fragment1 = newInstance(studyList,"study");
        fragmentList.add(fragment1);
        fragmentList.add(newInstance(reviewList,"review"));
        fragmentList.add(newInstance(newList,"new"));
        fragmentList.add(newInstance(graspList,"grasp"));
        adapter = new StrangeFragmentAdapter(getSupportFragmentManager(), titleDatas, fragmentList);

    }

    public void addData(){
        IWordsStatusDao newDao = new WordsStatusImpl();
        for(Words_Status word: newDao.select()){
            String status = word.getStatus();
            if(status.equals("new")){
                newList.add(word);
            } else if(status.equals("study")){
                studyList.add(word);
            } else if(status.equals("review")){
                reviewList.add(word);
            } else if(status.equals("grasp")){
                graspList.add(word);
            }
            LogUtils.d(word.getWord());
        }
    }



    /**
     * Method name : newInstance
     * Specific description :传递数据给fragment
     *@param
     *@param
     *@return
     */
    public static StrangeListFragment newInstance(List<Words_Status> dataList,String type ) {
        StrangeListFragment fragment = new StrangeListFragment();
        Bundle bundle = new Bundle();
        //传送类型
        bundle.putCharSequence("type",type);
        WordList wordList = new WordList();
        wordList.setData(dataList);
        //传送数据
        bundle.putSerializable("dataList",wordList);
        fragment.setArguments(bundle);
        return fragment;
    }


    public class ActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Words_Status addword = (Words_Status) intent.getSerializableExtra("addword");
            fragment1.setNewDate(addword);
                LogUtils.d("发送成功..................................");
            }
        }


}
