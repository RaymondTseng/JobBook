package com.example.jobbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 椰树 on 2016/8/30.
 */
public class MomentDetailBean implements Parcelable{
//    private static final long serialVersionUID = 1L;

    private MomentBean question;

    private List<MomentCommentBean> comments;

    public MomentBean getQuestionBean() {
        return question;
    }

    public void setQuestionBean(MomentBean momentBean) {
        this.question = momentBean;
    }

    public List<MomentCommentBean> getComments() {
        return comments;
    }

    public void setComments(List<MomentCommentBean> comments) {
        this.comments = comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.question, flags);
        dest.writeTypedList(this.comments);
    }

    public MomentDetailBean() {
    }

    protected MomentDetailBean(Parcel in) {
        this.question = in.readParcelable(MomentBean.class.getClassLoader());
        this.comments = in.createTypedArrayList(MomentCommentBean.CREATOR);
    }

    public static final Creator<MomentDetailBean> CREATOR = new Creator<MomentDetailBean>() {
        @Override
        public MomentDetailBean createFromParcel(Parcel source) {
            return new MomentDetailBean(source);
        }

        @Override
        public MomentDetailBean[] newArray(int size) {
            return new MomentDetailBean[size];
        }
    };
}
