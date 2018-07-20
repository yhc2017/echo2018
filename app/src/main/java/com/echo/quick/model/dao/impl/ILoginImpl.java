package com.echo.quick.model.dao.impl;

import com.echo.quick.model.dao.interfaces.ILoginDao;
import com.echo.quick.pojo.User;
import com.echo.quick.utils._NetHelper;

import okhttp3.Callback;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 20:27
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 20:27
 * 修改备注：
 */

public class ILoginImpl implements ILoginDao {

    //调用_NetHelper中的post与get方法，实现CRUD操作
    private _NetHelper.PostHelper postHelper = new _NetHelper.PostHelper();

    @Override
    public User doLoginPost(String tel, String pwd, Callback callback) {

        String requestContent = "tel=155211864297";

        postHelper.doPost(requestContent, _NetHelper.DOMAIN+"demo", callback);

        return null;
    }
}
