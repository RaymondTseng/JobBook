package com.example.jobbook.app;

/**
 * Created by Xu on 2017/11/12.
 */

public class NetConstants {

    /**
     * {
     * "code": 0,
     * "message": "True" or else,
     * "data": {}
     * }
     */

    public static final int PAZE_SIZE = 10;
    public static final String IP = "http://192.168.0.105/jobBook/index.php/";

    public static final int SUCCESS_CODE = 000;
    public static final String SUCCESS_WORD = "True";
    public static final int NETWORK_ERROR_CODE = 101;
    public static final String NETWORK_ERROR_WORD = "网络错误，请检查您的网络状态!";
    public static final int PERSON_LOGIN_ERROR_CODE = 102;
    public static final String PERSON_LOGIN_ERROR_WORD = "账号或密码错误，请重试!";
    public static final int PERSON_AUTO_LOGIN_ERROR_CODE = 103;
    public static final String PERSON_AUTO_LOGIN_ERROR_WORD = "登录信息过期，请重新登录!";
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
    public static final int PERSON_SEND_MAIL_NO_DESTINATION_ERROR_CODE = 113;
    public static final String PERSON_SEND_MAIL_NO_DESTINATION_ERROR_WORD = "该公司没有官方邮箱，请到官网联系!";
    public static final int PERSON_SEND_MAIL_ERROR_CODE = 114;
    public static final String PERSON_SEND_MAIL_ERROR_WORD = "该公司官方邮箱错误，请重试!";
    public static final int PERSON_SEND_MAIL_NO_DATA_CODE = 115;
    public static final String PERSON_SEND_MAIL_NO_DATA_WORD = "发送失败，请先完善简历!";
    public static final int PERSON_SEND_MAIL_HAVE_SENT_CODE = 116;
    public static final String PERSON_SEND_MAIL_HAVE_SENT_WORD = "您之前已经投递过该公司，请勿重复投递!";
    public static final int PERSON_UPDATE_TEL_ERROR_CODE = 117;
    public static final String PERSON_UPDATE_TEL_ERROR_WORD = "更新电话错误，请重试!";
    public static final int PERSON_UPDATE_PWD_ERROR_CODE = 118;
    public static final String PERSON_UPDATE_PWD_ERROR_WORD = "更新密码错误，请重试!";
    public static final int PERSON_UPDATE_PWD_WRONG_PWD_ERROR_CODE = 119;
    public static final String PERSON_UPDATE_PWD_WRONG_PWD_ERROR_WORD = "原始密码错误，请重试!";
    public static final int PERSON_UPDATE_NAME_ERROR_CODE = 120;
    public static final String PERSON_UPDATE_NAME_ERROR_WORD = "更新昵称失败，请重试!";
    public static final int PERSON_UPDATE_NAME_NAME_EXISTS_CODE = 121;
    public static final String PERSON_UPDATE_NAME_NAME_EXISTS_WORD = "昵称已存在，请重试!";
    public static final int PERSON_FOCUS_ERROR_CODE = 122;
    public static final String PERSON_FOCUS_ERROR_WORD = "关注用户失败，请重试!";
    public static final int PERSON_FOCUS_HAVED_FOCUSED_ERROR_CODE = 123;
    public static final String PERSON_FOCUS_HAVED_FOCUSED_ERROR_WORD = "不能重复关注用户!";
    public static final int PERSON_UNFOCUS_ERROR_CODE = 124;
    public static final String PERSON_UNFOCUS_ERROR_WORD = "取消关注用户失败，请重试!";
    public static final int PERSON_UPLOAD_HEAD_ERROR_CODE = 125;
    public static final String PERSON_UPLOAD_HEAD_ERROR_WORD = "上传头像失败，请重试!";
    public static final int SQUARE_RELEASE_ERROR_CODE = 126;
    public static final String SQUARE_RELEASE_ERROR_WORD = "发送工作圈失败，请重试!";
    public static final int SQUARE_LIKE_ERROR_CODE = 128;
    public static final String SQUARE_LIKE_ERROR_WORD = "工作圈点赞错误，请重试!";
    public static final int SQUARE_HAS_LIKED_ERROR_CODE = 129;
    public static final String SQUARE_HAS_LIKED_ERROR_WORD = "你已经点赞过该工作圈了~";
    public static final int SQUARE_UNLIKE_ERROR_CODE = 130;
    public static final String SQUARE_UNLIKE_ERROR_WORD = "工作圈取消点赞错误，请重试!";
}
