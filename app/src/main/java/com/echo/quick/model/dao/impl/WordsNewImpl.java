package com.echo.quick.model.dao.impl;

import com.echo.quick.model.dao.interfaces.WordsNewDao;
import com.echo.quick.pojo.Words_New;

import org.litepal.LitePal;

import java.util.List;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/23 16:30
 * 修改人：zhou-jx
 * 修改时间：2018/7/23 16:30
 * 修改备注：
 */

public class WordsNewImpl implements WordsNewDao {

    Words_New wordsNew;


    @Override
    public List<Words_New> select() {

        return LitePal.findAll(Words_New.class);
    }

    @Override
    public boolean update(Words_New wNew) {

        wordsNew = new Words_New();
        wordsNew = wNew;

        return wordsNew.save();

    }

    @Override
    public void delete(String id) {
        LitePal.deleteAll(Words_New.class, "wordId = ?", id);
    }

    @Override
    public boolean isExist(String word) {

        if(LitePal.where("word = ?", word).find(Words_New.class) != null)
            return true;

        return false;
    }
}
