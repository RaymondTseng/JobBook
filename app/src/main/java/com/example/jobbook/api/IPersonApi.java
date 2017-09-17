package com.example.jobbook.api;

import android.content.Context;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.PersonWithDeviceTokenBean;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    /**
     * 检查账户有效性
     * @param account
     * @return
     */
    @GET("enter/checkforget/account/{account}")
    Observable<ResultBean<String>> checkAccount(@Path("account") String account);

    /**
     * 修改密码
     * @param account
     * @param newpsw
     * @return
     */
    @GET("enter/forgetpsw/account/{account}/newpsw/{newpsw}")
    Observable<ResultBean<String>> changePwdComplete(@Path("account") String account, @Path("newpsw") String newpsw);

    /**
     * 检查code
     * @param mContext
     * @param code
     * @param phone
     */
    void checkCode(Context mContext, String code, String phone);

    @POST("enter/doRegister")
    Observable<ResultBean<PersonBean>> register(@Body PersonWithDeviceTokenBean bean);


}
