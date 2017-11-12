package com.example.jobbook.network;

import android.util.SparseArray;

import com.example.jobbook.commons.NetConstants;

/**
 * Created by zhaoxuzhang on 2017/11/4.
 * @author zhaoxuzhang
 */

public class ApiException extends RuntimeException {

    private static SparseArray<String> errors;

    static {
        errors = new SparseArray<>();
        errors.append(NetConstants.NETWORK_ERROR_CODE, NetConstants.NETWORK_ERROR_WORD);
        errors.append(NetConstants.LOGIN_ERROR_CODE, NetConstants.LOGIN_ERROR_WORD);
        errors.append(NetConstants.LOGIN_FIRST_ERROR_CODE, NetConstants.LOGIN_FIRST_ERROR_WORD);
        errors.append(NetConstants.ARTICLE_LIKE_ERROR_CODE, NetConstants.ARTICLE_LIKE_ERROR_WORD);
        errors.append(NetConstants.ARTICLE_UNLIKE_ERROR_CODE, NetConstants.ARTICLE_UNLIKE_ERROR_WORD);
    }

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    private ApiException(String detailMessage) {
        super(detailMessage);
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
