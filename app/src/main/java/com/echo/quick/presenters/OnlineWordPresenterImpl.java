package com.echo.quick.presenters;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.model.dao.impl.OnlineWordImpl;
import com.echo.quick.model.dao.impl.WordsLogImpl;
import com.echo.quick.model.dao.impl.WordsNewImpl;
import com.echo.quick.model.dao.interfaces.OnlineWord;
import com.echo.quick.model.dao.interfaces.WordsLogDao;
import com.echo.quick.model.dao.interfaces.WordsNewDao;
import com.echo.quick.pojo.Words;
import com.echo.quick.pojo.Words_Log;
import com.echo.quick.pojo.Words_New;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/23 15:23
 * 修改人：zhou-jx
 * 修改时间：2018/7/23 15:23
 * 修改备注：
 */

public class OnlineWordPresenterImpl extends BasePresenter implements OnlineWordContract.OnlineWordPresenter {

    App app = (App) App.getContext();

    @Override
    public List<Words> getOnlineWord(HashMap<String, String> map) {

        final List<Words> data = new ArrayList<>();

        OnlineWord onlineWord = new OnlineWordImpl();
        onlineWord.postToWord(map, "words/selectWords", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("无法获取OnlineWord");
                WordsNewDao newDao = new WordsNewImpl();
                List<Words_New> news = newDao.select();
                for(Words_New word: news){
                    data.add(new Words(word.getWordId(),
                            word.getPron(),
                            word.getWord(),
                            word.getSymbol(),
                            word.getExplain(),
                            word.getEg1(),
                            word.getEg1Chinese(),
                            word.getEg2(),
                            word.getEg2Chinese()));
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONArray jsonArray = JSONObject.parseArray(response.body().string());
//                    WordsNewDao wordsNew = new WordsNewImpl();
                    for (int i = 0; i < jsonArray.size(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        Words words = new Words(
                                object.getString("id"),
                                object.getString("word"),
                                object.getString(""),
                                object.getString("para"),
                                object.getString("phon"),
                                object.getString("build"),
                                object.getString("example"),
                                object.getString(""),
                                object.getString(""));
                        LogUtils.d(object.getString("id")+"       "+object.getString("para"));
                        data.add(words);
                    }
//                    for(Words_New words : wordsNew.select()){
//                        LogUtils.d(words.getWord());
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                    WordsNewDao newDao = new WordsNewImpl();
                    List<Words_New> news = newDao.select();
                    for(Words_New word: news){
                        data.add(new Words(word.getWordId(),
                                word.getPron(),
                                word.getWord(),
                                word.getSymbol(),
                                word.getExplain(),
                                word.getEg1(),
                                word.getEg1Chinese(),
                                word.getEg2(),
                                word.getEg2Chinese()));
                    }
                    LogUtils.d("错误信息："+response.toString());

                }

            }
        });


        return data;
    }

    @Override
    public List<Words> getOnlineSprint(HashMap<String, String> map) {

        final List<Words> data = new ArrayList<>();

        final OnlineWord onlineWord = new OnlineWordImpl();


        onlineWord.postToWord(map, "quick/paper/getReadingInfo", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort(App.getContext(), "申请出错");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                JSONArray jsonArray = JSONObject.parseArray(response.body().string());

                JSONObject res = jsonArray.getJSONObject(0);

                JSONArray jsonArray1 = JSONObject.parseArray(res.getString("wordCet4HighList"));

                app.setContent(res.getString("content"));
                app.setTranslation(res.getString("translation"));

                for (int i = 0; i < jsonArray1.size(); i++){
                    JSONObject object = jsonArray1.getJSONObject(i);
                    Words words = new Words(
                            object.getString("id"),
                            object.getString("phon"),
                            object.getString("word"),
                            object.getString(""),
                            object.getString("para"),
                            object.getString("build"),
                            object.getString("example"),
                            object.getString(""),
                            object.getString(""));
                    LogUtils.d(object.getString("id")+"       "+object.getString("para"));
                    data.add(words);
                }

                app.setList(data);

            }
        });


        return data;
    }

    @Override
    public List<String> getOnlineSprintType() {
        final List<String> data = new ArrayList<>();

        final OnlineWord onlineWord = new OnlineWordImpl();

        onlineWord.getToWord("quick/paper/getPaperList", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort(App.getContext(), "getOnlineSprintType()---申请出错");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONArray jsonArray = JSONObject.parseArray(response.body().string());
                for (int i = 0; i < jsonArray.size(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String res = object.getString("paperDate");
                    data.add(res);
                    LogUtils.d(res);
                }
                app.setPagerList(data);
            }
        });

        return data;
    }

    @Override
    public void postOnlineWordsLog() {
        WordsLogDao logDao = new WordsLogImpl();
        List<Words_Log> logs = logDao.select();
        String json = "[";
        for (Words_Log log:logs){
            String body = "{" +
                    "'wordId':"+log.getWordId()+
                    "',word':"+log.getWord()+
                    "',num':"+log.getNum()+
                    "},";
            json += body;
        }
        json += "]";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("logs", json);
        LogUtils.d(jsonObject.toString());
    }
}
