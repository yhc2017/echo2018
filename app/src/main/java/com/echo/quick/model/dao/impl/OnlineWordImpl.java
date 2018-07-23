package com.echo.quick.model.dao.impl;

import com.echo.quick.model.dao.interfaces.OnlineWord;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils._NetHelper;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 11:16
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 11:16
 * 修改备注：
 */

public class OnlineWordImpl implements OnlineWord {

    //调用_NetHelper中的post与get方法，实现CRUD操作
    private _NetHelper.PostHelper postHelper = new _NetHelper.PostHelper();


    @Override
    public void postToWord(HashMap<String, String> params, Callback callback) {

        String requestContent = "";

        if (params != null && !params.isEmpty()) {
            for(Map.Entry<String,String> entry : params.entrySet()){
                requestContent += entry.getKey() + "=" + entry.getValue() +"&";
            }
        }
        LogUtils.d("requestContent = " + requestContent );

        postHelper.doPost(requestContent, _NetHelper.POST_URL+"quick/words/selectWords", callback);
    }
}
