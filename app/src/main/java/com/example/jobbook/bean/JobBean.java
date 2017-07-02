package com.example.jobbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Xu on 2016/7/5.
 * 职位模型类
 */
public class JobBean implements Parcelable {


//    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String job_id;

    /**
     * 公司头像
     */
    private String comlogo;

    /**
     * 职位名称
     */
    private String name;

    /**
     * 公司名称
     */
    private String companyname;

    /**
     * 公司地址
     */
    private String location;

    /**
     * 时间
     */
    private String time;

    /**
     * 薪水
     */
    private String salary;


    public String getId() {
        return job_id;
    }

    public void setId(String id) {
        this.job_id = id;
    }

    public String getLogo() {
        return comlogo;
    }

    public void setLogo(String logo) {
        this.comlogo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyname;
    }

    public void setCompanyName(String companyName) {
        this.companyname = companyName;
    }

    public String getCompanyLocation() {
        return location;
    }

    public void setCompanyLocation(String companyLocation) {
        this.location = companyLocation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.salary);
        dest.writeString(this.comlogo);
        dest.writeString(this.name);
        dest.writeString(this.companyname);
        dest.writeString(this.location);
        dest.writeString(this.time);
        dest.writeString(this.job_id);
    }

    public JobBean() {
    }

    protected JobBean(Parcel in) {
        this.salary = in.readString();
        this.comlogo = in.readString();
        this.name = in.readString();
        this.companyname = in.readString();
        this.location = in.readString();
        this.time = in.readString();
        this.job_id = in.readString();
    }

    public static final Creator<JobBean> CREATOR = new Creator<JobBean>() {
        @Override
        public JobBean createFromParcel(Parcel source) {
            return new JobBean(source);
        }

        @Override
        public JobBean[] newArray(int size) {
            return new JobBean[size];
        }
    };
}
