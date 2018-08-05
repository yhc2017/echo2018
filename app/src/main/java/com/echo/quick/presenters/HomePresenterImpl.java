package com.echo.quick.presenters;

import android.content.Context;

import com.echo.quick.contracts.HomeContract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class name: echo2018
 * Specific description :<功能的详细描述>
 * 创建人: HUAHUA
 *
 * @Time : 2018/8/5
 * 修改人：
 * @Time :
 * @since ：[产品|模块版本]
 */


public class HomePresenterImpl implements HomeContract.IHomePresenter {

    HomeContract.IHomeView iHomeView;

    public HomePresenterImpl(){}

    public HomePresenterImpl(HomeContract.IHomeView iHomeView){
        this.iHomeView = iHomeView;
    }

    @Override
    public int getYear() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return Integer.valueOf(df.format(new Date()));
    }

    @Override
    public int getMouth() {
        SimpleDateFormat df = new SimpleDateFormat("MM");
        return Integer.valueOf(df.format(new Date()));
    }

    @Override
    public void updatePlan() {
        iHomeView.updatePlan();
    }
}
