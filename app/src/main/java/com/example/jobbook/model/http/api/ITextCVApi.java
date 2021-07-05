package com.example.jobbook.model.http.api;

import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.bean.TextCVBean;
import com.example.jobbook.model.http.api.bean.ResultBean;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Xu on 2017/10/21.
 */

public interface ITextCVApi {

    @POST("cv/postCV/account/{account}")
    Flowable<ResultBean<PersonBean>> postCV(@Path("account") String account, @Body TextCVBean textCVBean);

    @GET("cv/getcv/account/{account}")
    Flowable<ResultBean<TextCVBean>> loadCV(@Path("account") String account);

}
