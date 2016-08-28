package com.example.jobbook.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 椰树 on 2016/8/25.
 */
public class TextCV implements Serializable{
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
     * 所在城市
     */
    private String city;

    /**
     * 残疾类型
     */
    private String disabilityPattern;

    /**
     * 残疾等级
     */
    private String disabilityLevel;

    /**
     * 是否有残疾证
     */
    private boolean haveDisabilityCard;

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

    private List<EducationExpBean> educationExp;
}
