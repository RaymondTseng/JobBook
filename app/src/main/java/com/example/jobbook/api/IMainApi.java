package com.example.jobbook.api;

import com.example.jobbook.api.bean.ResultBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhaoxuzhang on 2017/9/5.
 */

public interface IMainApi {

    @GET("enter/checkLogin/account/{account}")
    Observable<ResultBean<String>> loginCheck(@Path("account") String account);
}
