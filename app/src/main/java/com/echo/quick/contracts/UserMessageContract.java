package com.echo.quick.contracts;

import java.util.HashMap;

/**
 * Created by Hello周少侠 on 2018/8/9.
 */

public interface UserMessageContract {

    interface IUserMessageView{
        void updateInfoResult(Boolean res);
    }

    interface IUserMessagePresenter{
        void postToUpdate(HashMap<String,String> map);
        void postToUpdatePwd(HashMap<String,String> map);
    }



}
