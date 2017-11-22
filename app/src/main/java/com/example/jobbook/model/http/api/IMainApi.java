package com.example.jobbook.model.http.api;

import com.example.jobbook.model.http.api.bean.ResultBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import io.reactivex.Observable;

/**
 * Created by zhaoxuzhang on 2017/9/5.
 */

public interface IMainApi {

    @GET("enter/checkLogin/account/{account}")
    Observable<ResultBean<String>> loginCheck(@Path("account") String account);
}
