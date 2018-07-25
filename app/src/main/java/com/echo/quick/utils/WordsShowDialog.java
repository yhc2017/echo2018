package com.echo.quick.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.echo.quick.activities.R;
import com.echo.quick.contracts.WordsShowContracts;
import com.echo.quick.pojo.Words;
import com.echo.quick.pojo.Words_New;
import com.echo.quick.presenters.WordsShowPresenters;

/**
 * 文件名：WordsShowDialog
 * 创建人：HUAHUA
 * 创建时间：2018/7/19
 * 类描述：
 *
 * 修改人：周少侠
 * 修改时间：2018/7/19 14:14
 * 修改内容：增加了增删生词的监听操作
 *
**/

public class WordsShowDialog extends Dialog implements WordsShowContracts.IWordsShowView,View.OnClickListener{
    Context context;
    Words words;

    private TextView tv_item,tv_symbol,tv_explain,tv_eg1,tv_eg1_chinese,tv_eg2,tv_eg2_chinese,tv_add_new,tv_del_new;

    WordsShowContracts.IWordsShowPresenter wordsShowPresenter;

    public WordsShowDialog(@NonNull Context context, Words words) {
        super(context);
        this.context = context;
        this.words = words;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_words_dialog);

        wordsShowPresenter = new WordsShowPresenters(this);

        //按空白处可以取消动画
        setCanceledOnTouchOutside(true);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();

        //初始化界面控件的事件
        initEvent();
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
        tv_add_new = (TextView)findViewById(R.id.tv_add_new);
        tv_del_new = (TextView)findViewById(R.id.tv_del_new);
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

    /**
     * 方法名称：initEvent
     * 方法描述: 初始化事件
     * @return void
     **/
    private void initEvent() {



        wordsShowPresenter.isExist(words.getWord());

        MyListener listener = new MyListener();

        tv_add_new.setOnClickListener(listener);
        tv_del_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d("监听中");
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_add_new:
                Words_New wordsNew = new Words_New(words.getWordId(),
                        words.getPron(),
                        words.getWord(),
                        words.getSymbol(),
                        words.getExplain(),
                        words.getEg1(),
                        words.getEg1_chinese(),
                        words.getEg2(),
                        words.getEg2_chinese());
                wordsShowPresenter.addNewWord(wordsNew);

                break;

            case R.id.tv_del_new:
//                    wordsShowPresenter.delNewWord(words.getWord());
                LogUtils.d("监听中");
                break;

            default:
                break;
        }
    }

    public class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_add_new:
                    Words_New wordsNew = new Words_New(words.getWordId(),
                            words.getPron(),
                            words.getWord(),
                            words.getSymbol(),
                            words.getExplain(),
                            words.getEg1(),
                            words.getEg1_chinese(),
                            words.getEg2(),
                            words.getEg2_chinese());
                    wordsShowPresenter.addNewWord(wordsNew);

                    break;

                case R.id.tv_del_new:
//                    wordsShowPresenter.delNewWord(words.getWord());
                    LogUtils.d("监听中");
                    break;

                default:
                    break;
            }
        }
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

    @Override
    public void initVisibility(Boolean res) {

        if(res){
            tv_add_new.setVisibility(TextView.INVISIBLE);
        }else {
            tv_del_new.setVisibility(TextView.INVISIBLE);
        }

    }
}
