package com.example.jobbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Xu on 2016/9/8.
 */
public class FeedBackBean implements Parcelable {

//    private static final long serialVersionUID = 1L;

    private String email;

    private String content;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        dest.writeString(this.email);
        dest.writeString(this.content);
    }

    public FeedBackBean() {
    }

    protected FeedBackBean(Parcel in) {
        this.email = in.readString();
        this.content = in.readString();
    }

    public static final Creator<FeedBackBean> CREATOR = new Creator<FeedBackBean>() {
        @Override
        public FeedBackBean createFromParcel(Parcel source) {
            return new FeedBackBean(source);
        }

        @Override
        public FeedBackBean[] newArray(int size) {
            return new FeedBackBean[size];
        }
    };
}
