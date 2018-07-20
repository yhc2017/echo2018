package com.echo.quick.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.echo.quick.activities.R;
import com.echo.quick.pojo.Words;

/**
 * Created by HUAHUA on 2018/7/19.
 */

public class WordsShowDialog extends Dialog {
    Context context;
    Words words;



    private TextView tv_item,tv_symbol,tv_explain,tv_eg1,tv_eg1_chinese,tv_eg2,tv_eg2_chinese;

    public WordsShowDialog(@NonNull Context context, Words words) {
        super(context);
        this.context = context;
        this.words = words;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_words_dialog);
        //按空白处可以取消动画
        setCanceledOnTouchOutside(true);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
//        //初始化界面控件的事件
//        initEvent();
    }
    /**
     * 初始化界面控件
     */
    private void initView() {
        tv_item = (TextView) findViewById(R.id.tv_item);
        tv_symbol = (TextView) findViewById(R.id.tv_symbol);
        tv_explain = (TextView) findViewById(R.id.tv_explain);
        tv_eg1 = (TextView) findViewById(R.id.tv_eg1);
        tv_eg1_chinese = (TextView) findViewById(R.id.tv_eg1_chinese);
        tv_eg2 = (TextView) findViewById(R.id.tv_eg2);
        tv_eg2_chinese = (TextView) findViewById(R.id.tv_eg2_chinese);
    }
    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {
        tv_item.setText(words.getWord());
        tv_symbol.setText(words.getSymbol());
        tv_explain.setText(words.getExplain());
        tv_eg1.setText(words.getEg1());
        tv_eg1_chinese.setText(words.getEg1_chinese());
        tv_eg2.setText(words.getEg2());
        tv_eg2_chinese.setText(words.getEg2_chinese());
    }



    public WordsShowDialog(@NonNull Context context) {
        super(context);
    }

    public WordsShowDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WordsShowDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
