package com.example.jobbook.network;

import android.util.SparseArray;

/**
 * Created by zhaoxuzhang on 2017/11/4.
 * @author zhaoxuzhang
 */

public class ApiException extends RuntimeException {
    /**
     * {
          "resultCode": 0,
          "resultMessage": "成功",
          "data": {}
       }
     */

    public static final int SUCCESS = 000;

    public static final int NETWORK_ERROR_CODE = 100;
    public static final String NETWORK_ERROR_WORD = "网络错误，请检查您的网络状态!";
    public static final int LOGIN_ERROR_CODE = 201;
    public static final String LOGIN_ERROR_WORD = "网络错误，请检查您的网络状态!";
    public static final int WRONG_PASSWORD = 101;
    private static SparseArray<String> errors;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
        errors = new SparseArray<>();
        errors.append(NETWORK_ERROR_CODE, NETWORK_ERROR_WORD);
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
