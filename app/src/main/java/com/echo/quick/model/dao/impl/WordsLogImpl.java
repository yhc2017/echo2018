package com.echo.quick.model.dao.impl;

import com.echo.quick.model.dao.interfaces.IWordsLogDao;
import com.echo.quick.pojo.Words;
import com.echo.quick.pojo.Words_Log;
import com.echo.quick.utils.LogUtils;

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

public class WordsLogImpl implements IWordsLogDao {

    private Words_Log words;


    @Override
    public List<Words_Log> select() {

        return LitePal.findAll(Words_Log.class);
    }

    @Override
    public int selectCount() {

        int num = select().size();

        return num;
    }

    @Override
    public int selectLeftNum(Words w) {

        List<Words_Log> logs = LitePal.where("word = ?", w.getWord()).find(Words_Log.class);
        if(logs.isEmpty()){
            Words_Log wordsLog = new Words_Log();
            wordsLog.setWordId(w.getWordId());
            wordsLog.setWord(w.getWord());
            wordsLog.setTopicId(w.getTopicId());
            wordsLog.setLeftNum(0);
            wordsLog.setRightNum(0);
            wordsLog.save();
            LogUtils.d("word 不存在。");
            return -1;
        }
        else if(logs.size() != 1){
            LogUtils.d("word 不唯一。");
        }
        else {
            LogUtils.d("word Num = "+logs.get(0).getLeftNum());
        }
        return logs.get(0).getLeftNum();
    }

    @Override
    public int selectRightNum(Words w) {

        List<Words_Log> logs = LitePal.where("word = ?", w.getWord()).find(Words_Log.class);
        if(logs.isEmpty()){
            Words_Log wordsLog = new Words_Log();
            wordsLog.setWordId(w.getWordId());
            wordsLog.setWord(w.getWord());
            wordsLog.setTopicId(w.getTopicId());
            wordsLog.setLeftNum(0);
            wordsLog.setRightNum(0);
            wordsLog.save();
            LogUtils.d("word 不存在。");
            return -1;
        }
        else if(logs.size() != 1){
            LogUtils.d("word 不唯一。");
        }
        else {
            LogUtils.d("word Num = "+logs.get(0).getRightNum());
        }
        return logs.get(0).getRightNum();
    }

    @Override
    public boolean update(Words_Log wLog) {

        words = new Words_Log();
        words = wLog;

        return words.save();

    }

    @Override
    public boolean updateLeftNum(String word, int num) {

        Words_Log wordsLog = new Words_Log();
        wordsLog.setLeftNum(num);
        try {
            wordsLog.updateAll("word = ?", word);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateRightNum(String word, int num) {

        Words_Log wordsLog = new Words_Log();
        wordsLog.setRightNum(num);
        try {
            wordsLog.updateAll("word = ?", word);
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
