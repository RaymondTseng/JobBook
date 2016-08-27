package com.example.jobbook.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Xu on 2016/7/6.
 */
public class PersonBean implements Serializable{
    private static final long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public List<VideoCV> getVideoCVs() {
        return videoCVs;
    }

    public void setVideoCVs(List<VideoCV> videoCVs) {
        this.videoCVs = videoCVs;
    }

    public List<TextCV> getTextCVs() {
        return textCVs;
    }

    public void setTextCVs(List<TextCV> textCVs) {
        this.textCVs = textCVs;
    }

    public List<JobBean> getFavourite() {
        return favourite;
    }

    public void setFavourite(List<JobBean> favourite) {
        this.favourite = favourite;
    }

    public String getAccount() {

        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 用户账号
     */

    private String account;
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
     * 视频简历
     */
    private List<VideoCV> videoCVs;
    /**
     * 文字简历
     */
    private List<TextCV> textCVs;
    /**
     * 收藏
     */
    private List<JobBean> favourite;

}
