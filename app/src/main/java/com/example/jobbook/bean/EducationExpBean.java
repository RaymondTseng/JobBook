package com.example.jobbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by 椰树 on 2016/8/27.
 */
public class EducationExpBean implements Parcelable{
//    private static final long serialVersionUID = 1L;


    /**
     * 入学年月
     */
    private String admissiondate;


    /**
     * 毕业年月
     */
    private String graduationdate;

    /**
     * 学校名称
     */
    private String school;


    /**
     * 专业名称
     */
    private String major;

    public String getAdmissionDate() {
        return admissiondate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissiondate = admissionDate;
    }

    public String getGraduationDate() {
        return graduationdate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationdate = graduationDate;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.major);
        dest.writeString(this.graduationdate);
        dest.writeString(this.school);
        dest.writeString(this.admissiondate);
    }

    public EducationExpBean() {
    }

    protected EducationExpBean(Parcel in) {
        this.major = in.readString();
        this.graduationdate = in.readString();
        this.school = in.readString();
        this.admissiondate = in.readString();
    }

    public static final Creator<EducationExpBean> CREATOR = new Creator<EducationExpBean>() {
        @Override
        public EducationExpBean createFromParcel(Parcel source) {
            return new EducationExpBean(source);
        }

        @Override
        public EducationExpBean[] newArray(int size) {
            return new EducationExpBean[size];
        }
    };
}
