package com.example.jobbook.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class MomentCommentBean implements Parcelable {
//    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    private int comment_id;
    /**
     * 问问id
     */
    private int s_id;
    /**
     * 文章评论者
     */
    private PersonBean applier;

    /**
     * 评论点赞数
     */
    private int good;
    /**
     * 评论踩数
     */
    private int bad;

    /**
     * 评论日期
     */
    private String ask_time;
    /**
     * 评论内容
     */
    private String content;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int q_id) {
        this.s_id = q_id;
    }

    public PersonBean getApplier() {
        return applier;
    }

    public void setApplier(PersonBean applier) {
        this.applier = applier;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public String getAsk_time() {
        return ask_time;
    }

    public void setAsk_time(String ask_time) {
        this.ask_time = ask_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString(){
        return "applier" + applier.toString() + ",good" + good + ",bad" + bad + ",ask_time" + ask_time +
                ",content" + content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.comment_id);
        dest.writeInt(this.s_id);
        dest.writeParcelable(this.applier, flags);
        dest.writeInt(this.good);
        dest.writeInt(this.bad);
        dest.writeString(this.ask_time);
        dest.writeString(this.content);
    }

    public MomentCommentBean() {
    }

    protected MomentCommentBean(Parcel in) {
        this.comment_id = in.readInt();
        this.s_id = in.readInt();
        this.applier = (PersonBean) in.readSerializable();
        this.good = in.readInt();
        this.bad = in.readInt();
        this.ask_time = in.readString();
        this.content = in.readString();
    }

    public static final Creator<MomentCommentBean> CREATOR = new Creator<MomentCommentBean>() {
        @Override
        public MomentCommentBean createFromParcel(Parcel source) {
            return new MomentCommentBean(source);
        }

        @Override
        public MomentCommentBean[] newArray(int size) {
            return new MomentCommentBean[size];
        }
    };
}
