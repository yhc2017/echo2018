package com.echo.quick.presenters;

import com.echo.quick.contracts.LoginContract;
import com.echo.quick.model.dao.impl.ILoginImpl;
import com.echo.quick.model.dao.interfaces.ILoginDao;
import com.echo.quick.utils.LogUtils;

import org.json.JSONObject;

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

public class LoginPresenterImpl extends BasePresenter implements LoginContract.ILoginPresenter {

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
                //LogUtils.d(response.body().string());

                //code指的是http状态码，可以判断操作的状态；
                int code  = response.code();

                //将获得的response直接解析成为JsonObject;
                JSONObject jsonObject = getJSONObject(response);
                // TO-DO:将上面得到的JsonObject进行处理，并通过用户上下文创建一个全局用户单例；
            }
        });
    }
}
