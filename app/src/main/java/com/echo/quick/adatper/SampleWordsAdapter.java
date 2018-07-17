package com.echo.quick.adatper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.echo.quick.quickly.R;
import java.util.ArrayList;

/**
 * Class name: SampleWordsAdapter
 * Specific description :用于作为5个单词的listview的适配器
 * 创建人: HUAHUA
 * @version :1.0 , 2018/7/17 14:25
 * 修改人：
 * @version :
 * @since ：[quick|速记单词模块]
 */

public class SampleWordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mData;

    /**
     * Method name : 构造方法
     * Specific description :用于将布局和数据引入绑定
     *@param   context Context
     *@param   data ArrayList<String>
     */
    public SampleWordsAdapter(Context context, ArrayList<String> data) {
        mLayoutInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.item_words, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).tvItem.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public View vBackground; // 背景
        public View vItem;
        public TextView tvItem; // 滑动的view
        public ImageView ivDone;
        public ImageView ivSchedule;

        public ItemViewHolder(View itemView) {
            super(itemView);
            vBackground = itemView.findViewById(R.id.rl_background);
            vItem = itemView.findViewById(R.id.ll_item);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
            ivDone = (ImageView) itemView.findViewById(R.id.iv_done);
            ivSchedule = (ImageView) itemView.findViewById(R.id.iv_schedule);
        }
    }
}
