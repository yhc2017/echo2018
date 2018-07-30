package com.echo.quick.activities;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.echo.quick.adapter.SampleWordsAdapter;
import com.echo.quick.contracts.WordsContract;
import com.echo.quick.pojo.Words;
import com.echo.quick.presenters.WordsPresenterImpl;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.ToastUtils;
import com.echo.quick.utils.WordsShowDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Class name: WordsActivity
 * Specific description :背单词的主界面
 * 创建人: HUAHUA
 * @version :1.0 , 2018/7/17 14:50
 * 修改人：茹韶燕
 * @version : 因为修改了适配器，构造方法多一个参数是自布局的类型
 * @since ：[quick|背单词模块]
 */
public class WordsActivity extends AppCompatActivity {
    private RecyclerView rvList;
    private SampleWordsAdapter mSampleWordsAdapter;
    private SampleWordsAdapter.OnItemClickListener listener;
    private ItemTouchHelper itemTouchHelper;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    private List<Words> dataList = new ArrayList<>();
    private List<Words> mData = new ArrayList<>();
    private List<Words> recurrent = new ArrayList<>();
    private App app;
    int start = 0;
    int stop = 5;
    boolean isEnd = false;
    private String CTE = "no";
    WordsContract.IWordsPresenter wordsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        app = (App)getApplication();

        Intent intent = getIntent();
        if(intent != null){
            CTE = intent.getStringExtra("CTE");
        }

        dataList = app.getList();

