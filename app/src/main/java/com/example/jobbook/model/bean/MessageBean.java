package com.example.jobbook.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Xu on 2017/1/21.
 */

public class MessageBean implements Parcelable {

//    private static final long serialVersionUID = 1L;

    public static final int FOLLOW = 1;

    public static final int LIKE = 2;

    public static final int COMMENT = 3;

    private String id;

    private String time;

    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.time);
        dest.writeInt(this.type);
    }

    public MessageBean() {
    }

    protected MessageBean(Parcel in) {
        this.id = in.readString();
        this.time = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel source) {
            return new MessageBean(source);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
        }
    };
}
