package com.echo.quick.contracts;

import java.text.ParseException;

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
        void updatePlan();

        void updateUserName();
    }

    interface IHomePresenter{

        int getYear();
        int getMouth();
        void updatePlan();
        int calMyPlanNmu(String date,int wordindex) throws ParseException;

    }

}
