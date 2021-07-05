package com.example.jobbook.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 椰树 on 2016/7/16.
 * 评论模型类
 */
public class ArticleCommentBean implements Parcelable {

//    private static final long serialVersionUID = 1L;

    /**
     * 文章评论者
     */
    private PersonBean author;

    /**
     * 评论点赞数
     */
    private int favourite;

    /**
     * 评论内容
     */
    private String content;

    public PersonBean getAuthor() {
        return author;
    }

    public void setAuthor(PersonBean author) {
        this.author = author;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.author, flags);
        dest.writeInt(this.favourite);
        dest.writeString(this.content);
    }

    public ArticleCommentBean() {
    }

    protected ArticleCommentBean(Parcel in) {
        this.author = (PersonBean) in.readSerializable();
        this.favourite = in.readInt();
        this.content = in.readString();
    }

    public static final Creator<ArticleCommentBean> CREATOR = new Creator<ArticleCommentBean>() {
        @Override
        public ArticleCommentBean createFromParcel(Parcel source) {
            return new ArticleCommentBean(source);
        }

        @Override
        public ArticleCommentBean[] newArray(int size) {
            return new ArticleCommentBean[size];
        }
    };
}
