package com.echo.quick.contracts;

import java.util.HashMap;

/**
 * 文件名：UserMessageContract.java
 * 创建人：Hello周少侠
 * 创建时间：2018/8/9
 * 类描述：
 *
 * 修改人：
 * 修改时间：
 * 修改内容：
 *
**/


public interface UserMessageContract {

    interface IUserMessageView{
        void updateInfoResult(Boolean res);
    }

    interface IUserMessagePresenter{
        void postToUpdate(HashMap<String,String> map);
        void postToUpdatePwd(HashMap<String,String> map);
        void postUserForgetPwd(HashMap<String,String> map);
    }



}
