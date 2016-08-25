package com.example.jobbook.util;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Xu on 2016/8/25.
 */
public class OkHttpUtils {

    private static final long DEFAULT_CONNECT_TIMEOUT = 30000L;
    private static final long DEFAULT_READ_TIMEOUT = 30000L;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient okHttpClient;

    private OkHttpUtils() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static OkHttpUtils instance = new OkHttpUtils();
    }

    private static OkHttpUtils getInstance() {
        return SingletonHolder.instance;
    }

    private void getRequest(String url) {
        Request request = new Request.Builder().url(url).build();
    }

    private void postJsonDataRequest(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
    }


    /*********************** 对外接口 ************************/

    public static void get(String url) {
        
    }

    public static void postJsonData(String url, String json) {

    }

}
