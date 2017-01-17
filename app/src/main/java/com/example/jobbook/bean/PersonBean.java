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

    /**
     * 关注者数量
     */
    private String follow;

    /**
     * 粉丝数量
     */
    private String fans;

    /**
     * 动态数量
     */
    private String moment;

    /**
     * 工作地点
     */

    private String workspace;

    /**
     * 工作岗位
     */
    private String workposition;



    public String getWorkSpace() {
        return workspace;
    }

    public void setWorkSpace(String workSpace) {
        this.workspace = workSpace;
    }

    public String getWorkPosition() {
        return workposition;
    }

    public void setWorkPosition(String workPosition) {
        this.workposition = workPosition;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getMoment() {
        return moment;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }

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

    public String toString(){
        return "account" + account + ",head" + head + ",password" + password + ",telephone" + telephone +
                ",userName" + userName;
    }


}
