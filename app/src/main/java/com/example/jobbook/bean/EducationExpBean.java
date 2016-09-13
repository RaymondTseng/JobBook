package com.example.jobbook.bean;

import java.io.Serializable;

/**
 * Created by 椰树 on 2016/8/27.
 */
public class EducationExpBean implements Serializable{
    private static final long serialVersionUID = 1L;

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
}
