package com.example.jobbook.model.exception;

import android.util.Log;
import android.util.SparseArray;

import com.example.jobbook.app.constants.NetConstants;
import com.example.jobbook.util.L;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by zhaoxuzhang on 2017/11/4.
 * @author zhaoxuzhang
 */

public class ApiException extends RuntimeException {

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
        if (e instanceof SocketTimeoutException) {
            return e;
        } else if (e instanceof ConnectException) {
            return e;
        }
        int code = Integer.valueOf(e.getMessage());
        L.i("code: " + code);
        String message = getApiExceptionMessage(code);
        Log.i("handleException", "message: " + message);
        ResponseThrowable ex = new ResponseThrowable(e, message);
        return ex;
    }

    public static class ResponseThrowable extends Exception {
        public ResponseThrowable(Throwable e, String msg) {
            super(msg, e);
        }
    }

    public ApiException(int code) {
        this(getApiExceptionMessage(code));
    }

    private ApiException(String message) {
        super(message);
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
