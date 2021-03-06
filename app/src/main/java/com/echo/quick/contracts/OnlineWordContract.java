package com.echo.quick.contracts;

import com.echo.quick.pojo.Words;

import java.util.HashMap;
import java.util.List;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/23 15:21
 * 修改人：zhou-jx
 * 修改时间：2018/7/23 15:21
 * 修改备注：
 */

public interface OnlineWordContract {

    interface OnlineWordPresenter{

        /**
         * 方法名称：getOnlineWord
         * 方法描述: 以前的测试方法
         * 参数1： 参数说明
         * @return [返回类型说明]
         **/
        List<Words> getOnlineWord(HashMap<String, String> map);

        /**
         * 方法名称：getOnlineWord
         * 方法描述: 以前的测试方法
         * 参数1： HashMap<String, String> map，一个必须的请求信息
         * 参数2：String rele， 一个复习优先或者学习优先的标识字段（使用review or learn）
         **/
        void getOnlineWordReviewOrLearn(HashMap<String, String> map, String rele);

        /**
         * 动态获取单词信息
         *
         * @param map 请求参数
         * */
        void getDynamicWordInfo(HashMap<String, String> map);

        /**
         * 方法名称：getOnlineSprint
         * 方法描述: 获取真题冲刺的单词和真题文章、翻译
         * 参数1： 参数说明
         * @return [返回类型说明]
         **/
        List<Words> getOnlineSprint(HashMap<String, String> map);



        /**
         * 茹韶燕，新增加获取真题分类列表
         * */
        List<String> getOnlineSprintType();

        //词库id写死为17
        void postOnlineWordsLog();

        /**
         * 方法名称：GetAllWordTopicInfo
         * 方法描述: 通过Get请求获取所有词库信息
         **/
        void GetAllWordTopicInfo();

        /**
         * <b>方法名称：</b>postToAddWordPlan<br/>
         * <b>方法描述:</b> 添加用户的学习计划，一个用户只能对一个词库设置一个学习计划
         * @param map 请求参数
         **/
        void postToAddWordPlan(HashMap<String, String> map);

        /**
         * <b>方法名称</b>：postToGetTopicIdWords<br/>
         * <b>方法描述:</b> 通过用户Id和topicId获取用户该词库的单词信息和计划情况
         * @param map 请求参数
         * @param isLogin 是否登录
         **/
        void postToGetTopicIdWords(HashMap<String, String> map, Boolean isLogin);

    }

}