        initView();
        wordsPresenter = new WordsPresenterImpl();
    }

    /**
     * Method name : initView()
     * Specific description :初始化单词的view
     *@return void
     */
    private void initView() {
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(linearLayoutManager);
        mSampleWordsAdapter = new SampleWordsAdapter(this, mData,1);
        //获取数据重新回到列表
        mSampleWordsAdapter = new SampleWordsAdapter(this, getData(),1);
        rvList.setAdapter(mSampleWordsAdapter);
        //列表子项的点击监听
        mSampleWordsAdapter.setOnItemClickListener(getListen());
        //滑动事件
        itemTouchHelper = new ItemTouchHelper(getEvent());
        itemTouchHelper.attachToRecyclerView(rvList);
    }


    /**
     * Method name : getEvent
     * Specific description :对于item的处理,不可以拖拽，只可以在左右方向滑动
     *@return simpleCallback ItemTouchHelper.SimpleCallback
     */
    private ItemTouchHelper.SimpleCallback getEvent() {

        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // 不处理拖拽事件回调
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 处理滑动事件回调
                final int pos = viewHolder.getAdapterPosition();//页面中子项的位置
                final Words item = mData.get(pos);//数据子项的位置

                Log.d("quick", "dataList.size:"+dataList.size()+"  start:"+start+"   stop:"+stop+"    recurrent:"+recurrent.size());
                //当页面还剩一条单词时，进行特殊处理
                if(mSampleWordsAdapter.getItemCount() == 1){
                    //当左滑单词需要复现的数组中存在单词时，将其加入到当前列表下
                    Words words = mData.get(pos);
                    if (direction == ItemTouchHelper.RIGHT) {
                        wordsPresenter.rightSwipe(words);
                        mSampleWordsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    } else {
                        dataList.add(words);
                        wordsPresenter.liefSwipe(words);
                    }
                    mData.remove(pos);

                    if(isEnd){
                        startActivity(new Intent(WordsActivity.this, ReadActivity.class));
                    }

                    start = stop;
                    stop += 5;

                    if(stop >= dataList.size()) {
                        stop = dataList.size();
                        isEnd = true;
                    }

                    try {

                        for(int i = start; i < stop; i++){
                            Words word = dataList.get(i);
                            mData.add(word);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSampleWordsAdapter = new SampleWordsAdapter(WordsActivity.this, mData,1);
                                rvList.setAdapter(mSampleWordsAdapter);
                                //列表子项的点击监听
                                mSampleWordsAdapter.setOnItemClickListener(getListen());

                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                        ToastUtils.showShort(WordsActivity.this, "一轮练习已完成");
                        mSampleWordsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        mSampleWordsAdapter.notifyItemRangeRemoved(pos,mData.size());
                        ToastUtils.showLong(WordsActivity.this, app.getContent());

                    }
                }else {//页面超过1个单词时
                    String text;
                    //判断滑动方向
                    if (direction == ItemTouchHelper.RIGHT) {
                        text = "已记住";
                        wordsPresenter.rightSwipe(mData.get(pos));
                        mData.remove(pos);
                        mSampleWordsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                    } else {
                        text = "还没记住";
                        Words words = mData.get(pos);
                        mData.remove(pos);
                        dataList.add(words);
                        wordsPresenter.liefSwipe(words);
                        mSampleWordsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                    }

                    mSampleWordsAdapter.notifyItemRangeRemoved(pos,mData.size());
                    /**
                     * 撤销上一个单词的操作
                     * @param  View 视图,  CharSequence 字符串, int 出现时间
                     */
                    Snackbar.make(viewHolder.itemView, text, Snackbar.LENGTH_LONG)
                            .setAction("撤销", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mData.add(pos, item);
                                    mSampleWordsAdapter.notifyItemInserted(pos);
                                }
                            }).show();
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                // 释放View时回调，清除背景颜色，隐藏图标
                // 默认是操作ViewHolder的itemView，这里调用ItemTouchUIUtil的clearView方法传入指定的view
                getDefaultUIUtil().clearView(((SampleWordsAdapter.ItemViewHolder) viewHolder).vItem);
                ((SampleWordsAdapter.ItemViewHolder) viewHolder).vBackground.setBackgroundColor(Color.TRANSPARENT);
                ((SampleWordsAdapter.ItemViewHolder) viewHolder).ivSchedule.setVisibility(View.GONE);
                ((SampleWordsAdapter.ItemViewHolder) viewHolder).ivDone.setVisibility(View.GONE);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                // 当viewHolder的滑动或拖拽状态改变时回调
                if (viewHolder != null) {
                    // 默认是操作ViewHolder的itemView，这里调用ItemTouchUIUtil的clearView方法传入指定的view
                    getDefaultUIUtil().onSelected(((SampleWordsAdapter.ItemViewHolder) viewHolder).vItem);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // ItemTouchHelper的onDraw方法会调用该方法，可以使用Canvas对象进行绘制，绘制的图案会在RecyclerView的下方
                // 默认是操作ViewHolder的itemView，这里调用ItemTouchUIUtil的clearView方法传入指定的view
                getDefaultUIUtil().onDraw(c, recyclerView, ((SampleWordsAdapter.ItemViewHolder) viewHolder).vItem, dX, dY, actionState, isCurrentlyActive);
                if (dX > 0) { // 向左滑动是的提示
                    ((SampleWordsAdapter.ItemViewHolder) viewHolder).vBackground.setBackgroundResource(R.drawable.item_left);
                    ((SampleWordsAdapter.ItemViewHolder) viewHolder).ivDone.setVisibility(View.VISIBLE);
                    ((SampleWordsAdapter.ItemViewHolder) viewHolder).ivSchedule.setVisibility(View.GONE);
                }
                if (dX < 0) { // 向右滑动时的提示
                    ((SampleWordsAdapter.ItemViewHolder) viewHolder).vBackground.setBackgroundResource(R.drawable.item_right);
                    ((SampleWordsAdapter.ItemViewHolder) viewHolder).ivSchedule.setVisibility(View.VISIBLE);
                    ((SampleWordsAdapter.ItemViewHolder) viewHolder).ivDone.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // ItemTouchHelper的onDrawOver方法会调用该方法，可以使用Canvas对象进行绘制，绘制的图案会在RecyclerView的上方
                // 默认是操作ViewHolder的itemView，这里调用ItemTouchUIUtil的clearView方法传入指定的view
                getDefaultUIUtil().onDrawOver(c, recyclerView, ((SampleWordsAdapter.ItemViewHolder) viewHolder).vItem, dX, dY, actionState, isCurrentlyActive);
            }
        };
        return simpleCallback;
    }

    /**
     * Method name : getListen()
     * Specific description :监听recycleview的item子项的点击事件
     *@return listener SampleWordsAdapter.OnItemClickListener
     */
    private SampleWordsAdapter.OnItemClickListener getListen() {
        listener = new SampleWordsAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
//                ToastUtils.showShort(WordsActivity.this, "点击事件！");
                int i = position ;

                LogUtils.d("数字为："+i);
                Words words = new Words(mData.get(i).getWordId(),mData.get(i).getPron(),mData.get(i).getWord(),mData.get(i).getSymbol(),mData.get(i).getExplain()+"\n"
                        ,"She gave him a quick glance.","她迅速地扫了他一眼。","She gave him a quick glance.","她迅速地扫了他一眼。");
                WordsShowDialog customDialog = new WordsShowDialog(WordsActivity.this,words);
                customDialog.show();
            }
        };

        return listener;
    }

    /**
     * Method name : getData()
     * Specific description :获取数据
     *@return mData List<Words>
     *
     * 修改人：周少侠
     * 修改原因：WordsActivity作为一个展示的Activity，需要显示的单词可以放在app.list中
     */
    private List<Words> getData() {

        try {
            if (mData==null) {
                LogUtils.d("为空的数据列表！");
            }else {
                for(int i = start; i < stop; i++){
                    Words word = dataList.get(i);
                    mData.add(word);
                }
            }
        }catch (Exception e){
            ToastUtils.showLong(WordsActivity.this,"网络连接出错");
        }

        //填充假数据
//        for (int i = 0; i < 5; i++) {
//            Words words = new Words("Quick","/kik/","");
//            mData.add(words);
//        }
        return mData;
    }


}
