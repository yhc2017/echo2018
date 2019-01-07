package com.echo.quick.contracts;

import org.json.JSONException;

import java.text.ParseException;

/**
 * 项目名称：echo2018
 * 类描述：把P-V以合约的方式绑定，方便操作和管理
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 20:07
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 20:07
 * 修改备注：
 */

public interface LoginContract {

    interface ILoginView{

        void onClearText();

        /**
         *  将userId,topicId,计划情况等信息存到sharePreference中
         *
         * @param result 登录结果
         * @param code 代码
         */
        void onLoginResult(Boolean result, String code);

        void overWordInfo();

    }

    interface ILoginPresenter{
        void clear();
        //登录
        void doLogin(String name, String passwd);
        //手机短信验证登录
        void doLoginForTel(String tel);
        /**
         *
         * 修复和检测
         */
        void detectionAndRestoration(String userId);
        //恢复数据
        void allWordInfo(Boolean login) throws JSONException;


        int calMyPlanNmu(String date,int wordcount) throws ParseException;


        /**
         * 描述：计算结束时间
         * @return int
         * */
        int calculateEndNum(String date) throws ParseException;

        /**
         * 描述：用词库id查词库名字
         * @return int
         * */
        String getTopicName(String topicId);
    }

}
