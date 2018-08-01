package com.echo.quick.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.echo.quick.activities.R;
import com.echo.quick.contracts.WordsContract;
import com.echo.quick.model.dao.impl.WordsLogImpl;
import com.echo.quick.model.dao.interfaces.IWordsLogDao;
import com.echo.quick.pojo.Words;

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
    public void liefSwipe(Words word) {
        int num;
        IWordsLogDao = new WordsLogImpl();
        num = IWordsLogDao.selectLeftNum(word);
        Log.d("left num    =    ", "   "+num);
        IWordsLogDao.updateLeftNum(word.getWord(), num+1);
    }

    @Override
    public void rightSwipe(Words word) {
        int num;
        IWordsLogDao = new WordsLogImpl();
        num = IWordsLogDao.selectRightNum(word);
        Log.d("right num    =    ", "   "+num);
        IWordsLogDao.updateRightNum(word.getWord(), num+1);
    }

    @Override
    public void endOnce(Context context) {

        popWindow(context);

    }


    public void popWindow(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("今日份已完成");
//        final EditText editText;
//        builder.setView(editText = new EditText(AthleticsActivity.this));
        builder.setIcon(R.drawable.boy);
        builder.setNegativeButton("打卡结束喽", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                iWordsView.RefreshPage(true);
            }
        });
        builder.setNeutralButton("上传数据", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }


}
