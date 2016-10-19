package com.xxapp.Entities;

/**
 * Created by Administrator on 2016/10/14.
 */
public class UserInfo {

    public UserInfo(String account, String pwd) {
        Account = account;
        Pwd = pwd;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    private String Account;
    private String Pwd;
}
