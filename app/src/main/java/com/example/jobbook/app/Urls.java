package com.example.jobbook.app;

/**
 * Created by Xu on 2016/8/25.
 */
public class Urls {

    public static final int PAZE_SIZE = 10;

    public static final String IP = "http://192.168.203.196/jobBook/index.php/";

    public static final String REGISTER_URL = IP + "/enter/doRegister/";

    public static final String LOGIN_URL = IP + "/enter/doLogin";

    public static final String SQUARE_URL = IP + "/question/allQuestions";

    public static final String SQUARE_LIKE_URL = IP + "/question/likeQuestion/q_id/";

    public static final String SQUARE_UNLIKE_URL = IP + "/question/unlikeQuestion/q_id/";

    public static final String SQUARE_LOAD_COMMENT_URL = IP + "/question/getComments/q_id/";

    public static final String NEW_SQUARE_URL = IP + "/question/releaseQuestion";

    public static final String SEND_SQUARE_COMMENT_URL = IP + "/question/comment/q_id/";

    public static final String SEARCH_URL = IP + "/job/search/keyword/";

    public static final String JOB_HEADER_SEARCH_URL = IP + "/job/search";

    public static final String GET_CODE_URL = IP + "/enter/verify";

    public static final String FEED_BACK_URL = IP + "/suggestion/postSuggestion/account/";

    public static final String FAVOURITE_JOB_URL = IP + "/person/MyFavourite/account/";

    public static final String FAVOURITE_ARTICLE_URL = IP + "/person/MyArticle/account/";

    public static final String POST_TEXT_CV_URL = IP + "/cv/postCV/account/";

    public static final String LOAD_TEXT_CV_URL = IP + "/cv/getcv/account/";

    public static final String UPLOAD_IMAGE_URL = IP + "/person/upload2/";

    public static final String UPDATE_PHONE_URL = IP + "/person/updateTel/";

    public static final String UPDATE_PWD_URL = IP + "/person/updatepsw/";

    public static final String UPDATE_USERNAME_URL = IP + "/person/updateName/";

    public static final String SEND_CV_URL = IP + "/mail/check/";

    public static final String CHECK_ACCOUNT_URL = IP + "/enter/checkforget/account/";

    public static final String CHANGE_PWD_URL = IP + "/enter/forgetpsw/account/";

    public static final String COMMENT_LIKE_AND_UNLIKE = IP + "/question/deal/";

    public static final String LOGIN_CHECK_URL = IP + "/enter/checkLogin/account/";

    public static final String USER_DETAIL_MOMENT_URL = IP + "/question/getHisQuestion/his/";

    public static final String USER_DETAIL_FOLLOW_URL = IP + "/person/myFocus/account/";

    public static final String USER_DETAIL_FANS_URL = IP + "/person/focusMe/account/";

    public static final String USER_DETAIL_FOLLOW_SB_URL = IP + "/person/focus/my/";

    public static final String USER_DETAIL_UNFOLLOW_SB_URL = IP + "/person/unfocus/my/";

    public static final String SINGLE_MOMENT_URL = IP + "/question/getSingleMoment/account/";

    public static final String GET_MESSAGE_URL = IP + "/person/myMessage/";

    public static final String GET_USER_DETAIL_BY_ACCOUNT_URL = IP + "/person/getPersonBean/account/";

    public static final String UPLOAD_ERROR_URL = IP + "/index/loadError";
}
