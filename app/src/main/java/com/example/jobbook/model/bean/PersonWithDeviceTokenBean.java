package com.example.jobbook.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Xu on 2016/7/6.
 */
public class PersonWithDeviceTokenBean implements Parcelable {
//    private static final long serialVersionUID = 1L;

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
     * 用户姓名
     */
    private String username;

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

    /**
     * devicetoken，用于友盟推送
     */
    private String devicetoken;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString(){
        return "account" + account + ",head" + head + ",password" + password +
                ",userName" + username + ",follow" + follow + ",fans" + fans + ",devicetoken" + devicetoken;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.account);
        dest.writeString(this.head);
        dest.writeString(this.password);
        dest.writeString(this.username);
        dest.writeString(this.follow);
        dest.writeString(this.fans);
        dest.writeString(this.moment);
        dest.writeString(this.workspace);
        dest.writeString(this.workposition);
        dest.writeString(this.devicetoken);
    }

    public PersonWithDeviceTokenBean() {
    }

    protected PersonWithDeviceTokenBean(Parcel in) {
        this.account = in.readString();
        this.head = in.readString();
        this.password = in.readString();
        this.username = in.readString();
        this.follow = in.readString();
        this.fans = in.readString();
        this.moment = in.readString();
        this.workspace = in.readString();
        this.workposition = in.readString();
        this.devicetoken = in.readString();
    }

    public static final Creator<PersonWithDeviceTokenBean> CREATOR = new Creator<PersonWithDeviceTokenBean>() {
        @Override
        public PersonWithDeviceTokenBean createFromParcel(Parcel source) {
            return new PersonWithDeviceTokenBean(source);
        }

        @Override
        public PersonWithDeviceTokenBean[] newArray(int size) {
            return new PersonWithDeviceTokenBean[size];
        }
    };
}
