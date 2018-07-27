package com.echo.quick.contracts;

import com.echo.quick.pojo.Words;

import java.util.HashMap;
import java.util.List;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/23 15:21
 * 修改人：zhou-jx
 * 修改时间：2018/7/23 15:21
 * 修改备注：
 */

public interface OnlineWordContract {

    interface OnlineWordPresenter{

        List<Words> getOnlineWord(HashMap<String, String> map);

        List<Words> getOnlineSprint(HashMap<String, String> map);

    }

}
