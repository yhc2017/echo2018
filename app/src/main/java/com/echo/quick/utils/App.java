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
    private String topicId;
    private String study;

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
            setTopicId("12");
            Object topicId = SPUtils.get(getContext(), "topicID", "12");
            setTopicId(topicId.toString());
            OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
            onlineWordPresenter.getOnlineSprintType();
            onlineWordPresenter.GetAllWordTopicInfo();
            setUserId(SPUtils.get(getContext(), "userId", "111").toString());
            setNickName(SPUtils.get(getContext(), "nickname", "请登录").toString());
            setSex(SPUtils.get(getContext(), "sex", "男").toString());
            IWordsStatusDao statusDao = new WordsStatusImpl();
            List<Words_Status> statuses = statusDao.selectByStatusAndTopicId("learn_", getTopicId());
            if (statuses.size() < 5) {
                final HashMap<String, String> map = new HashMap<>();
                map.put("userId", getUserId());
                map.put("topicId", getTopicId());
                onlineWordPresenter.getOnlineWordReviewOrLearn(map, "review");
            }
        }catch (Exception e){
            e.printStackTrace();
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

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

}
