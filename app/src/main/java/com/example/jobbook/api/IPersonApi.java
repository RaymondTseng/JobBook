package com.example.jobbook.api;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.bean.MessageBean;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.bean.TypePersonBean;

import java.util.List;

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
     * 用户注册
     * @param bean
     * @return
     */
    @POST("enter/doRegister")
    Observable<ResultBean<PersonBean>> register(@Body PersonWithDeviceTokenBean bean);

    @GET("person/MyArticle/account/{account}")
    Observable<ResultBean<List<ArticleBean>>> loadFavouriteArticles(@Path("account") String account);

    @GET("person/myMessage/account/{account}")
    Observable<ResultBean<List<MessageBean>>> getMessages(@Path("account") String account);

    @GET("person/focusMe/account/{account}/my/{myAccount}")
    Observable<ResultBean<List<TypePersonBean>>> loadFanList(@Path("account") String account, @Path("myAccount") String myAccount);

    @GET("person/focus/my/{myAccount}/you/{hisAccount}")
    Observable<ResultBean<String>> follow(@Path("myAccount") String myAccount, @Path("hisAccount") String hisAccount);

    @GET("person/MyFavourite/account/{account}")
    Observable<ResultBean<List<JobBean>>> loadFavouriteJobs(@Path("account") String account);

    @GET("person/myFocus/account/{account}/my/{myAccount}")
    Observable<ResultBean<List<TypePersonBean>>> loadFollowerList(@Path("account") String account, @Path("myAccount") String myAccount);

}
