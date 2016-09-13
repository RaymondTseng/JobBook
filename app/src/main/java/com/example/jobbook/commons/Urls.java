package com.example.jobbook.commons;

/**
 * Created by Xu on 2016/8/25.
 */
public class Urls {

    public static final int PAZE_SIZE = 10;

    public static final String IP = "http://192.168.203.217";

    public static final String REGISTER_URL = IP + "/jobBook/Home/enter/doRegister/";

    public static final String LOGIN_URL = IP + "/jobBook/Home/enter/doLogin";

    public static final String JOB_URL = IP + "/jobBook/Home/job/getAll";

    public static final String JOB_DETAIL_URL = IP + "/jobBook/Home/job/getDetail/job_IP/";

    public static final String JOB_LIKE_URL = IP + "/jobBook/Home/job/liked/job_IP/";

    public static final String JOB_UNLIKE_URL = IP + "/jobBook/Home/job/unliked/job_IP/";

    public static final String ARTICLE_URL = IP + "/jobBook/Home/article/allArticle/type/";

    public static final String ARTICLE_DETAIL_URL = IP + "/jobBook/Home/article/getArticle/a_IP/";

    public static final String QUESTION_URL = IP + "/jobBook/Home/question/allQuestions";

    public static final String QUESTION_DETAIL_URL = IP + "/jobBook/Home/question/getQuestion/q_IP/";

    public static final String NEW_QUESTION_URL = IP + "/jobBook/Home/question/releaseQuestion";

    public static final String SEND_QUESTION_COMMENT_URL = IP + "/jobBook/Home/question/comment";

    public static final String SEARCH_URL = IP + "/jobBook/Home/job/search/keyword/";

    public static final String GET_CODE_URL = IP + "/jobBook/Home/enter/verify";

    public static final String FEED_BACK_URL = IP + "/jobBook/Home/suggestion/postSuggestion/account/";

    public static final String FAVOURITE_URL = IP + "/jobBook/Home/person/MyFavourite/account/";

    public static final String POST_TEXT_CV_URL = IP + "/jobBook/Home/cv/postCV/account/";

    public static final String LOAD_TEXT_CV_URL = IP + "/jobBook/Home/cv/getcv/account/";

    public static final String UPLOAD_IMAGE_URL = IP + "/jobBook/Home/person/upload2/";

    public static final String UPDATE_PHONE_URL = IP + "/jobBook/Home/person/updateTel/";

    public static final String UPDATE_PWD_URL = IP + "/jobBook/Home/person/updatepsw/";

    public static final String UPDATE_USERNAME_URL = IP + "/jobBook/Home/person/updateName/";

    public static final String SEND_CV_URL = IP + "/jobBook/Home/mail/check/";

}
