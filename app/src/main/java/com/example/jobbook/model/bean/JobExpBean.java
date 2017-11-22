package com.example.jobbook.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 椰树 on 2016/8/27.
 */
public class JobExpBean implements Parcelable {
//    private static final long serialVersionUID = 1L;

    /**
     * 就职年月
     */
    private String inaugurationdate;

    /**
     * 离职年月
     */
    private String dimissiondate;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 职位名称
     */
    private String position;

    public String getInaugurationDate() {
        return inaugurationdate;
    }

    public void setInaugurationDate(String inaugurationDate) {
        this.inaugurationdate = inaugurationDate;
    }

    public String getDimissionDate() {
        return dimissiondate;
    }

    public void setDimissionDate(String dimissionDate) {
        this.dimissiondate = dimissionDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.inaugurationdate);
        dest.writeString(this.dimissiondate);
        dest.writeString(this.company);
        dest.writeString(this.position);
    }

    public JobExpBean() {
    }

    protected JobExpBean(Parcel in) {
        this.inaugurationdate = in.readString();
        this.dimissiondate = in.readString();
        this.company = in.readString();
        this.position = in.readString();
    }

    public static final Creator<JobExpBean> CREATOR = new Creator<JobExpBean>() {
        @Override
        public JobExpBean createFromParcel(Parcel source) {
            return new JobExpBean(source);
        }

        @Override
        public JobExpBean[] newArray(int size) {
            return new JobExpBean[size];
        }
    };
}
