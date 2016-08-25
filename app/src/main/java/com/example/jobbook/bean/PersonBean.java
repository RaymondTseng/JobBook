package com.example.jobbook.bean;

import java.util.List;

/**
 * Created by Xu on 2016/7/6.
 */
public class PersonBean {
    private static final long serialVersionUID = 1L;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 手机号码
     */
    private String telephone;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 视频简历
     */
    private List<VideoCV> videoCVs;
    /**
     * 文字简历
     */
    private List<TextCV> textCVs;
    /**
     * 收藏
     */
    private List<JobBean> favourite;


}
