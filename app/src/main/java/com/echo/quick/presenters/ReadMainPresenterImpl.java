package com.echo.quick.presenters;

import com.echo.quick.contracts.ReadMainContract;
import com.echo.quick.utils.App;
import com.echo.quick.utils.SPUtils;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/29 11:00
 * 修改人：zhou-jx
 * 修改时间：2018/7/29 11:00
 * 修改备注：
 */

public class ReadMainPresenterImpl implements ReadMainContract.ReadMainPresenter{


    @Override
    public String isExist(String key) {
        try{
            String res = (String) SPUtils.get(App.getContext(), key, "不存在");
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return "无数据";
        }
    }
}
