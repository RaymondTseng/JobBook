package com.example.jobbook.network;

import android.util.SparseArray;

import com.example.jobbook.commons.NetConstants;

/**
 * Created by zhaoxuzhang on 2017/11/4.
 * @author zhaoxuzhang
 */

public class ApiException {

    private static SparseArray<String> errors;

    static {
        errors = new SparseArray<>();
        errors.append(NetConstants.NETWORK_ERROR_CODE, NetConstants.NETWORK_ERROR_WORD);
        errors.append(NetConstants.PERSON_LOGIN_ERROR_CODE, NetConstants.PERSON_LOGIN_ERROR_WORD);
        errors.append(NetConstants.PERSON_AUTO_LOGIN_ERROR_CODE, NetConstants.PERSON_AUTO_LOGIN_ERROR_WORD);
        errors.append(NetConstants.PERSON_LOGIN_FIRST_ERROR_CODE, NetConstants.PERSON_LOGIN_FIRST_ERROR_WORD);
        errors.append(NetConstants.ARTICLE_LIKE_ERROR_CODE, NetConstants.ARTICLE_LIKE_ERROR_WORD);
        errors.append(NetConstants.ARTICLE_UNLIKE_ERROR_CODE, NetConstants.ARTICLE_UNLIKE_ERROR_WORD);
        errors.append(NetConstants.CV_UPDATE_ERROR_CODE, NetConstants.CV_UPDATE_ERROR_WORD);
        errors.append(NetConstants.CV_GET_ERROR_CODE, NetConstants.CV_GET_ERROR_WORD);
        errors.append(NetConstants.PERSON_REGISTER_ERROR_CODE, NetConstants.PERSON_REGISTER_ERROR_WORD);
        errors.append(NetConstants.JOB_LIKE_ERROR_CODE, NetConstants.JOB_LIKE_ERROR_WORD);
        errors.append(NetConstants.JOB_UNLIKE_ERROR_CODE, NetConstants.JOB_UNLIKE_ERROR_WORD);
    }

    public static Throwable handleException(Throwable e) {
        int code = Integer.valueOf(e.getMessage());
        String message = getApiExceptionMessage(code);
        ResponseThrowable ex = new ResponseThrowable(e, code);
        ex.message = message;
        return ex;
    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable e, int code) {
            super(e);
            this.code = code;
        }
    }


    /**
     * 需要根据错误码对错误信息进行一个转换
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        return errors.get(code);
    }
}
