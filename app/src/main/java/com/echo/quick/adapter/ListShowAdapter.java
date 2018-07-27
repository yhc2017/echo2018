package com.echo.quick.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.echo.quick.activities.R;
import com.echo.quick.pojo.Words;
import com.echo.quick.utils.LogUtils;

import java.util.List;

public class ListShowAdapter extends ArrayAdapter<Words> {
    private int resourceId;
    private List<Words> mobjects;

    public ListShowAdapter(Context context, int textViewResourceId, List<Words> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        mobjects = objects;
    }

    class ViewHolder {
        TextView tv_word;
        TextView tv_symbol;
        TextView tv_explain;
        ImageView im_delete;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Words mlist2 = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_word = (TextView) view.findViewById(R.id.tv_word);
            viewHolder.tv_symbol = (TextView) view.findViewById(R.id.tv_symbol);
            viewHolder.tv_explain = (TextView) view.findViewById(R.id.tv_explain);
            viewHolder.im_delete = (ImageView) view.findViewById(R.id.im_delete);
            view.setTag(viewHolder);//将它存储在View中
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
        }

        if (null == mlist2) {
            LogUtils.d("生词列表：数据为空！！！");
        }else {
            //绑定数据
            LogUtils.d("打印生词本words："+mlist2.getWord());
            viewHolder.tv_word.setText(mlist2.getWord());
            viewHolder.tv_symbol.setText(mlist2.getSymbol());
            viewHolder.tv_explain.setText(mlist2.getExplain());
        }
        return view;
    }


}