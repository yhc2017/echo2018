package com.echo.quick.utils;

import android.app.Application;
import android.content.Context;

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

    public List<String> pagerList;//真题类型列表

    private static Context mContext;

    private String content;

    private String translation;

    public void onCreate() {
        super.onCreate();
        list = new ArrayList<>();
        // 初始化LitePal数据库
        mContext = getApplicationContext();
        LitePal.initialize(this);
        init();
    }

    public void init(){
        try {
            OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
            final HashMap<String, String> map = new HashMap<>();
            map.put("userId", "111");
            map.put("paperDate", "2017年6月四级真题A卷");
            map.put("paperType", "A");
            setList(onlineWordPresenter.getOnlineSprint(map));
            onlineWordPresenter.getOnlineSprintType();
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

    /**获取Context.
     * @return
     */
    public static Context getContext(){
        return mContext;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public List<String> getPagerList() {
        return pagerList;
    }

    public void setPagerList(List<String> pagerList) {
        this.pagerList = pagerList;
    }
}
