package com.example.jobbook.commons;

/**
 * Created by Xu on 2016/8/25.
 */
public class Urls {

    public static final int PAZE_SIZE = 10;

//    public static final String IP = "http://115.28.202.143";
    public static final String IP = "http://192.168.1.118";

    public static final String REGISTER_URL = IP + "/jobBook/enter/doRegister/";

    public static final String LOGIN_URL = IP + "/jobBook/enter/doLogin";

    public static final String JOB_URL = IP + "/jobBook/job/getAll";

    public static final String JOB_DETAIL_URL = IP + "/jobBook/job/getDetail/job_id/";

    public static final String JOB_LIKE_URL = IP + "/jobBook/job/liked/job_id/";

    public static final String JOB_UNLIKE_URL = IP + "/jobBook/job/unliked/job_id/";

    public static final String ARTICLE_URL = IP + "/jobBook/article/allArticle/type/";

    public static final String ARTICLE_DETAIL_URL = IP + "/jobBook/article/getArticle/a_id/";

    public static final String SQUARE_URL = IP + "/jobBook/question/allQuestions";

    public static final String SQUARE_DETAIL_URL = IP + "/jobBook/question/getQuestion/q_id/";

    public static final String NEW_SQUARE_URL = IP + "/jobBook/question/releaseQuestion";

    public static final String SEND_SQUARE_COMMENT_URL = IP + "/jobBook/question/comment";

    public static final String SEARCH_URL = IP + "/jobBook/job/search/keyword/";

    public static final String GET_CODE_URL = IP + "/jobBook/enter/verify";

    public static final String FEED_BACK_URL = IP + "/jobBook/suggestion/postSuggestion/account/";

    public static final String FAVOURITE_URL = IP + "/jobBook/person/MyFavourite/account/";

    public static final String POST_TEXT_CV_URL = IP + "/jobBook/cv/postCV/account/";

    public static final String LOAD_TEXT_CV_URL = IP + "/jobBook/cv/getcv/account/";

    public static final String UPLOAD_IMAGE_URL = IP + "/jobBook/person/upload2/";

    public static final String UPDATE_PHONE_URL = IP + "/jobBook/person/updateTel/";

    public static final String UPDATE_PWD_URL = IP + "/jobBook/person/updatepsw/";

    public static final String UPDATE_USERNAME_URL = IP + "/jobBook/person/updateName/";

    public static final String SEND_CV_URL = IP + "/jobBook/mail/check/";

    public static final String CHECK_ACCOUNT_URL = IP + "/jobBook/enter/checkforget/account/";

    public static final String CHANGE_PWD_URL = IP + "/jobBook/enter/forgetpsw/account/";

    public static final String COMMENT_LIKE_AND_UNLIKE = IP + "/jobBook/question/deal/";

    public static final String LOGIN_CHECK_URL = IP + "/jobBook/enter/checkLogin/account/";

}
