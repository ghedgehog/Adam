package com.sunway.model;

/***
 * 系统用户
 */
public class SysUsers {
    private String name;
    private String pwd;
    private String email;
    private int  permiz;

    public SysUsers(String name, String pwd, String email, int permiz) {
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
