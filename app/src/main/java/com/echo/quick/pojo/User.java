package com.echo.quick.pojo;


/**
 * 项目名称：LastOne
 * 类描述：用户信息库
 * 创建人：zhou-jx
 * 创建时间：2018/7/7 9:49
 * 修改人：zhou-jx
 * 修改时间：2018/7/7 9:49
 * 修改备注：
 */

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
