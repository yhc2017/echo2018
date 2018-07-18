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

    private int userId;
    private String nickname;
    private String pwd;
    private String sex;
    private String icon;
    private String introduce;
    private String tel;
    private int code;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
