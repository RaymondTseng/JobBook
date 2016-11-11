package com.example.jobbook.bean;

import java.io.Serializable;

/**
 * Created by Xu on 2016/7/5.
 * 职位模型类
 */
public class JobBean implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String job_id;

    /**
     * 公司头像
     */
    private String logo;

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
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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
}
