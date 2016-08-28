package com.example.jobbook.bean;

import java.io.Serializable;

/**
 * Created by Xu on 2016/7/5.
 * 职位模型类
 */
public class JobBean implements Serializable{
    private static final long serialVersionUID = 1L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    /**
     * id
     */
    private int id;
    /**
     * 职位名称
     */
    private String name;
    /**
     * 薪资范围
     */
    private String salary;

    /**
     * 公司
     */
    private CompanyBean company;
    /**
     * 岗位职责
     */
    private String responsibilities;
    /**
     * 任职要求
     */
    private String requirements;

    /**
     * 时间
     */
    private String time;

    /**
     * 点赞数
     */
    private int like;
}
