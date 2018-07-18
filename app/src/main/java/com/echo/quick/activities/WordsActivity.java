package com.echo.quick.activities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.echo.quick.adapter.SampleWordsAdapter;
import com.echo.quick.utils.Words;

import java.util.ArrayList;
import java.util.List;

/**
 * Class name: WordsActivity
 * Specific description :背单词的主界面
 * 创建人: HUAHUA
 * @version :1.0 , 2018/7/17 14:50
 * 修改人：
 * @version :
 * @since ：[quick|背单词模块]
 */
public class WordsActivity extends AppCompatActivity {
    private RecyclerView rvList;
    private SampleWordsAdapter mSampleWordsAdapter;
    private List<Words> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        initView();
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

//        if (0==mSampleWordsAdapter.getItemCount()){
//            for (int i = 0; i < 5; i++) {
//                Words words = new Words("Quick","/kulk/","adj. 快的；adv. 迅速地");
//                mData.add(words);
//            }
//        }
        //填充假数据
        for (int i = 0; i < 5; i++) {
            Words words = new Words("Quick","/kulk/","adj. 快的；adv. 迅速地");
            mData.add(words);
        }
        mSampleWordsAdapter = new SampleWordsAdapter(this, mData);
        rvList.setAdapter(mSampleWordsAdapter);

         //new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) 表示不可以拖拽，只可以在左右方向滑动
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
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

//                mData.remove(pos);
//                mSampleWordsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                String text;
                // 判断方向，进行不同的操作
                if (direction == ItemTouchHelper.RIGHT) {
                    text = "已记住";
                    mData.remove(pos);
                    mSampleWordsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                } else {
                    text = "还没记住";
                    mData.remove(pos);
                    mSampleWordsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                }
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
        });
        itemTouchHelper.attachToRecyclerView(rvList);
    }


}
