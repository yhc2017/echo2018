package com.echo.quick.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.common.PreferenceConstants;
import com.echo.quick.common.PreferenceManager;
import com.echo.quick.contracts.HomeContract;
import com.echo.quick.contracts.LoginContract;
import com.echo.quick.contracts.WordsShowContract;
import com.echo.quick.model.dao.impl.LoginImpl;
import com.echo.quick.model.dao.impl.OnlineWordImpl;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.ILoginDao;
import com.echo.quick.model.dao.interfaces.IOnlineWord;
import com.echo.quick.pojo.Words_Status;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.SPUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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



                PreferenceManager.getInstance().put(PreferenceConstants.USERPHONE,name);
                PreferenceManager.getInstance().put(PreferenceConstants.USERPASSWORD,passwd);
                PreferenceManager.getInstance().put(PreferenceConstants.USERLOGIN,"true");

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
//                    JSONObject userInfo = all.getJSONObject("userInfo");
//                    JSONObject allPlan = all.getJSONObject("allPlan");
                        JSONObject lastPlan = all.getJSONObject("lastPlan");
                        String topicId = lastPlan.getString("topicId");
                        app.setTopicId(topicId);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void allWordInfo(Boolean login) throws JSONException {
        Object object = SPUtils.get(App.getContext(), "UserAllWordInfo", "");
        try {
            if (object != "") {
                LogUtils.d("object.toString............."+object.toString());
                org.json.JSONObject object1 = new org.json.JSONObject(object.toString());
                org.json.JSONArray array = object1.getJSONArray("wordInfo");
                initOldWord(array);
            }
            if(login)
                iLoginView.onLoginResult(true, "500");
            else
                iHomeView.setData();
        }catch (Exception e) {
            LogUtils.d("object.toString.............");
            e.printStackTrace();
        }
    }

    private void initOldWord(org.json.JSONArray jsonArray) throws JSONException {
        WordsShowContract.IWordsShowPresenter wordsShowPresenter = new WordsShowPresenters();
        for(int i = 0; i < jsonArray.length(); i++){
            org.json.JSONObject object = jsonArray.getJSONObject(i);
            String status = "learn";
            switch (object.getString("status")){
                case "207":
                    status = "learn";
                    break;
                case "208":
                    status = "review";
                    break;
                case "209":
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
