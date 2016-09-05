package com.example.jobbook.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 椰树 on 2016/9/5.
 */
public class TextCVBean implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String head;

    /**
     * 性别
     */
    private String sex;
    /**
     * 最高学历
     */
    private String qualification;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 残疾类型
     */
    private String disabilityType;

    /**
     * 残疾等级
     */
    private String disabilityLevel;

    /**
     * 是否有残疾证
     */
    private String haveDisabilityCard;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 期待职位
     */
    private String expectPosition;

    /**
     * 期待薪水
     */
    private String expectSalary;

    /**
     * 期待工作地点
     */
    private String expectLocation;

    private List<EducationExpBean> educationExpBeanList;

    private List<JobExpBean> jobExpBeanList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDisabilityType() {
        return disabilityType;
    }

    public void setDisabilityType(String disabilityType) {
        this.disabilityType = disabilityType;
    }

    public String getDisabilityLevel() {
        return disabilityLevel;
    }

    public void setDisabilityLevel(String disabilityLevel) {
        this.disabilityLevel = disabilityLevel;
    }

    public String isHaveDisabilityCard() {
        return haveDisabilityCard;
    }

    public void setHaveDisabilityCard(String haveDisabilityCard) {
        this.haveDisabilityCard = haveDisabilityCard;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpectPosition() {
        return expectPosition;
    }

    public void setExpectPosition(String expectPosition) {
        this.expectPosition = expectPosition;
    }

    public String getExpectLocation() {
        return expectLocation;
    }

    public void setExpectLocation(String expectLocation) {
        this.expectLocation = expectLocation;
    }

    public List<EducationExpBean> getEducationExpBeanList() {
        return educationExpBeanList;
    }

    public void setEducationExpBeanList(List<EducationExpBean> educationExpBeanList) {
        this.educationExpBeanList = educationExpBeanList;
    }

    public List<JobExpBean> getJobExpBeanList() {
        return jobExpBeanList;
    }

    public void setJobExpBeanList(List<JobExpBean> jobExpBeanList) {
        this.jobExpBeanList = jobExpBeanList;
    }

    public String getExpectSalary() {
        return expectSalary;
    }

    public void setExpectSalary(String expectSalary) {
        this.expectSalary = expectSalary;
    }
}
