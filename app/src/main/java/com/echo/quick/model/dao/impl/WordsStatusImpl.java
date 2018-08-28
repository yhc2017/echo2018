package com.echo.quick.model.dao.impl;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.Words;
import com.echo.quick.pojo.Words_Status;

import org.json.JSONException;
import org.litepal.LitePal;

import java.text.SimpleDateFormat;
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

            case "new":
                return LitePal.where("status = ?", "new").find(Words_Status.class);

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

            case "new":
                return LitePal.where("status = ? and topicId = ?", "new", topicId).find(Words_Status.class);


            case "review":
                return LitePal.where("status = ? and topicId = ?", "review", topicId).find(Words_Status.class);

            case "learn":
                return LitePal.where("status = ? and topicId = ?", "learn", topicId).find(Words_Status.class);

            case "learn_":
                return LitePal.where("(status = ? or status = ? or status = ?) and topicId = ?",  "", "new", "learn" , topicId).find(Words_Status.class);

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

            case "new":
                return LitePal.where("status = ?", "new").find(Words_Status.class).size();

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
            case "new":
                return LitePal.where("status = ? and topicId = ?",  "new", topicId).find(Words_Status.class).size();

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

            case "all":
                return LitePal.where("topicId = ?",  topicId).find(Words_Status.class).size();

            default:
                return -1;
        }
    }

    @Override
    public int selectCountByStatusAndTopicIdToday(String request, String topicId) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
        String time = df.format(System.currentTimeMillis());
        switch (request){
            case "":
                return LitePal.where("status = ? and topicId = ? and recordTime = ?",  "", topicId, time).find(Words_Status.class).size();
            case "new":
                return LitePal.where("status = ? and topicId = ? and recordTime = ?",  "new", topicId, time).find(Words_Status.class).size();

            case "learn":
                return LitePal.where("status = ? and topicId = ? and recordTime = ?",  "learn", topicId, time).find(Words_Status.class).size();

            case "review":
                return LitePal.where("status = ? and topicId = ? and recordTime = ?",  "review", topicId, time).find(Words_Status.class).size();

            case "grasp":
                return LitePal.where("status = ? and topicId = ? and recordTime = ?",  "grasp", topicId, time).find(Words_Status.class).size();

            case "study":
                return LitePal.where("status = ? and topicId = ? and recordTime = ?",  "study", topicId, time).find(Words_Status.class).size();

            case "review_grasp":
                return LitePal.where("(status = ? or status = ?) and topicId = ? and recordTime = ?",  "review", "grasp", topicId, time).find(Words_Status.class).size();

            case "learn_review":
                return LitePal.where("(status = ? or status = ?) and topicId = ? and recordTime = ?",  "learn", "review", topicId, time).find(Words_Status.class).size();

            case "learn_":
                return LitePal.where("(status = ? or status = ?) and topicId = ? and recordTime = ?",  "", "review", topicId, time).find(Words_Status.class).size();

            case "All":
                return LitePal.where("recordTime = ?",  time).find(Words_Status.class).size();

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

        return wordsNew.updateAll("word = ?", words.getWord()) > 0;
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

    //为了让Word实例统一，在这里做统一方法
    @NonNull
    public static  Words_Status getWordsByStatus(String status, org.json.JSONObject object) throws JSONException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
        String time = df.format(System.currentTimeMillis());
        return new Words_Status(object.getString("id"),
                object.getString("pron"),
                object.getString("phon"),
                object.getString("word"),
                object.getString("para"),
                object.getString("build"),
                object.getString("example"),
                "",
                "",
                status,
                object.getString("topicId"),
                time);
    }

    @NonNull
    public static  Words_Status getWordsByStatusFastJson(String status, JSONObject object) throws JSONException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
        String time = df.format(System.currentTimeMillis());
        return new Words_Status(object.getString("id"),
                object.getString("pron"),
                object.getString("phon"),
                object.getString("word"),
                object.getString("para"),
                object.getString("build"),
                object.getString("example"),
                "",
                "",
                status,
                object.getString("topicId"),
                time);
    }

    @NonNull
    public static  Words_Status getWordsByStatusWord(Words words){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
        String time = df.format(System.currentTimeMillis());
        return new Words_Status(words.getWordId(),
                words.getPron(),
                words.getWord(),
                words.getSymbol(),
                words.getExplain(),
                words.getEg1(),
                words.getEg1_chinese(),
                words.getEg2(),
                words.getEg2_chinese(),
                "new",
                words.getTopicId(),
                time);
    }
}
