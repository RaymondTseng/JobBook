package com.example.jobbook.api;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.PersonWithDeviceTokenBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Xu on 2017/7/3.
 */

public interface IPersonApi {

    /**
     * 用户登录
     * @param bean
     * @return
     */
    @POST("enter/doLogin")
    Observable<ResultBean<PersonBean>> login(@Body PersonWithDeviceTokenBean bean);
}
