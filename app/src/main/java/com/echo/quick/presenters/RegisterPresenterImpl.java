package com.echo.quick.presenters;

import com.echo.quick.contracts.RegisterContract;
import com.echo.quick.model.dao.impl.IRegisterImpl;
import com.echo.quick.model.dao.interfaces.IRegisterDao;
import com.echo.quick.pojo.User;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
    public User doRegister(String tel, String pwd, String nickname, String sex) {
        IRegisterDao registerDao = new IRegisterImpl();
        registerDao.doRegisterPost(tel, pwd, nickname, sex, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iRegisterView.onRegisterResult(false, 203);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject res = getJSONObject(response);


                try {
                    if(res.getString("prepare4").equals("204")) {
//                        iRegisterView.onRegisterResult(true, 204);
                        ToastUtils.showShort(App.getContext(), "用户注册成功");
                    }else if(res.getString("prepare4").equals("205")){
                        ToastUtils.showShort(App.getContext(), "用户已注册");
                    }
                    String code = res.getString("prepare4");
                    if(code.equals("200")) {
                        LogUtils.d("res.getString(\"userId\")" + res.getString("userId"));
                    }
                    user = new User(res.getString("userId"),
                            res.getString("nickname"),
                            res.getString("pwd"),
                            res.getString("sex"),
                            res.getString("icon"),
                            res.getString("introduce"),
                            res.getString("tel"),
                            res.getString("prepare4"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return user;
    }
}
