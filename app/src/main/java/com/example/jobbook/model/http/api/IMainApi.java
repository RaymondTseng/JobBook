package com.example.jobbook.model.http.api;

import com.example.jobbook.model.http.api.bean.ResultBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhaoxuzhang on 2017/9/5.
 */

public interface IMainApi {

    @GET("enter/checkLogin/account/{account}")
    Flowable<ResultBean<String>> loginCheck(@Path("account") String account);
}
