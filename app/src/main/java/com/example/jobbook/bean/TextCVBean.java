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
     * 求职状态
     */
    private String status;

    /**
     * 公司
     */
    private String company;

    /**
     * 职位
     */
    private String position;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 残疾类型
     */
    private String disabilitytype;

    /**
     * 残疾等级
     */
    private String disabilitylevel;

    /**
     * 是否有残疾证
     */
    private String havedisabilitycard;

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
    private String expectposition;

    /**
     * 期待薪水
     */
    private String expectsalary;

    /**
     * 期待工作地点
     */
    private String expectlocation;

    private List<EducationExpBean> education;

    private List<JobExpBean> work;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDisabilityType() {
        return disabilitytype;
    }

    public void setDisabilityType(String disabilityType) {
        this.disabilitytype = disabilityType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDisabilityLevel() {
        return disabilitylevel;
    }

    public void setDisabilityLevel(String disabilityLevel) {
        this.disabilitylevel = disabilityLevel;
    }

    public String isHaveDisabilityCard() {
        return havedisabilitycard;
    }

    public void setHaveDisabilityCard(String haveDisabilityCard) {
        this.havedisabilitycard = haveDisabilityCard;
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
        return expectposition;
    }

    public void setExpectPosition(String expectPosition) {
        this.expectposition = expectPosition;
    }

    public String getExpectLocation() {
        return expectlocation;
    }

    public void setExpectLocation(String expectLocation) {
        this.expectlocation = expectLocation;
    }

    public List<EducationExpBean> getEducationExpBeanList() {
        return education;
    }

    public void setEducationExpBeanList(List<EducationExpBean> educationExpBeanList) {
        this.education = educationExpBeanList;
    }

    public List<JobExpBean> getJobExpBeanList() {
        return work;
    }

    public void setJobExpBeanList(List<JobExpBean> jobExpBeanList) {
        this.work = jobExpBeanList;
    }

    public String getExpectSalary() {
        return expectsalary;
    }

    public void setExpectSalary(String expectSalary) {
        this.expectsalary = expectSalary;
    }
}
