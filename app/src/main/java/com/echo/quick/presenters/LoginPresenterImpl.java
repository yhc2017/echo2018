package com.echo.quick.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.common.Constants;
import com.echo.quick.common.PreferenceConstants;
import com.echo.quick.common.PreferenceManager;
import com.echo.quick.contracts.HomeContract;
import com.echo.quick.contracts.LoginContract;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.contracts.WordsShowContract;
import com.echo.quick.model.dao.impl.LoginImpl;
import com.echo.quick.model.dao.impl.OnlineWordImpl;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.ILoginDao;
import com.echo.quick.model.dao.interfaces.IOnlineWord;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.Words_Status;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.SPUtils;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.echo.quick.activities.ReadActivity.TAG;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 20:24
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 20:24
 * 修改备注：
 */

public class LoginPresenterImpl extends BasePresenter implements LoginContract.ILoginPresenter {

    private LoginContract.ILoginView iLoginView;
    private HomeContract.IHomeView iHomeView;
    private App app;

    {
        app = (App) App.getContext();
    }

    public LoginPresenterImpl(HomeContract.IHomeView iHomeView){
        this.iHomeView = iHomeView;
    }

    public LoginPresenterImpl(LoginContract.ILoginView loginView){

        this.iLoginView = loginView;

    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    /**
     * Method name : doLogin
     * Specific description :登录
     */
    @Override
    public void doLogin(final String name, final String passwd) {

        ILoginDao loginDao = new LoginImpl();
        loginDao.doLoginPost(name, passwd,  new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                LogUtils.d("失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                //code指的是http状态码，可以判断操作的状态；
                int code  = response.code();
                String res = response.body().string();
                Log.e("logindata",res);
                JSONObject object = JSON.parseObject(res);
                String prepare4 = object.getString("prepare4");
                System.out.println("HomeMainAcitivity类======="+name);
                System.out.println("HomeMainAcitivity类======="+name);
                System.out.println("HomeMainAcitivity类======="+name);
                System.out.println("HomeMainAcitivity类======="+name);
                System.out.println("HomeMainAcitivity类======="+name);
                System.out.println("HomeMainAcitivity类======="+name);



//                PreferenceManager.getInstance().put(PreferenceConstants.USERPHONE,name);
//                PreferenceManager.getInstance().put(PreferenceConstants.USERPASSWORD,passwd);
                PreferenceManager.getInstance().put(PreferenceConstants.USERLOGIN,"true");

                switch (prepare4) {
                    case "200":
                        String userId = object.getString("userId");
                        String nickname = object.getString("nickname");
                        String sex = object.getString("sex");

                        SPUtils.put(App.getContext(), PreferenceConstants.USERPHONE, userId);
                        SPUtils.put(App.getContext(), PreferenceConstants.USERNAME, nickname);
                        SPUtils.put(App.getContext(), PreferenceConstants.USERSEX, sex);

                        app.setUserId(userId);
                        app.setNickName(nickname);
                        app.setSex(sex);

                        iLoginView.onLoginResult(true, prepare4);
                        break;
                    case "199":
                        iLoginView.onLoginResult(true, prepare4);
                        break;
                    case "202":
                        iLoginView.onLoginResult(true, prepare4);
                        break;
                    default:
                        iLoginView.onLoginResult(false, prepare4);
                        break;
                }
            }
        });

    }

