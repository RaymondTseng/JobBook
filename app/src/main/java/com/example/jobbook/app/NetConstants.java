package com.example.jobbook.app;

/**
 * Created by Xu on 2017/11/12.
 */

public class NetConstants {

    /**
     * {
     "code": 0,
     "message": "True" or else,
     "data": {}
     }
     */

    public static final int SUCCESS_CODE = 000;
    public static final String SUCCESS_WORD = "True";
    public static final int NETWORK_ERROR_CODE = 101;
    public static final String NETWORK_ERROR_WORD = "网络错误，请检查您的网络状态!";
    public static final int PERSON_LOGIN_ERROR_CODE = 102;
    public static final String PERSON_LOGIN_ERROR_WORD = "账号或密码错误，请重试!";
    public static final int PERSON_AUTO_LOGIN_ERROR_CODE = 103;
    public static final String PERSON_AUTO_LOGIN_ERROR_WORD = "自动登录失败";
    public static final int PERSON_LOGIN_FIRST_ERROR_CODE = 104;
    public static final String PERSON_LOGIN_FIRST_ERROR_WORD = "请先登录!";
    public static final int ARTICLE_LIKE_ERROR_CODE = 105;
    public static final String ARTICLE_LIKE_ERROR_WORD = "文章收藏失败，请重试!";
    public static final int ARTICLE_UNLIKE_ERROR_CODE = 106;
    public static final String ARTICLE_UNLIKE_ERROR_WORD = "文章取消收藏失败，请重试!";
    public static final int CV_UPDATE_ERROR_CODE = 107;
    public static final String CV_UPDATE_ERROR_WORD = "更新简历失败，请重试!";
    public static final int CV_GET_ERROR_CODE = 108;
    public static final String CV_GET_ERROR_WORD = "获取简历信息失败，请重试!";
    public static final int PERSON_REGISTER_ERROR_CODE = 109;
    public static final String PERSON_REGISTER_ERROR_WORD = "注册失败，请重试!";
    public static final int PERSON_REGISTER_ACCOUNT_EXISTS_CODE = 110;
    public static final String PERSON_REGISTER_ACCOUNT_EXISTS_WORD = "该手机号已注册!";
    public static final int JOB_LIKE_ERROR_CODE = 111;
    public static final String JOB_LIKE_ERROR_WORD = "岗位收藏失败，请重试!";
    public static final int JOB_UNLIKE_ERROR_CODE = 112;
    public static final String JOB_UNLIKE_ERROR_WORD = "岗位取消收藏失败，请重试!";


}
