package com.example.jobbook.bean;

/**
 * Created by 椰树 on 2016/8/27.
 */
public class JobExpBean {
    private static final long serialVersionUID = 1L;

    /**
     * 就职年月
     */
    private String inaugurationDate;

    /**
     * 离职年月
     */
    private String dimissionDate;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 职位名称
     */
    private String position;

    public String getInaugurationDate() {
        return inaugurationDate;
    }

    public void setInaugurationDate(String inaugurationDate) {
        this.inaugurationDate = inaugurationDate;
    }

    public String getDimissionDate() {
        return dimissionDate;
    }

    public void setDimissionDate(String dimissionDate) {
        this.dimissionDate = dimissionDate;
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
}
