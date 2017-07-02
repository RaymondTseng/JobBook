package com.example.jobbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/7/15.
 * 公司详情模型类
 */
public class CompanyBean implements Parcelable {

//    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    private String com_id;
    /**
     * 公司名称
     */
    private String name;

//    /**
//     * 公司图标
//     */
//    private String logo;

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

    /**
     * 公司网址
     */
    private String website;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getLogo() {
//        return logo;
//    }
//
//    public void setLogo(String logo) {
//        this.logo = logo;
//    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.com_id);
        dest.writeString(this.name);
        dest.writeString(this.location);
        dest.writeString(this.scale);
        dest.writeString(this.welfare);
        dest.writeString(this.introduction);
        dest.writeList(this.comments);
        dest.writeString(this.website);
    }

    public CompanyBean() {
    }

    protected CompanyBean(Parcel in) {
        this.com_id = in.readString();
        this.name = in.readString();
        this.location = in.readString();
        this.scale = in.readString();
        this.welfare = in.readString();
        this.introduction = in.readString();
        this.comments = new ArrayList<CompanyCommentBean>();
        in.readList(this.comments, CompanyCommentBean.class.getClassLoader());
        this.website = in.readString();
    }

    public static final Creator<CompanyBean> CREATOR = new Creator<CompanyBean>() {
        @Override
        public CompanyBean createFromParcel(Parcel source) {
            return new CompanyBean(source);
        }

        @Override
        public CompanyBean[] newArray(int size) {
            return new CompanyBean[size];
        }
    };

    @Override
    public String toString() {
        return "[com_id:" + com_id + ", name:" + name + ", location:" + location + ", scale:" + scale
                + ", welfare:" + welfare + ", introduction:" + introduction + ", website:" + website;
    }
}
