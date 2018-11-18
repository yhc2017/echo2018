package com.echo.quick.pojo;


/**
 * 项目名称：echo2018
 * 类描述：用户上下文单例，简单版本，可以优化
 * 创建人：TanzJ
 * 创建时间：2018/7/21 0:13
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class UserContext {

    private User user;

    static private UserContext userContext  = new UserContext();

    static UserContext getInstance(){
        return userContext;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

}
