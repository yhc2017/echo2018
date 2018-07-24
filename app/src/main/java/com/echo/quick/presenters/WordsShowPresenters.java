package com.echo.quick.presenters;

import com.echo.quick.contracts.WordsShowContracts;
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

public class WordsShowPresenters implements WordsShowContracts.IWordsShowPresenter {

    @Override
    public void isExist(String word) {

    }

    @Override
    public boolean addNewWord(Words_New wordsNew) {
        return false;
    }

    @Override
    public boolean delNewWord(String word) {
        return false;
    }
}
