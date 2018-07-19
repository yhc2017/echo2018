package com.echo.quick.utils;

import android.app.Application;

import com.echo.quick.model.dao.impl.OnlineWordImpl;
import com.echo.quick.model.dao.interfaces.OnlineWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 14:20
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 14:20
 * 修改备注：
 */

public class App extends Application{

    public List<Words> list;

    public void onCreate() {
        super.onCreate();
        list = new ArrayList<>();
        init();
    }

    public void init(){
        try {
            OnlineWord word = new OnlineWordImpl();
            final HashMap<String, String> map = new HashMap<>();
            map.put("userId", "444");
            map.put("classId", "11");
            setList(word.postToWord(map));
        }catch (Exception e){
            
        }
    }


    public List<Words> getList() {
        return list;
    }

    public void setList(List<Words> list) {
        this.list = list;
    }
}
