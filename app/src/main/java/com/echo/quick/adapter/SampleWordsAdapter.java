package com.echo.quick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.echo.quick.activities.R;
import com.echo.quick.pojo.Words_Status;
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

public class SampleWordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Words_Status> mData;
    private List<String> mtitle;
    private OnItemClickListener mOnItemClickListener = null;
    View view;
    int mviewType;
    final int WORDS_ONE = 1;
    final int WORDS_TWO = 3;
    final int WORDS_THREE = 4;
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
        if(WORDS_ONE==viewType || WORDS_TWO==viewType || WORDS_THREE==viewType) {
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
            return vh;
        }else if (TWO==viewType){
            LogUtils.d("这是绑定阅读的item！！！！！！！！！！！！！！");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reading_home, parent, false);
            TwoItemViewHolder vh = new TwoItemViewHolder(view);
            return vh;
        }else if (WORDS_TWO==viewType) {
            LogUtils.d("这是绑定生词本的item！！！！！！！！！！！！！！");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_strage_words_two, parent, false);
            ThreeItemViewHolder vh = new ThreeItemViewHolder(view);
            return vh;
        }else if (WORDS_THREE == viewType){
            LogUtils.d("这是绑定生词本的item！！！！！！！！！！！！！！");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_strage_words_two, parent, false);
            ThreeItemViewHolder vh = new ThreeItemViewHolder(view);
            vh.bt_del.setVisibility(View.GONE);
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
        }else if (WORDS_THREE == mviewType){
            LogUtils.d("返回类型："+WORDS_THREE);
            return WORDS_THREE;
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
        if (WORDS_ONE==mviewType || WORDS_TWO==mviewType || WORDS_THREE==mviewType) {
            return mData.size();
        }else{
            LogUtils.d("打印数量2"+mtitle.size());
            return mtitle.size();
        }
    }


    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }


    public  class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View vBackground; // 背景
        public View vItem;
        public TextView tvItem; // 滑动的view
        public TextView tvSymbol;
        public TextView tvExplain;
//        public ImageView implay;
        public ImageView ivDone,iv_play;
        public ImageView ivSchedule;
        public LinearLayout ll_word;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ll_word = (LinearLayout)itemView.findViewById(R.id.ll_word);
            vBackground = itemView.findViewById(R.id.rl_background);
            vItem = itemView.findViewById(R.id.ll_item);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
            tvSymbol = (TextView) itemView.findViewById(R.id.tv_symbol);
            tvExplain = (TextView) itemView.findViewById(R.id.tv_explain);
            ivDone = (ImageView) itemView.findViewById(R.id.iv_done);
            ivSchedule = (ImageView) itemView.findViewById(R.id.iv_schedule);
            iv_play = (ImageView)itemView.findViewById(R.id.iv_play);

            //事件监听设置
//            ll_word.setOnClickListener(this);
//            iv_play.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        //绑定数据
        public void setData(Words_Status data){
            tvItem.setText(data.getWord());
            tvSymbol.setText(data.getSymbol());
            tvExplain.setText(data.getExplain());
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取position
                mOnItemClickListener.onItemClick(view,(int)view.getTag());
            }
        }
    }

    public  class TwoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
    }

    public  class ThreeItemViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public TextView tv_word;
        public TextView tv_symbol;
        public TextView tv_explain;
        public Button bt_del;
        public LinearLayout ll_item;


        public ThreeItemViewHolder(View itemView) {
            super(itemView);
            ll_item = (LinearLayout)itemView.findViewById(R.id.ll_item);
            tv_word = (TextView) itemView.findViewById(R.id.tv_word);
            tv_symbol = (TextView) itemView.findViewById(R.id.tv_symbol);
            tv_explain = (TextView) itemView.findViewById(R.id.tv_explain);
            bt_del = (Button) itemView.findViewById(R.id.bt_del);

            //绑定事件
            ll_item.setOnClickListener(this);
            bt_del.setOnClickListener(this);
        }
        //绑定数据
        public void setData(Words_Status data){
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

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取position
                mOnItemClickListener.onItemClick(view,getAdapterPosition());
            }
        }
    }
}
