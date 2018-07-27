package com.echo.quick.contracts;

import com.echo.quick.pojo.User;

/**
 * 项目名称：echo2018
 * 类描述：把P-V以合约的方式绑定，方便操作和管理
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 20:07
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 20:07
 * 修改备注：
 */

public interface RegisterContract {

    interface IRegisterView{
        void onClearText();
        void onRegisterResult(Boolean result, int code);
    }
    interface IRegister{
        void clear();
        User doRegister(String tel, String pwd, String nickname, String sex);
    }

}
