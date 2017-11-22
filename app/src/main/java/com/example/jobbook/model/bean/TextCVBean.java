package com.example.jobbook.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 椰树 on 2016/9/5.
 */
public class TextCVBean implements Parcelable{
//    private static final long serialVersionUID = 1L;

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
    private String workspace;

    /**
     * 职位
     */
    private String workposition;

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
        return workspace;
    }

    public void setCompany(String company) {
        this.workspace = company;
    }

    public String getPosition() {
        return workposition;
    }

    public void setPosition(String position) {
        this.workposition = position;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.head);
        dest.writeString(this.sex);
        dest.writeString(this.status);
        dest.writeString(this.workspace);
        dest.writeString(this.workposition);
        dest.writeString(this.city);
        dest.writeString(this.disabilitytype);
        dest.writeString(this.disabilitylevel);
        dest.writeString(this.havedisabilitycard);
        dest.writeString(this.telephone);
        dest.writeString(this.email);
        dest.writeString(this.expectposition);
        dest.writeString(this.expectsalary);
        dest.writeString(this.expectlocation);
        dest.writeTypedList(this.education);
        dest.writeTypedList(this.work);
    }

    public TextCVBean() {
    }

    protected TextCVBean(Parcel in) {
        this.name = in.readString();
        this.head = in.readString();
        this.sex = in.readString();
        this.status = in.readString();
        this.workspace = in.readString();
        this.workposition = in.readString();
        this.city = in.readString();
        this.disabilitytype = in.readString();
        this.disabilitylevel = in.readString();
        this.havedisabilitycard = in.readString();
        this.telephone = in.readString();
        this.email = in.readString();
        this.expectposition = in.readString();
        this.expectsalary = in.readString();
        this.expectlocation = in.readString();
        this.education = in.createTypedArrayList(EducationExpBean.CREATOR);
        this.work = in.createTypedArrayList(JobExpBean.CREATOR);
    }

    public static final Creator<TextCVBean> CREATOR = new Creator<TextCVBean>() {
        @Override
        public TextCVBean createFromParcel(Parcel source) {
            return new TextCVBean(source);
        }

        @Override
        public TextCVBean[] newArray(int size) {
            return new TextCVBean[size];
        }
    };
}
