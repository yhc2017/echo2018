package com.echo.quick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.echo.quick.activities.R;
import com.echo.quick.pojo.Words;
import com.echo.quick.utils.LogUtils;

import java.util.List;

/**
 * Class name: SampleWordsAdapter
 * Specific description :用于作为5个单词的listview的适配器
 * 创建人: HUAHUA
 * @version :1.0 , 2018/7/17 14:25
 * 修改人：
 * @version :
 * @since ：[quick|速记单词模块]
 */

public class SampleWordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private LayoutInflater mLayoutInflater;
    private List<Words> mData;
    private List<String> mtitle;
    private OnItemClickListener mOnItemClickListener = null;
    View view;
    int mviewType;
    final int WORDS_ONE = 1;
    final int WORDS_TWO = 3;
    final int TWO = 2;

    /**
     * Method name : 构造方法
     * Specific description :用于将布局和数据引入绑定
     *@param   context Context
     *@param   data ArrayList<String>
     */
    public SampleWordsAdapter(Context context, List data,int viewType) {
        mLayoutInflater = LayoutInflater.from(context);
        mviewType = viewType;
        if(WORDS_ONE==viewType || WORDS_TWO==viewType) {
            mData = data;
            LogUtils.d("这是打印单词的");
        }else if(TWO==viewType){
            mtitle = data;
            LogUtils.d(mtitle.toString());
            LogUtils.d("打印数量"+mtitle.size());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtils.d("打印子类类型："+viewType);
        if (WORDS_ONE==viewType) {
            LogUtils.d("这是绑定单词的list！");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_words, parent, false);
            ItemViewHolder vh = new ItemViewHolder(view);
            //将创建的View注册点击事件
            view.setOnClickListener(this);
            return vh;
        }else if (TWO==viewType){
            LogUtils.d("这是绑定阅读的item！！！！！！！！！！！！！！");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reading_home, parent, false);
            TwoItemViewHolder vh = new TwoItemViewHolder(view);
            //将创建的View注册点击事件
            view.setOnClickListener(this);
            return vh;
        }else if (WORDS_TWO==viewType) {
            LogUtils.d("这是绑定生词本的item！！！！！！！！！！！！！！");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_strage_words_two, parent, false);
            ThreeItemViewHolder vh = new ThreeItemViewHolder(view);
            //将创建的View注册点击事件
            view.setOnClickListener(this);
            return vh;
        }
            return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(WORDS_ONE==mviewType){
            LogUtils.d("返回类型："+WORDS_ONE);
            return WORDS_ONE;
        } else if (TWO==mviewType){
            LogUtils.d("返回类型："+TWO);
            return TWO;
        }else if (WORDS_TWO==mviewType){
            LogUtils.d("返回类型："+WORDS_TWO);
            return WORDS_TWO;
        }
        return 0;
    }

    //最后暴露给外面的调用者，定义一个设置Listener的方法（）：
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            LogUtils.d("绑定单词的布局");
            ((ItemViewHolder)holder).setData(mData.get(position));
        }else if (holder instanceof TwoItemViewHolder){
            LogUtils.d("绑定其他的界面");
            ((TwoItemViewHolder)holder).setData(mtitle.get(position));
        }else if (holder instanceof ThreeItemViewHolder){
            LogUtils.d("绑定生词本界面");
            ((ThreeItemViewHolder)holder).setData(mData.get(position));
        }
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (WORDS_ONE==mviewType || WORDS_TWO==mviewType) {
            return mData.size();
        }else{
            LogUtils.d("打印数量2"+mtitle.size());
            return mtitle.size();
        }
    }

    /**
     * Method name : onClick
     * Specific description :继承View.OnClickListener 接口，实现这个点击方法
     *@param    view
     *@return void
     */
    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public View vBackground; // 背景
        public View vItem;
        public TextView tvItem; // 滑动的view
        public TextView tvSymbol;
        public TextView tvExplain;
//        public ImageView implay;
        public ImageView ivDone;
        public ImageView ivSchedule;

        public ItemViewHolder(View itemView) {
            super(itemView);
            vBackground = itemView.findViewById(R.id.rl_background);
            vItem = itemView.findViewById(R.id.ll_item);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
            tvSymbol = (TextView) itemView.findViewById(R.id.tv_symbol);
            tvExplain = (TextView) itemView.findViewById(R.id.tv_explain);
//            implay = (ImageView) itemView.findViewById(R.id.tv_item);
            ivDone = (ImageView) itemView.findViewById(R.id.iv_done);
            ivSchedule = (ImageView) itemView.findViewById(R.id.iv_schedule);
        }

        //绑定数据
        public void setData(Words data){
            tvItem.setText(data.getWord());
            tvSymbol.setText(data.getSymbol());
            tvExplain.setText(data.getExplain());
//            tvExplain.setText("");
        }

    }

    public static class TwoItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_reading_title;


        public TwoItemViewHolder(View itemView) {
            super(itemView);
            tv_reading_title = (TextView) itemView.findViewById(R.id.tv_reading_title);
        }
        //绑定数据
        public void setData(String title){
            LogUtils.d(title);
            tv_reading_title.setText(title);
        }

    }

    public static class ThreeItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_word;
        public TextView tv_symbol;
        public TextView tv_explain;
        public ImageView im_delete;


        public ThreeItemViewHolder(View itemView) {
            super(itemView);
            tv_word = (TextView) itemView.findViewById(R.id.tv_word);
            tv_symbol = (TextView) itemView.findViewById(R.id.tv_symbol);
            tv_explain = (TextView) itemView.findViewById(R.id.tv_explain);
            im_delete = (ImageView) itemView.findViewById(R.id.im_delete);
        }
        //绑定数据
        public void setData(Words data){
            LogUtils.d(data.getWord());
            if (null == data) {
                LogUtils.d("生词列表：数据为空！！！");
            }else {
                //绑定数据
                LogUtils.d("打印生词本words："+data.getWord());
                tv_word.setText(data.getWord());
                tv_symbol.setText(data.getSymbol());
                tv_explain.setText(data.getExplain());
            }
        }

    }
}
