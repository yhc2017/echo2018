package com.echo.quick.activities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.echo.quick.adapter.SampleWordsAdapter;
import com.echo.quick.adapter.WordsAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;

import java.util.ArrayList;



public class Words2Activity extends AppCompatActivity implements SwipeItemClickListener {
    private SwipeMenuRecyclerView rvList;
    private WordsAdapter mWordsAdapter;
    private ArrayList<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words2);

        rvList = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(linearLayoutManager);
        // 设置菜单创建器。
        rvList.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        rvList.setSwipeMenuItemClickListener(mMenuItemClickListener);
//        rvList.setOnItemMoveListener(getItemMoveListener());// 监听拖拽和侧滑删除，更新UI和数据源。
        rvList.setOnItemStateChangedListener(mOnItemStateChangedListener); // 监听Item的手指状态，拖拽、侧滑、松开。

        //填充假数据
        for (int i = 0; i < 20; i++) {
            mData.add("RecyclerView" + i);
        }
        mWordsAdapter = new WordsAdapter(this);
        rvList.setAdapter(mWordsAdapter);
        mWordsAdapter.notifyDataSetChanged(mData);
        initView();
    }



    /**
     * Item的拖拽/侧滑删除时，手指状态发生变化监听。
     */
    private OnItemStateChangedListener mOnItemStateChangedListener = new OnItemStateChangedListener() {
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState == OnItemStateChangedListener.ACTION_STATE_SWIPE) {
//                mActionBar.setSubtitle("状态：滑动删除");
                Toast.makeText(Words2Activity.this, "list第" + "删除子项", Toast.LENGTH_SHORT).show();
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_IDLE) {
                // 在手松开的时候还原背景。
                ViewCompat.setBackground(viewHolder.itemView, ContextCompat.getDrawable(Words2Activity.this, R.color.bule));
            }
        }
    };


    /**
     * Method name : initView()
     * Specific description :初始化单词的view
     *
     * @return void
     */
    private void initView() {

    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(Words2Activity.this)
                        .setImage(R.mipmap.bt_ic_schedule_white_24)
                        .setText("左边")
                        .setTextColor(Color.YELLOW)
                        .setWidth(width)
                        .setHeight(height);
//                swipeRightMenu.addMenuItem(add);// 添加菜单到右侧。
            }
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(Words2Activity.this)
                        .setImage(R.mipmap.bt_ic_schedule_white_24)
                        .setText("删除")
                        .setTextColor(Color.RED)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(Words2Activity.this)
                        .setText("添加")
                        .setTextColor(Color.BLUE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                //判断是第几项
                if (0==menuPosition){
                    mData.remove(adapterPosition);
                    mWordsAdapter.notifyItemRemoved(adapterPosition);
                    Toast.makeText(Words2Activity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                }else if(1==menuPosition){

                    Toast.makeText(Words2Activity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                }

            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                mData.remove(adapterPosition);
                mWordsAdapter.notifyItemRemoved(adapterPosition);
            }
        }
    };


    @Override
    public void onItemClick(View itemView, int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