    @Override
    public void doLoginForTel(String tel) {
        ILoginDao loginDao = new LoginImpl();
        loginDao.doLoginTel(tel, "quick/doLoginForTel", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                iLoginView.onLoginResult(false, "203");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = response.body().string();
                JSONObject object = JSON.parseObject(res);
                String prepare4 = object.getString("prepare4");
                switch (prepare4) {
                    case "200":
                        String userId = object.getString("userId");
                        String nickname = object.getString("nickname");
                        String sex = object.getString("sex");
                        SPUtils.put(App.getContext(), "userId", userId);
                        SPUtils.put(App.getContext(), "nickname", nickname);
                        SPUtils.put(App.getContext(), "sex", sex);
                        app.setUserId(userId);
                        app.setNickName(nickname);
                        app.setSex(sex);
                        iLoginView.onLoginResult(true, prepare4);
                        break;
                    case "199":
                        iLoginView.onLoginResult(true, prepare4);
                        break;
                    default:
                        iLoginView.onLoginResult(false, prepare4);
                        break;
                }
            }
        });
    }

    @Override
    public void detectionAndRestoration(final String userId) {
        IOnlineWord online = new OnlineWordImpl();
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        online.postToWord(map, "quick/getUserInfo", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject all = JSON.parseObject(response.body().string());
                    if (all != null) {
                        JSONObject lastPlan = all.getJSONObject("lastPlan");
                        String topicId = lastPlan.getString("topicId");
                        String topicName = getTopicName(topicId);
                        String planTime = lastPlan.getString("planEndTime").substring(0,7);
                        String planTypeNum = lastPlan.getString("prepare4");
                        Integer wordAllCount = Integer.valueOf(lastPlan.getString("wordAllCount"));
                        try {
                            //存入目标数
                            Integer dataNum = calMyPlanNmu(planTime, wordAllCount);
                            System.out.printf("登录计算=================每日的目标数："+dataNum);
                            //每日单词数量
                            SPUtils.put(App.getContext(), PreferenceConstants.DATEPLANNUM, dataNum);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        app.setTopicId(topicId);
                        //词库ID
                        SPUtils.put(App.getContext(), PreferenceConstants.LEXICON_ID, topicId);
                        //词库名
                        SPUtils.put(App.getContext(), PreferenceConstants.CURRENT_PLAN_LEXICON, topicName);
                        //计划时间
                        SPUtils.put(App.getContext(), PreferenceConstants.PLAN_TIME, planTime);

                        String planType = "复习优先";
                        if (Constants.LEARN_FIRST.equals(planTypeNum)){
                             planType = "学习优先";
                        }
                        //计划模式
                        SPUtils.put(App.getContext(), PreferenceConstants.PLAN_TYPE, planType);
                        //词库总词量
                        SPUtils.put(App.getContext(), PreferenceConstants.LEXICON_ALLCOUNT, wordAllCount);

                        LogUtils.d(TAG, "onResponse: "+"lastPlan:"+lastPlan+" topicPlan:"+topicId+" topicName:"+topicName+" planTime:"+planTime+" planType:"+planType+" wordAllCount:"+wordAllCount);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 用于通过id查找词库的名称
     * @param topicId
     * @return
     */
    @Override
    public String getTopicName(String topicId) {
        Map lexiconListById = new HashMap<>();
        lexiconListById = SPUtils.getMap(App.getContext(), PreferenceConstants.LEXICON_BYID);
        if (lexiconListById.isEmpty()){
            OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
            onlineWordPresenter.GetAllWordTopicInfo();
            lexiconListById = SPUtils.getMap(App.getContext(), PreferenceConstants.LEXICON_BYID);
        }
        Map lexiconMapById = (Map) lexiconListById.get(topicId);
        String topicName = (String) lexiconMapById.get("topicName");
        return topicName;
    }

    @Override
    public void allWordInfo(Boolean isLogin) throws JSONException {
        Object object = SPUtils.get(App.getContext(), PreferenceConstants.USER_ALL_WORD_INFO, "");
        try {
            if (object != "") {
                LogUtils.d("获取所有单词的信息"+object.toString()+" 有无登录:"+isLogin);
                org.json.JSONObject object1 = new org.json.JSONObject(object.toString());
                org.json.JSONArray array = object1.getJSONArray("wordInfo");
                initOldWord(array);
            }
            if(isLogin) {
                //如果已经登录
                iHomeView.setData();
            }else {
                //如果没有登录
                iLoginView.onLoginResult(true, "500");
            }
        }catch (Exception e) {
            LogUtils.e("该词库的所有单词的信息获取出异常");
            e.printStackTrace();
        }
    }


    /**
     * Method name : calMyPlanNmu
     * Specific description :用于计算每天的单词目标数
     * 创建人：茹韶燕
     *@param   date String
     *@param   wordcount int
     *@return datenum int
     */
    @Override
    public int calMyPlanNmu(String date, int wordcount) throws ParseException {
        //相差天数
        int datenum = 0;
        String s1=date+"-12";
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String s2 = df.format(System.currentTimeMillis());
        Date d1=df.parse(s1);
        Date d2=df.parse(s2);
        int hh = (int) ((d1.getTime()-d2.getTime())/(60*60*1000*24));

        //已背单词数量
        IWordsStatusDao statusDao = new WordsStatusImpl();
        int overWords = statusDao.selectCountByStatusAndTopicId("review_grasp", app.getTopicId());

        //四舍五入取整得到每日目标数
        datenum = Math.round((wordcount - overWords)/hh);
        System.out.printf("--------------每日的目标数："+"("+wordcount+"-"+overWords+")/"+hh+"="+datenum);

        return datenum;
    }

    @Override
    public int calculateEndNum(String date) throws ParseException {
        //相差天数
        int dateNum = 0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

        Date planDate = df.parse( date+"-12");
        Date currentDate = df.parse(df.format(System.currentTimeMillis()));

        int hh = (int) ((planDate.getTime()-currentDate.getTime())/(60*60*1000*24));

        return hh;
    }

    /**
     * 获取用户所有的单词后，存进数据库
     * @param jsonArray
     * @throws JSONException
     */
    private void initOldWord(org.json.JSONArray jsonArray) throws JSONException {
        WordsShowContract.IWordsShowPresenter wordsShowPresenter = new WordsShowPresenters();
        for(int i = 0; i < jsonArray.length(); i++){
            org.json.JSONObject object = jsonArray.getJSONObject(i);
            String status = "learn";
            switch (object.getString("status")){
                case Constants.WORD_LEARN:
                    status = "learn";
                    break;
                case Constants.WORD_REVIEW:
                    status = "review";
                    break;
                case Constants.WORD_GRASP:
                    status = "grasp";
                    break;
                default:
                    break;
            }
            Words_Status words = WordsStatusImpl.getWordsByStatus(status, object);
            wordsShowPresenter.addNewWord(words);
        }

    }

}
