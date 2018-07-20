package com.echo.quick.presenters;

import com.echo.quick.contracts.LoginContract;
import com.echo.quick.model.dao.impl.ILoginImpl;
import com.echo.quick.model.dao.interfaces.ILoginDao;
import com.echo.quick.utils.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 20:24
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 20:24
 * 修改备注：
 */

public class LoginPresenterImpl implements LoginContract.ILoginPresenter {

    LoginContract.ILoginView iLoginView;

    public LoginPresenterImpl(LoginContract.ILoginView loginView){

        this.iLoginView = loginView;

    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(String name, String passwd) {
        ILoginDao loginDao = new ILoginImpl();
        loginDao.doLoginPost(name, passwd, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.d(response.body().string());
            }
        });
    }
}
