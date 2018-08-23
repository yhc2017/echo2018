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
    public List<Words_Status> selectByTopicId(String topicId) {
        return LitePal.where("topicId = ?", topicId).find(Words_Status.class);
    }

    @Override
    public List<Words_Status> selectByStatus(String status) {
        switch (status){
            case "":
                return LitePal.where("status = ?", "").find(Words_Status.class);

            case "review":
                return LitePal.where("status = ?", "review").find(Words_Status.class);

            case "learn":
                return LitePal.where("status = ?", "learn").find(Words_Status.class);

            case "learn_":
                return LitePal.where("status = ? or status = ?",  "", "review").find(Words_Status.class);

            default:
                return LitePal.where("status = ?", status).find(Words_Status.class);
        }

    }

    @Override
    public List<Words_Status> selectByStatusAndTopicId(String status, String topicId) {
        switch (status){
            case "":
                return LitePal.where("status = ? and topicId = ?", "", topicId).find(Words_Status.class);

            case "review":
                return LitePal.where("status = ? and topicId = ?", "review", topicId).find(Words_Status.class);

            case "learn":
                return LitePal.where("status = ? and topicId = ?", "learn", topicId).find(Words_Status.class);

            case "learn_":
                return LitePal.where("(status = ? or status = ?) and topicId = ?",  "", "learn" , topicId).find(Words_Status.class);

            case "all":
                return LitePal.where("topicId = ?", topicId).find(Words_Status.class);

            default:
                return LitePal.where("status = ? and topicId = ?", status, topicId).find(Words_Status.class);
        }
    }

    @Override
    public int selectCount(String request) {
        switch (request){

            case "":
                return LitePal.where("status = ?",  "").find(Words_Status.class).size();

            case "learn":
                return LitePal.where("status = ?",  "learn").find(Words_Status.class).size();

            case "review":
                return LitePal.where("status = ?",  "review").find(Words_Status.class).size();

            case "grasp":
                return LitePal.where("status = ?",  "grasp").find(Words_Status.class).size();

            case "study":
                return LitePal.where("status = ?",  "study").find(Words_Status.class).size();

            case "review_grasp":
                return LitePal.where("status = ? or status = ?",  "review", "grasp").find(Words_Status.class).size();

            case "learn_review":
                return LitePal.where("status = ? or status = ?",  "", "review").find(Words_Status.class).size();

            default:
                return -1;
        }
    }

    @Override
    public int selectCountByStatusAndTopicId(String request, String topicId) {
        switch (request){
            case "":
                return LitePal.where("status = ? and topicId = ?",  "", topicId).find(Words_Status.class).size();

            case "learn":
                return LitePal.where("status = ? and topicId = ?",  "learn", topicId).find(Words_Status.class).size();

            case "review":
                return LitePal.where("status = ? and topicId = ?",  "review", topicId).find(Words_Status.class).size();

            case "grasp":
                return LitePal.where("status = ? and topicId = ?",  "grasp", topicId).find(Words_Status.class).size();

            case "study":
                return LitePal.where("status = ? and topicId = ?",  "study", topicId).find(Words_Status.class).size();

            case "review_grasp":
                return LitePal.where("(status = ? or status = ?) and topicId = ?",  "review", "grasp", topicId).find(Words_Status.class).size();

            case "learn_review":
                return LitePal.where("(status = ? or status = ?) and topicId = ?",  "learn", "review", topicId).find(Words_Status.class).size();

            case "learn_":
                return LitePal.where("(status = ? or status = ?) and topicId = ?",  "", "review", topicId).find(Words_Status.class).size();
            default:
                return -1;
        }
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

    @Override
    public boolean detectionEmpty() {
        return LitePal.findAll(Words_Status.class).size() == 0;
    }
}
