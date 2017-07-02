package com.example.jobbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 * 文章模型类
 */
public class ArticleBean implements Parcelable {

//    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    private String article_id;

    /**
     * 文章类型
     */
    private int type;

    /**
     * 文章图片
     */
    private String image;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章发表日期
     */
    private String date;

    /**
     * 文章阅读量
     */
    private int readingquantity;

    /**
     * 文章的评论
     */
    private List<ArticleCommentBean> comments;

    /**
     * 是否点赞
     */
    private int ifLike;

    public int isIfLike() {
        return ifLike;
    }

    public void setIfLike(int ifLike) {
        this.ifLike = ifLike;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadingquantity() {
        return readingquantity;
    }

    public void setReadingquantity(int readingquantity) {
        this.readingquantity = readingquantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ArticleCommentBean> getComments() {
        return comments;
    }

    public void setComments(List<ArticleCommentBean> comments) {
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.article_id);
        dest.writeInt(this.type);
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.date);
        dest.writeInt(this.readingquantity);
        dest.writeList(this.comments);
        dest.writeInt(this.ifLike);
    }

    public ArticleBean() {
    }

    protected ArticleBean(Parcel in) {
        this.article_id = in.readString();
        this.type = in.readInt();
        this.image = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.date = in.readString();
        this.readingquantity = in.readInt();
        this.comments = new ArrayList<ArticleCommentBean>();
        in.readList(this.comments, ArticleCommentBean.class.getClassLoader());
        this.ifLike = in.readInt();
    }

    public static final Creator<ArticleBean> CREATOR = new Creator<ArticleBean>() {
        @Override
        public ArticleBean createFromParcel(Parcel source) {
            return new ArticleBean(source);
        }

        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };
}
