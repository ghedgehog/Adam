package com.sunway.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/***
 * 系统用户
 */
public class User {

    @NotEmpty(message="用户名不能为空")
    private String name;

    @NotEmpty(message="密码不能为空")
    @Length(min=6, message="密码长度不能少于六位")
    private String pwd;

    private String email;

    private int  permiz;

    public User(String name, String pwd, String email, int permiz) {
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.permiz = permiz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPermiz() {
        return permiz;
    }

    public void setPermiz(int permiz) {
        this.permiz = permiz;
    }
}
