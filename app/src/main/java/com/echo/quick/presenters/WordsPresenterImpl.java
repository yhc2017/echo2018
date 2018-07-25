package com.echo.quick.presenters;

import com.echo.quick.contracts.WordsContract;
import com.echo.quick.model.dao.impl.WordsLogImpl;
import com.echo.quick.model.dao.interfaces.WordsLogDao;
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

    private WordsLogDao wordsLogDao;

    @Override
    public void liefSwipe(Words word) {
        int num;
        wordsLogDao = new WordsLogImpl();
        num = wordsLogDao.selectNum(word);
        wordsLogDao.updateNum(word.getWordId(), num+1);
    }

    @Override
    public void rightSwipe(Words word) {
        int num;
        wordsLogDao = new WordsLogImpl();
        num = wordsLogDao.selectNum(word);
        if(num < 3){
            wordsLogDao.updateNum(word.getWordId(), 3);
        }else {
            wordsLogDao.updateNum(word.getWordId(), num+1);
        }
    }

}
