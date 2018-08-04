package com.echo.quick.model.dao.impl;

import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.Words_Status;

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

public class WordsStatusImpl implements IWordsStatusDao {

    private Words_Status wordsNew;


    @Override
    public List<Words_Status> select() {

        return LitePal.findAll(Words_Status.class);
    }

    @Override
    public List<Words_Status> selectByStatus(String status) {

        return LitePal.where("status = ?", status).find(Words_Status.class);
    }

    @Override
    public int selectCount() {

        return LitePal.where("status = ?",  "").find(Words_Status.class).size();
    }

    @Override
    public List<Words_Status> selectNotGrasp() {
        return LitePal.where("status != ?", "grasp").find(Words_Status.class);
    }

    @Override
    public boolean update(Words_Status wNew) {

        wordsNew = new Words_Status();
        wordsNew = wNew;

        return wordsNew.save();

    }

    @Override
    public boolean updateByWord(Words_Status words) {

        wordsNew = new Words_Status();
        wordsNew.setStatus(words.getStatus());

        if(wordsNew.updateAll("word = ?", words.getWord()) > 0){
            return true;
        }
        return false;
    }

    @Override
    public void delete(String word) {
        LitePal.deleteAll(Words_Status.class, "word = ?", word);
    }

    @Override
    public boolean isExist(String word) {

        try {
            if(LitePal.where("word = ? and status = ?", word, "new").find(Words_Status.class).isEmpty()){
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }


        return true;
    }
}
