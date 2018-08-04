package com.echo.quick.activities;

import android.annotation.SuppressLint;
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
    private List<Words_Status> reviewList = new ArrayList<>();
    private List<Words_Status> graspList = new ArrayList<>();

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strange_words);
        initView();
        viewPager = (ViewPager)findViewById(R.id.vp_coupon);
        tabs = (TabLayout)findViewById(R.id.tl_coupon);
        viewPager.setAdapter(adapter);
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
        fragmentList.add(newInstance(studyList));
        fragmentList.add(newInstance(reviewList));
        fragmentList.add(newInstance(newList));
        fragmentList.add(newInstance(graspList));
        adapter = new StrangeFragmentAdapter(getSupportFragmentManager(), titleDatas, fragmentList);

    }

    /**
     * Method name : newInstance
     * Specific description :传递数据给fragment
     *@param
     *@param
     *@return
     */
    public static StrangeListFragment newInstance(List<Words_Status> dataList ) {
        StrangeListFragment fragment = new StrangeListFragment();
        Bundle bundle = new Bundle();
        WordList wordList = new WordList();
        wordList.setData(dataList);
        bundle.putSerializable("dataList",wordList);
        fragment.setArguments(bundle);
        return fragment;
    }





}
