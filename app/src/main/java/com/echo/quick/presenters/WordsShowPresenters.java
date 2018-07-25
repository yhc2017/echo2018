package com.echo.quick.presenters;

import com.echo.quick.contracts.WordsShowContract;
import com.echo.quick.model.dao.impl.WordsNewImpl;
import com.echo.quick.model.dao.interfaces.WordsNewDao;
import com.echo.quick.pojo.Words_New;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/24 14:57
 * 修改人：zhou-jx
 * 修改时间：2018/7/24 14:57
 * 修改备注：
 */

public class WordsShowPresenters implements WordsShowContract.IWordsShowPresenter {

    WordsShowContract.IWordsShowView view;

    public WordsShowPresenters(WordsShowContract.IWordsShowView view){
        this.view = view;
    }

    @Override
    public void isExist(String word) {
        WordsNewDao newDao = new WordsNewImpl();
        if(newDao.isExist(word)){
            view.initVisibility(true);
        }else {
            view.initVisibility(false);
        }
    }

    @Override
    public boolean addNewWord(Words_New wordsNew) {
        WordsNewDao newDao = new WordsNewImpl();

        if(newDao.update(wordsNew))
            return true;

        return false;
    }

    @Override
    public boolean delNewWord(String wordId) {
        WordsNewDao newDao = new WordsNewImpl();
        newDao.delete(wordId);
        return false;
    }
}
