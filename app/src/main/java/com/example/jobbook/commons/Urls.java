package com.example.jobbook.commons;

/**
 * Created by Xu on 2016/8/25.
 */
public class Urls {

    public static final int PAZE_SIZE = 10;

//    public static final String IP = "http://192.168.1.7";
    public static final String IP = "http://192.168.1.30";

    public static final String REGISTER_URL = IP + "/jobBook/index.php/Home/enter/doRegister/";

    public static final String LOGIN_URL = IP + "/jobBook/index.php/Home/enter/doLogin";

    public static final String JOB_URL = IP + "/jobBook/index.php/Home/job/getAll";

    public static final String JOB_DETAIL_URL = IP + "/jobBook/index.php/Home/job/getDetail/job_id/";

    public static final String JOB_LIKE_URL = IP + "/jobBook/index.php/Home/job/liked/job_id/";

    public static final String JOB_UNLIKE_URL = IP + "/jobBook/index.php/Home/job/unliked/job_id/";

    public static final String ARTICLE_URL = IP + "/jobBook/index.php/Home/article/allArticle/type/";

    public static final String ARTICLE_DETAIL_URL = IP + "/jobBook/index.php/Home/article/getArticle/a_id/";

    public static final String QUESTION_URL = IP + "/jobBook/index.php/Home/question/allQuestions";

    public static final String QUESTION_DETAIL_URL = IP + "/jobBook/index.php/Home/question/getQuestion/q_id/";

    public static final String NEW_QUESTION_URL = IP + "/jobBook/index.php/Home/question/releaseQuestion";

    public static final String SEND_QUESTION_COMMENT_URL = IP + "/jobBook/index.php/Home/question/comment";

    public static final String SEARCH_URL = IP + "/jobBook/index.php/Home/job/search/keyword/";

    public static final String GET_CODE_URL = IP + "/jobBook/index.php/Home/enter/verify";

    public static final String FEED_BACK_URL = IP + "/jobBook/index.php/Home/suggestion/postSuggestion/account/";

    public static final String FAVOURITE_URL = IP + "/jobBook/index.php/Home/person/MyFavourite/account/";

    public static final String POST_TEXT_CV_URL = IP + "/jobBook/index.php/Home/cv/postCV/account/";

    public static final String LOAD_TEXT_CV_URL = IP + "/jobBook/index.php/Home/cv/getcv/account/";

    public static final String UPLOAD_IMAGE_URL = IP + "/jobBook/index.php/Home/person/upload2/";

    public static final String UPDATE_PHONE_URL = IP + "/jobBook/index.php/Home/person/updateTel/";

    public static final String UPDATE_PWD_URL = IP + "/jobBook/index.php/Home/person/updatepsw/";

    public static final String UPDATE_USERNAME_URL = IP + "/jobBook/index.php/Home/person/updateName/";

    public static final String SEND_CV_URL = IP + "/jobBook/index.php/Home/mail/check/";

    public static final String CHECK_ACCOUNT_URL = IP + "/jobBook/index.php/Home/enter/checkforget/account/";

    public static final String CHANGE_PWD_URL = IP + "/jobBook/index.php/Home/enter/forgetpsw/account/";

    public static final String COMMENT_LIKE_AND_UNLIKE = IP + "/jobBook/index.php/Home/question/deal/";

    public static final String LOGIN_CHECK_URL = IP + "/jobBook/Home/enter/checkLogin/account/";

}
