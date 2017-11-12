package com.example.jobbook.commons;

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
    public static final int LOGIN_ERROR_CODE = 102;
    public static final String LOGIN_ERROR_WORD = "账号或密码错误，请重试!";
    public static final int LOGIN_FIRST_ERROR_CODE = 103;
    public static final String LOGIN_FIRST_ERROR_WORD = "请先登录!";
    public static final int ARTICLE_LIKE_ERROR_CODE = 104;
    public static final String ARTICLE_LIKE_ERROR_WORD = "文章收藏失败，请重试!";
    public static final int ARTICLE_UNLIKE_ERROR_CODE = 105;
    public static final String ARTICLE_UNLIKE_ERROR_WORD = "文章取消收藏失败，请重试!";


}
