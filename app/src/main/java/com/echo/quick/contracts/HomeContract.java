package com.echo.quick.contracts;

import java.text.ParseException;
import java.util.HashMap;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/27 14:12
 * 修改人：zhou-jx
 * 修改时间：2018/7/27 14:12
 * 修改备注：
 */

public interface HomeContract {

    interface IHomeView{

        /**
         * 更新计划
         * */
        void updatePlan();
        void updateUserName();
        void addPlanResult(Boolean result);
        void overWordInfo();

        /**
         * <b>描述 :</b>界面数据绑定与数据传输到服务器方法<br/>
         *@return HashMap
         */
        HashMap<String, String> setData();
    }

    interface IHomePresenter{

        int getYear();
        int getMouth();
        void updatePlan();
        int calMyPlanNmu(String date,int wordindex) throws ParseException;


        /**
         * 描述：计算结束时间
         * @return int
         * */
        int calculateEndNum(String date) throws ParseException;

    }

}
