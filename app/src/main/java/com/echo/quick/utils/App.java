package com.echo.quick.utils;

import android.app.Application;

import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.pojo.Words;
import com.echo.quick.presenters.OnlineWordPresenterImpl;

import org.litepal.LitePal;

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
        // 初始化LitePal数据库
        LitePal.initialize(this);
        init();
    }

    public void init(){
        try {
            OnlineWordContract.OnlineWordPresenter wordPresenter = new OnlineWordPresenterImpl();
            final HashMap<String, String> map = new HashMap<>();
            map.put("userId", "444");
            map.put("classId", "11");
            setList(wordPresenter.getOnlineWord(map));
        }catch (Exception e){
            LogUtils.d("没在服务器获取到数据");
        }
    }


    public List<Words> getList() {
        return list;
    }

    public void setList(List<Words> list) {
        this.list = list;
    }
}
