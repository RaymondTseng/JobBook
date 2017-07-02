package com.example.jobbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 椰树 on 2016/8/28.
 * 职位详情类型
 */
public class JobDetailBean implements Parcelable {

//    private static final long serialVersionUID = 1L;

    public int getId() {
        return job_id;
    }

    public void setId(int id) {
        this.job_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public CompanyBean getCompany() {
        return company;
    }

    public void setCompany(CompanyBean company) {
        this.company = company;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int isIfLike() {
        return ifLike;
    }

    public void setIfLike(int ifLike) {
        this.ifLike = ifLike;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * id
     */
    private int job_id;
    /**
     * 职位名称
     */
    private String name;

    /**
     * 薪资范围
     */
    private String salary;

    /**
     * 工作地点
     */
    private String location;

    /**
     * 时间
     */
    private String time;

    /**
     * 岗位职责
     */
    private String responsibilities;

    /**
     * 任职要求
     */
    private String requirements;

    /**
     * 公司
     */
    private CompanyBean company;

    /**
     * 是否点赞
     */
    private int ifLike;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.job_id);
        dest.writeString(this.name);
        dest.writeString(this.salary);
        dest.writeString(this.location);
        dest.writeString(this.time);
        dest.writeString(this.responsibilities);
        dest.writeString(this.requirements);
        dest.writeParcelable(this.company, flags);
        dest.writeInt(this.ifLike);
    }

    public JobDetailBean() {
    }

    protected JobDetailBean(Parcel in) {
        this.job_id = in.readInt();
        this.name = in.readString();
        this.salary = in.readString();
        this.location = in.readString();
        this.time = in.readString();
        this.responsibilities = in.readString();
        this.requirements = in.readString();
        this.company = in.readParcelable(CompanyBean.class.getClassLoader());
        this.ifLike = in.readInt();
    }

    public static final Creator<JobDetailBean> CREATOR = new Creator<JobDetailBean>() {
        @Override
        public JobDetailBean createFromParcel(Parcel source) {
            return new JobDetailBean(source);
        }

        @Override
        public JobDetailBean[] newArray(int size) {
            return new JobDetailBean[size];
        }
    };
}
