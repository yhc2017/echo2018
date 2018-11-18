package com.echo.quick.model.dao.interfaces;

import okhttp3.Callback;


/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 20:26
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 20:26
 * 修改备注：
 */

public interface IRegisterDao {

    void doRegisterPost(String tel, String pwd, String introduce, String sex, Callback callback);

}
