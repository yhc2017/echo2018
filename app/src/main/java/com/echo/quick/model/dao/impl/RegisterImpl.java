package com.echo.quick.model.dao.impl;

import com.echo.quick.model.dao.interfaces.IRegisterDao;
import com.echo.quick.utils._NetHelper;

import okhttp3.Callback;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 20:28
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 20:28
 * 修改备注：
 */

public class RegisterImpl implements IRegisterDao {

    //调用_NetHelper中的post与get方法，实现CRUD操作
    private _NetHelper.PostHelper postHelper = new _NetHelper.PostHelper();

    @Override
    public void doRegisterPost(String tel, String pwd, String nickname, String sex, Callback callback) {

//         注册数据-json       ]
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("tel", tel);
//        jsonObject.put("pwd", pwd);
//        jsonObject.put("nickname", nickname);
//        jsonObject.put("sex", sex);
//        String json = jsonObject.toJSONString();

        String requestContent = "tel="+ tel +
                "&pwd="+ pwd +
                "&nickname="+ nickname +
                "&sex="+sex;

        postHelper.doPost(requestContent, _NetHelper.DOMAIN+"quick/userRegister", callback);

    }

}
