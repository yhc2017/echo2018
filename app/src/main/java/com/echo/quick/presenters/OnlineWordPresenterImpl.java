package com.echo.quick.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.common.PreferenceConstants;
import com.echo.quick.contracts.HomeContract;
import com.echo.quick.contracts.LoginContract;
import com.echo.quick.contracts.MainContract;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.contracts.WordsContract;
import com.echo.quick.contracts.WordsShowContract;
import com.echo.quick.model.dao.impl.OnlineWordImpl;
import com.echo.quick.model.dao.impl.WordsLogImpl;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IOnlineWord;
import com.echo.quick.model.dao.interfaces.IWordsLogDao;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.Lexicon;
import com.echo.quick.pojo.Words;
import com.echo.quick.pojo.Words_Log;
import com.echo.quick.pojo.Words_Status;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.SPUtils;
import com.echo.quick.utils.ToastUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.echo.quick.activities.ReadingTranslateActivity.TAG;

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

    private App app = (App) App.getContext();

    private MainContract.IMainView iMainView;

    private HomeContract.IHomeView iHomeView;

    private LoginContract.ILoginView iLoginView;

    private WordsContract.IWordsView iWordsView;

    public OnlineWordPresenterImpl(){

    }

    public OnlineWordPresenterImpl(MainContract.IMainView iMainView){
        this.iMainView = iMainView;
    }

    public OnlineWordPresenterImpl(HomeContract.IHomeView iHomeView){
        this.iHomeView = iHomeView;
    }

    public OnlineWordPresenterImpl(LoginContract.ILoginView iLoginView){
        this.iLoginView = iLoginView;
    }

    public OnlineWordPresenterImpl(WordsContract.IWordsView iWordsView){
        this.iWordsView = iWordsView;
    }



    @Override
    public List<Words> getOnlineWord(HashMap<String, String> map) {

        final List<Words> data = new ArrayList<>();

        IOnlineWord IOnlineWord = new OnlineWordImpl();
        IOnlineWord.postToWord(map, "words/selectWords", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("无法获取OnlineWord");
                IWordsStatusDao newDao = new WordsStatusImpl();
                List<Words_Status> news = newDao.selectByTopicId(app.getTopicId());
                for(Words_Status word: news){
                    Words wd = new Words();
                    wd.setWordId(word.getWordId());
                    wd.setWord(word.getWord());
                    wd.setPron(word.getPron());
                    wd.setSymbol(word.getSymbol());
                    wd.setExplain(word.getExplain());
                    wd.setEg1(word.getEg1());
                    wd.setEg1_chinese(word.getEg1Chinese());
                    wd.setWordId(word.getWordId());
                    data.add(wd);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
//                    JSONArray jsonArray = JSONObject.parseArray(response.body().string());
                    JSONArray jsonArray = JSONObject.parseArray(response.body().string());
                    for (int i = 0; i < jsonArray.size(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        data.add(objectToWord(object));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    IWordsStatusDao newDao = new WordsStatusImpl();
                    List<Words_Status> news = newDao.selectByTopicId(app.getTopicId());
                    for(Words_Status word: news){
                        Words wd = new Words();
                        wd.setWordId(word.getWordId());
                        wd.setWord(word.getWord());
                        wd.setPron(word.getPron());
                        wd.setSymbol(word.getSymbol());
                        wd.setExplain(word.getExplain());
                        wd.setEg1(word.getEg1());
                        wd.setEg1_chinese(word.getEg1Chinese());
                        wd.setWordId(word.getWordId());
                        data.add(wd);
                    }
                }
                app.setList(data);

            }
        });


        return data;
    }

    @Override
    public void getOnlineWordReviewOrLearn(HashMap<String, String> map, final String rele) {

        final List<Words> data = new ArrayList<>();

        IOnlineWord IOnlineWord = new OnlineWordImpl();
        IOnlineWord.postToWord(map, "quick/reviewOrLearn/"+rele+"First", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("无法获取OnlineWord");
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String res = response.body().string();
                    LogUtils.d(res);
                    JSONObject jsonObject = JSONObject.parseObject(res);
                    JSONArray oldWords = jsonObject.getJSONArray("oldWords");
                    JSONArray newWords = jsonObject.getJSONArray("newWords");
                    if(newWords != null) {
                        initNewWord(newWords);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    IWordsStatusDao newDao = new WordsStatusImpl();
                    List<Words_Status> news = newDao.selectByTopicId(app.getTopicId());
                    for(Words_Status word: news){
                        Words wd = new Words();
                        wd.setWordId(word.getWordId());
                        wd.setWord(word.getWord());
                        wd.setPron(word.getPron());
                        wd.setSymbol(word.getSymbol());
                        wd.setExplain(word.getExplain());
                        wd.setEg1(word.getEg1());
                        wd.setEg1_chinese(word.getEg1Chinese());
                        wd.setWordId(word.getWordId());
                        data.add(wd);
                    }
                    app.setList(data);
                }
                //测试使用,关闭弹窗
//                iMainView.OverShowPL(false);

            }
        });

    }

    @Override
    public void getDynamicWordInfo(HashMap<String, String> map) {
        final List<Words> data = new ArrayList<>();

        IOnlineWord IOnlineWord = new OnlineWordImpl();
        IOnlineWord.postToWord(map, "quick/selectDynamicWordInfo", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = response.body().string();
                LogUtils.d(res);
                JSONArray newWords = JSON.parseArray(res);
                if(newWords != null) {
                    try {
                        initNewWord(newWords);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public List<Words> getOnlineSprint(HashMap<String, String> map) {

        final List<Words> data = new ArrayList<>();

        final IOnlineWord IOnlineWord = new OnlineWordImpl();


        IOnlineWord.postToWord(map, "quick/paper/getReadingInfo", new Callback() {
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
                            object.getString(""),
                            object.getString("topicId"));
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

        final IOnlineWord IOnlineWord = new OnlineWordImpl();

        IOnlineWord.getToWord("quick/paper/getPaperList", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray jsonArray = JSONObject.parseArray(response.body().string());
                    for (int i = 0; i < jsonArray.size(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String res = object.getString("paperDate");
                        data.add(res);
                        LogUtils.d(res);
                    }
                    app.setPagerList(data);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        return data;
    }

    @Override
    public void postOnlineWordsLog() {
        IWordsLogDao logDao = new WordsLogImpl();
        List<Words_Log> logs = logDao.select();
        StringBuilder json = new StringBuilder("[");
        for (Words_Log log:logs){
            //改变记录的操作左右的问题
            //本地数据库的左边代表云服的右边，本地数据库的右边代表云服的左边
            String body = "{\"wordId\":\""+log.getWordId()
                    +"\",\"word\":\""+log.getWord()
                    +"\",\"topicId\":\""+log.getTopicId()
                    +"\",\"leftNum\":\""+log.getRightNum()
                    +"\",\"rightNum\":\""+log.getLeftNum()
                    +"\"},";
            json.append(body);
        }
        //切割掉最后一个逗号
        json = new StringBuilder(json.substring(0, json.length() - 1));
        json.append("]");
        LogUtils.d(json.toString());

        final HashMap<String, String> map = new HashMap<>();
        map.put("userId", app.getUserId());
        map.put("logs", json.toString());

        final IOnlineWord iOnlineWord = new OnlineWordImpl();
        iOnlineWord.postToWord(map, "quick/reviewOrLearn/pushUserAction", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                LogUtils.d("logs发送失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String str = response.body().string();

                if(str.equals("1")){
                    LogUtils.d("logs发送成功");
                    try {
                        iWordsView.sendLogResult();
                    }catch (Exception e){
                        e.printStackTrace();
                        LogUtils.d("logs发送失败");
                    }
                }else {
                    LogUtils.d("logs发送失败");
                }

            }
        });

    }

    @Override
    public void GetAllWordTopicInfo() {
        IOnlineWord iOnline = new OnlineWordImpl();
//        iOnline.getToWord("quick/selectAllWordTopicInfo", new Callback() {
        iOnline.getToWord("quick/selectAllWordTopicInfo", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("无法接收单词表信息");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONArray lexiconArrary = JSON.parseArray(response.body().string());
                LogUtils.d("GetAllWordTopicInfo()=selectAllWord",lexiconArrary.toString());
                /**
                 * 一开始就获得所有的词库信息
                 * 用一个hashmap 存起来所有的词库表
                 * 然后存到数据库中
                 */

                Map<String,Lexicon> lexiconListByName = new HashMap();
                Map<String,Lexicon> lexiconListById = new HashMap();
                List<String> lexiconNameList = new ArrayList<>();

                for(int i=0;i<lexiconArrary.size();i++){
                    System.out.println("---------------");
                    JSONObject lexicon= lexiconArrary.getJSONObject(i);
                    lexiconListByName.put(lexicon.getString("topicName"),new Lexicon(lexicon.getString("topicId"),lexicon.getString("topicName"),lexicon.getString("tableName"),lexicon.getString("wordAllCount")));
                    lexiconListById.put(lexicon.getString("topicId"),new Lexicon(lexicon.getString("topicId"),lexicon.getString("topicName"),lexicon.getString("tableName"),lexicon.getString("wordAllCount")));
                    lexiconNameList.add(lexicon.getString("topicName"));
                    LogUtils.d("GetAllWordTopicInfo()=是不是空的词库:"+lexicon.getString("topicName"));
                }
                //存词库信息map<String,Lexicon> 通过名字找
                SPUtils.setMap(App.getContext(), PreferenceConstants.LEXICON, lexiconListByName);
                //存词库信息map<String,Lexicon> 通过id找
                SPUtils.setMap(App.getContext(), PreferenceConstants.LEXICON_BYID, lexiconListById);
                //存词库名list
                SPUtils.setDataList(App.getContext(), PreferenceConstants.LEXICONNAME, lexiconNameList);
            }
        });
    }

    @Override
    public void postToAddWordPlan(HashMap<String, String> map) {
        IOnlineWord iOnline = new OnlineWordImpl();
        iOnline.postToWord(map, "quick/addWordPlan", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                iHomeView.addPlanResult(false);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body().string().equals("0")){
                    iHomeView.addPlanResult(false);
                }else {
                    iHomeView.addPlanResult(true);
                }
            }
        });
    }

    @Override
    public void postToGetTopicIdWords(HashMap<String, String> map, final Boolean login) {
        IOnlineWord iOnline = new OnlineWordImpl();
        iOnline.postToWord(map, "quick/getUserAllWordInfo", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String userAllWordInfo = response.body().string();
                    SPUtils.put(App.getContext(),PreferenceConstants.USER_ALL_WORD_INFO, userAllWordInfo);
                    Log.d(TAG, "UserAllWordInfo: "+userAllWordInfo);
                    if(login)
                        //如果已经登录就主页操作
                        iHomeView.overWordInfo();
                    else
                        //如果没有登录就登录页面的操作
                        iLoginView.overWordInfo();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void initNewWord(JSONArray jsonArray) throws JSONException {
        for(int i = 0; i < jsonArray.size(); i++){
            WordsShowContract.IWordsShowPresenter wordsShowPresenter = new WordsShowPresenters();
            JSONObject object = jsonArray.getJSONObject(i);
            Words_Status words = WordsStatusImpl.getWordsByStatusFastJson("", object);
            wordsShowPresenter.addNewWord(words);
        }
    }

    public void initReviewWord(JSONArray jsonArray) throws JSONException {
        WordsShowContract.IWordsShowPresenter wordsShowPresenter = new WordsShowPresenters();
        for(int i = 0; i < jsonArray.size(); i++){
            JSONObject object = jsonArray.getJSONObject(i);
            Words_Status words = WordsStatusImpl.getWordsByStatusFastJson("review", object);
            wordsShowPresenter.addNewWord(words);
        }

    }


    private Words objectToWord(JSONObject object){
        //需要特别留意
        Words word = new Words(
                object.getString("id"),
                object.getString("pron"),
                object.getString("word"),
                object.getString("phon"),
                object.getString("para"),
                object.getString("build"),
                object.getString("example"),
                object.getString(""),
                object.getString(""),
                object.getString("topicId"));
        return word;
    }
}
