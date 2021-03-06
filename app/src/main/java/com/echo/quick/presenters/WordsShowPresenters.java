package com.echo.quick.presenters;

import com.echo.quick.contracts.WordsShowContract;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.Words;
import com.echo.quick.pojo.Words_Status;

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

    public WordsShowPresenters(){}

    public WordsShowPresenters(WordsShowContract.IWordsShowView view){
        this.view = view;
    }

    @Override
    public void isExist(String word) {
        IWordsStatusDao newDao = new WordsStatusImpl();
        if(newDao.isExist(word)){
            view.initVisibility(true);
        }else {
            view.initVisibility(false);
        }
    }

    @Override
    public boolean addNewWord(Words_Status wordsNew) {
        IWordsStatusDao newDao = new WordsStatusImpl();

        return newDao.update(wordsNew);

    }

    @Override
    public boolean updateWord(Words_Status word) {

        IWordsStatusDao newDao = new WordsStatusImpl();
        return newDao.updateByWord(word);

    }

    @Override
    public boolean addWordToStatus(Words words) {
        Words_Status wordsNew = WordsStatusImpl.getWordsByStatusWord(words);
        IWordsStatusDao newDao = new WordsStatusImpl();

        return newDao.update(wordsNew);

    }

    @Override
    public boolean delNewWord(String word) {
        IWordsStatusDao newDao = new WordsStatusImpl();
        newDao.delete(word);
        return false;
    }
}
