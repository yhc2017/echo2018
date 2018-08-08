package com.echo.quick.utils;

import android.app.Application;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.Words;
import com.echo.quick.pojo.Words_Status;
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

    public List<Words_Status> statusList;

    public List<String> pagerList;//真题类型列表

    private static Context mContext;

    private String content;

    private String translation;

    private String userId;
    private String nickName;
    private String sex;

    public static final String CTL_ACTION = "com.zjx.action.CTL_ACTION";
    public static final String UPDATE_ACTION = "com.zjx.action.UPDATE_ACTION";

    public void onCreate() {
        super.onCreate();
        list = new ArrayList<>();
        statusList = new ArrayList<>();
        // 初始化LitePal数据库
        mContext = getApplicationContext();
        LitePal.initialize(this);
        init();
    }

    public void init(){
        try {
            setUserId("111");
            Object o2 = "未登录";
            Object o = SPUtils.get(App.getContext(), "UserInfo", o2);
            if(o != o2) {
                JSONObject object = JSON.parseObject(o.toString());
                setUserId(object.getString("userId"));
                setNickName(object.getString("nickname"));
                setSex(object.getString("sex"));
            }
            IWordsStatusDao statusDao = new WordsStatusImpl();
            List<Words_Status> statuses = statusDao.selectByStatus("");
//            setStatusList(statuses);
            OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
            if(statuses.size() == 5){
                final HashMap<String, String> map = new HashMap<>();
                map.put("userId", "111");
                map.put("topicId", "17");
                onlineWordPresenter.getOnlineWordReviewOrLearn(map, "review");
            }
            onlineWordPresenter.getOnlineSprintType();
            onlineWordPresenter.GetAllWordTopicInfo();
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

    public List<Words_Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Words_Status> statusList) {
        this.statusList = statusList;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
