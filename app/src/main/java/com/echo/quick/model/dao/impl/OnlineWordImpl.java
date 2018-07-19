package com.echo.quick.model.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.model.dao.interfaces.OnlineWord;
import com.echo.quick.utils.L;
import com.echo.quick.utils.OKhttpManager;
import com.echo.quick.utils.Words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    //返回结果
    List<Words> data;
    String URLONLINEWORD = "http://192.168.43.167:8080/words/selectWords";

    @Override
    public List<Words> postToWord(HashMap<String, String> map) {
        OKhttpManager manager = OKhttpManager.getInstance();
        data = new ArrayList<>();
        manager.sendComplexForm(URLONLINEWORD, map, new OKhttpManager.Func1() {
            @Override
            public void onResponse(String result) {
//                L.d(result);
                JSONArray jsonArray = JSONObject.parseArray(result);
//                L.d(jsonArray.toString());
                for (int i = 0; i < jsonArray.size(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Words words = new Words(object.getString("word"), object.getString("phon"), object.getString("para"));
                    L.d(object.getString("word")+"       "+object.getString("para"));
                    data.add(words);
                }
            }
        });
        return data;
    }
}
