package com.echo.quick.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.echo.quick.adapter.StrangeFragmentAdapter;
import com.echo.quick.fragment.StrangeListFragment;
import com.echo.quick.fragment.StrangeTwoListFragment;

import java.util.ArrayList;

public class StrangeWordsListActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragmentList;
    ArrayList<String> titleDatas;
    private TabLayout tabs;
    private ViewPager viewPager;
    private StrangeFragmentAdapter adapter;

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
        titleDatas.add("生词");
        titleDatas.add("不熟词");
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new StrangeListFragment());
        fragmentList.add(new StrangeTwoListFragment());
        adapter = new StrangeFragmentAdapter(getSupportFragmentManager(), titleDatas, fragmentList);


    }



}
