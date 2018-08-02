package com.echo.quick.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.echo.quick.activities.R;
import com.echo.quick.adapter.SampleWordsAdapter;
import com.echo.quick.pojo.WordList;
import com.echo.quick.pojo.Words;
import com.echo.quick.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUAHUA on 2018/7/23.
 */

public class StrangeListFragment extends Fragment {
    private RecyclerView rvList;
    private SampleWordsAdapter mSampleWordsAdapter;
    private SampleWordsAdapter.OnItemClickListener listener;
    private List<Words> dataList = new ArrayList<>();
//    private List<Words> mData = new ArrayList<>();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_strage_words_one, container, false);
        rvList = (RecyclerView)view.findViewById(R.id.one_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(linearLayoutManager);
        //获取数据重新回到列表
        mSampleWordsAdapter = new SampleWordsAdapter(view.getContext(), initList(), 3);
        rvList.setAdapter(mSampleWordsAdapter);
        //列表子项的点击监听
        mSampleWordsAdapter.setOnItemClickListener(getListen());
        return view;
    }

    /**
     * Method name : initView()
     * Specific description :初始化单词的view
     *@return void
     */
    private void initView() {

    }
    //加载数据
    private List<Words> initList() {
        WordList dataBean = (WordList)getArguments().getSerializable("dataList");
//避免报空指针异常,重新创建了mData,并添加了data
        dataList = dataBean.getData();
        LogUtils.d(dataList.toString());
        return dataList;
    }

    /**
     * Method name : getListen()
     * Specific description :监听recycleview的item子项的点击事件
     *@return listener SampleWordsAdapter.OnItemClickListener
     */
    private SampleWordsAdapter.OnItemClickListener getListen() {
//        listener = new SampleWordsAdapter.OnItemClickListener(){
//            @Override
//            public void onItemClick(View view , int position){
////                ToastUtils.showShort(WordsActivity.this, "点击事件！");
//                int i = position ;
//
//                LogUtils.d("数字为："+i);
//                Words words = new Words(dataList.get(i).getWordId(),dataList.get(i).getPron(),dataList.get(i).getWord(),dataList.get(i).getSymbol(),dataList.get(i).getExplain()+"\n"
//                        ,dataList.get(i).getEg1(),dataList.get(i).getEg1_chinese(),"","", dataList.get(i).getTopicId());
//                WordsShowDialog customDialog = new WordsShowDialog(view.getContext(),words);
//                customDialog.show();
//            }
//        };

        return listener;
    }


}
