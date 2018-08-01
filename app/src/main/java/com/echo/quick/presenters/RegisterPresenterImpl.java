package com.echo.quick.presenters;

import com.alibaba.fastjson.JSONObject;
import com.echo.quick.contracts.RegisterContract;
import com.echo.quick.model.dao.impl.RegisterImpl;
import com.echo.quick.model.dao.interfaces.IRegisterDao;
import com.echo.quick.pojo.User;
import com.echo.quick.utils.App;
import com.echo.quick.utils.SPUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 项目名称：echo2018
 * 类描述：
 * 创建人：zhou-jx
 * 创建时间：2018/7/19 20:25
 * 修改人：zhou-jx
 * 修改时间：2018/7/19 20:25
 * 修改备注：
 */

public class RegisterPresenterImpl extends BasePresenter implements RegisterContract.IRegister {

    private RegisterContract.IRegisterView iRegisterView;

    private User user;

    public RegisterPresenterImpl(){}

    public RegisterPresenterImpl(RegisterContract.IRegisterView iRegisterView){
        this.iRegisterView = iRegisterView;
    }

    @Override
    public void clear() {
        iRegisterView.onClearText();
    }

    @Override
    public void doRegister(String tel, String pwd, String nickname, String sex) {
        IRegisterDao registerDao = new RegisterImpl();
        registerDao.doRegisterPost(tel, pwd, nickname, sex, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iRegisterView.onRegisterResult(false, 203);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                int code  = response.code();
                String res = response.body().string();

                JSONObject jsonObject = JSONObject.parseObject(res);

                if(jsonObject.getString("prepare4").equals("204")){
                    SPUtils.put(App.getContext(), "UserInfo", res);
                    iRegisterView.onRegisterResult(true, code);
                }

            }
        });
    }
}
