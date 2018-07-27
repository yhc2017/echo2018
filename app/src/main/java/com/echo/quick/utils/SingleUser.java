package com.echo.quick.utils;

import com.echo.quick.pojo.User;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/27 19:21
 * 修改人：zhou-jx
 * 修改时间：2018/7/27 19:21
 * 修改备注：
 */

public class SingleUser {

    private static User singleUser = null;

    private SingleUser(){}

    public static User getSingleUser(){
        if(singleUser == null){

        }
        return singleUser;
    }

}
