package com.example.jobbook.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by Xu on 2016/7/6.
 */
public class PersonBean implements Parcelable {
//    private static final long serialVersionUID = 1L;

    public PersonBean(PersonBean personBean) {
        this.account = personBean.getAccount();
        this.head = personBean.getHead();
        this.password = personBean.getPassword();
        this.telephone = personBean.getTelephone();
        this.username = personBean.getUsername();
        this.follow = personBean.getFollow();
        this.fans = personBean.getFans();
        this.moment = personBean.getMoment();
        this.workspace = personBean.getWorkSpace();
        this.workposition = personBean.getWorkPosition();
    }

    public PersonBean(){

    }

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

    public String toString(){
        return "account" + account + ",head" + head + ",password" + password + ",telephone" + telephone +
                ",userName" + username + ",follow" + follow + ",fans" + fans;
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
        dest.writeString(this.telephone);
        dest.writeString(this.username);
        dest.writeString(this.follow);
        dest.writeString(this.fans);
        dest.writeString(this.moment);
        dest.writeString(this.workspace);
        dest.writeString(this.workposition);
    }

    protected PersonBean(Parcel in) {
        this.account = in.readString();
        this.head = in.readString();
        this.password = in.readString();
        this.telephone = in.readString();
        this.username = in.readString();
        this.follow = in.readString();
        this.fans = in.readString();
        this.moment = in.readString();
        this.workspace = in.readString();
        this.workposition = in.readString();
    }

    public static final Creator<PersonBean> CREATOR = new Creator<PersonBean>() {
        @Override
        public PersonBean createFromParcel(Parcel source) {
            return new PersonBean(source);
        }

        @Override
        public PersonBean[] newArray(int size) {
            return new PersonBean[size];
        }
    };
}
