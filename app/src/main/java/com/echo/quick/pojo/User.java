package com.echo.quick.pojo;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目名称：LastOne
 * 类描述：用户信息库
 * 创建人：zhou-jx
 * 创建时间：2018/7/7 9:49
 * 修改人：zhou-jx
 * 修改时间：2018/7/7 9:49
 * 修改备注：
 */
@Data
public class User{

    private String userId;
    private String nickname;
    private String pwd;
    private String sex;
    private String icon;
    private String introduce;
    private String tel;
    private String code;

    public User(){}

    public User(String userId, String nickname, String pwd, String sex, String icon, String introduce, String tel, String code) {
        this.userId = userId;
        this.nickname = nickname;
        this.pwd = pwd;
        this.sex = sex;
        this.icon = icon;
        this.introduce = introduce;
        this.tel = tel;
        this.code = code;
    }

}
