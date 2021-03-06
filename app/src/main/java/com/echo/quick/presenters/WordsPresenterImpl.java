package com.echo.quick.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.echo.quick.activities.R;
import com.echo.quick.contracts.WordsContract;
import com.echo.quick.model.dao.impl.WordsLogImpl;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsLogDao;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.Words_Status;
import com.echo.quick.service.AudioPlayerService;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.ToastUtils;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/25 14:42
 * 修改人：zhou-jx
 * 修改时间：2018/7/25 14:42
 * 修改备注：
 */

public class WordsPresenterImpl implements WordsContract.IWordsPresenter {

    private IWordsLogDao IWordsLogDao;

    private WordsContract.IWordsView iWordsView;

    public WordsPresenterImpl(){

    }

    public WordsPresenterImpl(WordsContract.IWordsView iWordsView){
        this.iWordsView = iWordsView;
    }

    @Override
    public void leftSwipe(Words_Status word) {
        int num;
        IWordsLogDao = new WordsLogImpl();
        num = IWordsLogDao.selectRightNum(word);
        Log.d("right num    =    ", "   "+num);
        //设置滑动+1
        IWordsLogDao.updateRightNum(word.getWord(), num+1);
        if(num == 0){
            Words_Status wordsNew = new Words_Status();
            wordsNew.setStatus("review");
            wordsNew.setWord(word.getWord());
            IWordsStatusDao newDao = new WordsStatusImpl();
            if(newDao.updateByWord(wordsNew)){
                LogUtils.d("添加成功。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
            }
        }
        if(num == 1){
            Words_Status wordsNew = new Words_Status();
            wordsNew.setStatus("grasp");
            wordsNew.setWord(word.getWord());
            IWordsStatusDao newDao = new WordsStatusImpl();
            if(newDao.updateByWord(wordsNew)){
                LogUtils.d("添加成功。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
            }
        }
    }

    @Override
    public void rightSwipe(Words_Status word) {
        int num;
        IWordsLogDao = new WordsLogImpl();
        num = IWordsLogDao.selectLeftNum(word);
        IWordsLogDao.updateLeftNum(word.getWord(), num+1);
        if(num == 0){
            Words_Status wordsNew = new Words_Status();
            wordsNew.setStatus("study");
            wordsNew.setWord(word.getWord());
            IWordsStatusDao newDao = new WordsStatusImpl();
            if(newDao.updateByWord(wordsNew)){
                LogUtils.d("添加成功。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
            }
        }
    }

    @Override
    public void endOnce(Context context) {

        popWindow(context);

    }

    @Override
    public void play(Context context, String url) {
        if(url != null && !url.equals("")){
            try{
                Intent intent = new Intent(context, AudioPlayerService.class);
                intent.putExtra("path", url);
                context.startService(intent);
            }catch (Exception e){
                e.printStackTrace();
                ToastUtils.showLong(context, "音频播放失败");
            }
        }else {
            ToastUtils.showLong(context, "暂无该音频");
        }
    }


    private void popWindow(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("今日份已完成");
//        final EditText editText;
//        builder.setView(editText = new EditText(AthleticsActivity.this));
        builder.setIcon(R.drawable.boy);
        builder.setNegativeButton("打卡结束喽", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                iWordsView.RefreshPage("");
            }
        });
        builder.setNeutralButton("低调低调", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                iWordsView.RefreshPage("log");
            }
        });
//        builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
        builder.show();
    }


}
