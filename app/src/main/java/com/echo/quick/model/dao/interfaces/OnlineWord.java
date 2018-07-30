package com.echo.quick.model.dao.interfaces;

import java.util.HashMap;

import okhttp3.Callback;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 11:14
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 11:14
 * 修改备注：
 */

public interface OnlineWord {

    void postToWord(HashMap<String, String> map, String adress, Callback callback);

    void postToWordString(HashMap<String, String> map, String adress, Callback callback);

    void getToWord(String adress, Callback callback);

}
