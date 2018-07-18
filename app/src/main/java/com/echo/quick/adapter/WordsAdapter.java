package com.echo.quick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.echo.quick.activities.R;

import java.util.List;

/**
 * Created by HUAHUA on 2018/7/18.
 */

public class WordsAdapter extends BaseAdapter<WordsAdapter.ViewHolder> {


    private List<String> mDataList;


    public WordsAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<String> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_words_two, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public View vBackground; // 背景
        public View vItem;

        public ViewHolder(View itemView) {
            super(itemView);
            vBackground = itemView.findViewById(R.id.rl_background);
            vItem = itemView.findViewById(R.id.ll_item);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }
    }

}

