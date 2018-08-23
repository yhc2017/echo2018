package com.echo.quick.contracts;

import android.app.ProgressDialog;

import org.json.JSONException;

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
        void onLoginResult(Boolean result, String code);
        void overWordInfo();

    }

    interface ILoginPresenter{
        void clear();
        //登录
        void doLogin(String name, String passwd);
        //
        void detectionAndRestoration(String userId);
        //恢复数据
        void allWordInfo(Boolean login) throws JSONException;
    }

}
