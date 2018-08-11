package com.echo.quick.presenters;

import android.support.annotation.NonNull;

import com.echo.quick.contracts.UserMessageContract;
import com.echo.quick.model.dao.impl.OnlineWordImpl;
import com.echo.quick.model.dao.interfaces.IOnlineWord;
import com.echo.quick.utils.App;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Hello周少侠 on 2018/8/9.
 */

public class UserMessagePresenterImpl extends BasePresenter implements UserMessageContract.IUserMessagePresenter {

    private UserMessageContract.IUserMessageView iUserMessageView;
    private App app;

    {
        app = (App) App.getContext();
    }

    public UserMessagePresenterImpl(){

    }

    public UserMessagePresenterImpl(UserMessageContract.IUserMessageView iUserMessageView){
        this.iUserMessageView = iUserMessageView;
    }

    @Override
    public void postToUpdate(HashMap<String, String> map) {

        IOnlineWord online = new OnlineWordImpl();
        online.postToWord(map, "", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                iUserMessageView.updateInfoResult(false);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                JSONObject object = getJSONObject(response);
                if(object != null){
                    try {
                        app.setNickName(object.getString("nickname"));
                        app.setSex(object.getString("sex"));
                        iUserMessageView.updateInfoResult(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        iUserMessageView.updateInfoResult(false);
                    }
                }else{
                    iUserMessageView.updateInfoResult(false);
                }
            }
        });

    }

    @Override
    public void postToUpdatePwd(HashMap<String, String> map) {
        IOnlineWord online = new OnlineWordImpl();
        online.postToWord(map, "", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                iUserMessageView.updateInfoResult(false);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                JSONObject object = getJSONObject(response);
                if(object != null){
                    iUserMessageView.updateInfoResult(true);
                }else{
                    iUserMessageView.updateInfoResult(false);
                }
            }
        });
    }

}
