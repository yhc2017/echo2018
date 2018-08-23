package com.echo.quick.presenters;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;


/**
 * 项目名称：echo2018
 * 类描述：基础的Presenter，可以将相同的代码抽象到这里来，子类去修改和调用；
 * 创建人：TanzJ
 * 创建时间：2018/7/21 0:13
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class BasePresenter {

    //解析来自服务器返回的response字符串成为JsonObject
    JSONObject getJSONObject(Response response){
        try {
            return new JSONObject(response.body().string());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
