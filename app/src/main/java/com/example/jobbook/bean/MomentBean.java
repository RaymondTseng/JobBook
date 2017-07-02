package com.example.jobbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Xu on 2016/7/5.
 * 问题模型类
 */

public class MomentBean implements Parcelable {
//    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private int q_id;
    /**
     * 工作圈作者
     */
    private TypePersonBean author;
    /**
     * 工作圈内容
     */
    private String content;
    /**
     * 提出日期
     */
    private String date;
    /**
     * 工作圈评论量
     */
    private int commentNum;
    /**
     * 工作圈点赞量
     */
    private int likesNum;
    /**
     * 是否点赞
     */
    private int ifLike;

    public int getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(int likesNum) {
        this.likesNum = likesNum;
    }

    public int getId() {
        return q_id;
    }

    public void setId(int id) {
        this.q_id = id;
    }

    public PersonBean getAuthor() {
        return author;
    }

    public void setAuthor(TypePersonBean author) {
        this.author = author;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getIfLike() {
        return ifLike;
    }

    public void setIfLike(int ifLike) {
        this.ifLike = ifLike;
    }

    public String toString(){
        return "author" + author.toString() + ",content" + content + ",date" + date + ",commentNum" +
                commentNum + ",likesNum" + likesNum + ",ifLike" + ifLike ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.q_id);
        dest.writeParcelable(this.author, flags);
        dest.writeString(this.content);
        dest.writeString(this.date);
        dest.writeInt(this.commentNum);
        dest.writeInt(this.likesNum);
        dest.writeInt(this.ifLike);
    }

    public MomentBean() {
    }

    protected MomentBean(Parcel in) {
        this.q_id = in.readInt();
        this.author = in.readParcelable(TypePersonBean.class.getClassLoader());
        this.content = in.readString();
        this.date = in.readString();
        this.commentNum = in.readInt();
        this.likesNum = in.readInt();
        this.ifLike = in.readInt();
    }

    public static final Creator<MomentBean> CREATOR = new Creator<MomentBean>() {
        @Override
        public MomentBean createFromParcel(Parcel source) {
            return new MomentBean(source);
        }

        @Override
        public MomentBean[] newArray(int size) {
            return new MomentBean[size];
        }
    };
}
