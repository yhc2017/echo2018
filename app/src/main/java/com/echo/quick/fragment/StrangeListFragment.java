package com.echo.quick.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.echo.quick.activities.R;
import com.echo.quick.activities.StrangeWordsListActivity;
import com.echo.quick.adapter.SampleWordsAdapter;
import com.echo.quick.pojo.WordList;
import com.echo.quick.pojo.Words;
import com.echo.quick.pojo.Words_Status;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.MyPlanDialog;
import com.echo.quick.utils.WordsShowDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUAHUA on 2018/7/23.
 */

public class StrangeListFragment extends Fragment {
    private RecyclerView rvList;
    private SampleWordsAdapter mSampleWordsAdapter;
    private SampleWordsAdapter.OnItemClickListener listener;
    private List<Words_Status> dataList = new ArrayList<>();
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
        mSampleWordsAdapter = new SampleWordsAdapter(view.getContext(), initList(), getViewType());
        rvList.setAdapter(mSampleWordsAdapter);
        //列表子项的点击监听
        mSampleWordsAdapter.setOnItemClickListener(getListener());
        return view;
    }

    /**
     * Method name : getViewType()
     * Specific description :确定item布局
     */
    private int getViewType() {
        //获取activity的frgment的bunle传回来的值
        String str = (String) getArguments().getCharSequence("type");
        if (str.equals("study")||str.equals("new")){
            return 4;
        }else {
            return 3;
        }
    }
    //加载数据
    private List<Words_Status> initList() {
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
    private SampleWordsAdapter.OnItemClickListener getListener(){
            listener = new SampleWordsAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                int i = position ;
                LogUtils.d("数字为："+i);
                switch(view.getId()){
                    case R.id.bt_del:
                        LogUtils.d("回到学习中"+position);
                        break;
                        default:
                            LogUtils.d("这个是子项"+position);
                            break;
                }

            }
        };

        return listener;
    }


}
