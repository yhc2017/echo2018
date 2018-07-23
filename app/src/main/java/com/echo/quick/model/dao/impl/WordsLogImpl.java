package com.echo.quick.model.dao.impl;

import com.echo.quick.model.dao.interfaces.WordsLogDao;
import com.echo.quick.pojo.Words_Log;

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

public class WordsLogImpl implements WordsLogDao {

    Words_Log words;


    @Override
    public List<Words_Log> select() {

        return LitePal.findAll(Words_Log.class);
    }

    @Override
    public boolean update(Words_Log wLog) {

        words = new Words_Log();
        words = wLog;

        return words.save();

    }

    @Override
    public boolean updateNum(String wordId, int num) {

        Words_Log wordsLog = new Words_Log();
        wordsLog.setNum(num);
        try {
            wordsLog.updateAll("wordId = ?", wordId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public void delete(String id) {
        LitePal.deleteAll(Words_Log.class, "wordId = ?", id);
    }
}
