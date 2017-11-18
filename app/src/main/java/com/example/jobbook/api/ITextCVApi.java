package com.example.jobbook.api;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.TextCVBean;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import io.reactivex.Observable;

/**
 * Created by Xu on 2017/10/21.
 */

public interface ITextCVApi {

    @POST("cv/postCV/account/{account}")
    Observable<ResultBean<PersonBean>> postCV(@Path("account") String account, @Body TextCVBean textCVBean);

    @GET("cv/getcv/account/{account}")
    Observable<ResultBean<TextCVBean>> loadCV(@Path("account") String account);

}
