package com.echo.quick.presenters;

import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.model.dao.impl.WordsNewImpl;
import com.echo.quick.model.dao.interfaces.WordsNewDao;
import com.echo.quick.pojo.Words;
import com.echo.quick.pojo.Words_New;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/23 15:23
 * 修改人：zhou-jx
 * 修改时间：2018/7/23 15:23
 * 修改备注：
 */

public class OnlineWordPresenterImpl implements OnlineWordContract.OnlineWordPresenter {

    @Override
    public List<Words> getOnlineWord(HashMap<String, String> map) {

        final List<Words> data = new ArrayList<>();

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


//        OnlineWord onlineWord = new OnlineWordImpl();
//        onlineWord.postToWord(map, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                LogUtils.d("无法获取OnlineWord");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                try {
//                    JSONArray jsonArray = JSONObject.parseArray(response.body().string());
////                    WordsNewDao wordsNew = new WordsNewImpl();
//                    for (int i = 0; i < jsonArray.size(); i++){
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        Words words = new Words(object.getString("word"), object.getString("phon"), object.getString("para"));
//                        LogUtils.d(object.getString("word")+"       "+object.getString("para"));
//                        data.add(words);
////
////                        Words_New words_new = new Words_New();
////                        words_new.setWordId(object.getString("id"));
////                        words_new.setWord(object.getString("word"));
////                        words_new.setExplain(object.getString("para"));
////                        words_new.setPron(object.getString("pron"));
////                        words_new.setSymbol(object.getString("phon"));
////                        wordsNew.update(words_new);
//                    }
////                    for(Words_New words : wordsNew.select()){
////                        LogUtils.d(words.getWord());
////                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                    LogUtils.d("错误信息："+response.toString());
//                }
//
//            }
//        });


        return data;
    }
}
