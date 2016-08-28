package com.example.jobbook.bean;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Xu on 2016/7/15.
 * 公司详情模型类
 */
public class CompanyBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String com_id;
    /**
     * 公司名称
     */
    private String name;

    /**
     * 公司图标
     */
    private ImageView logo;

    /**
     * 公司地址
     */
    private String location;

    /**
     * 公司规模
     */
    private String scale;

    /**
     * 公司福利
     */
    private String welfare;

    /**
     * 公司介绍
     */
    private String introduction;

    /**
     * 公司评论
     */
    private List<CompanyCommentBean> comments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageView getLogo() {
        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }

    public String getWelfare() {
        return welfare;
    }

    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public List<CompanyCommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CompanyCommentBean> comments) {
        this.comments = comments;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getId() {
        return com_id;
    }

    public void setId(String id) {
        this.com_id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
