package com.example.jobbook.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Xu on 2016/7/6.
 */
public class PersonBean implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户头像
     */
    private String head;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 用户姓名
     */
    private String userName;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String name) {
        this.userName = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }




}
