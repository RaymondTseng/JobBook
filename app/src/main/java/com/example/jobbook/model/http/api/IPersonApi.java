package com.example.jobbook.model.http.api;

import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.model.bean.MessageBean;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.model.http.api.bean.ResultBean;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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
    Flowable<ResultBean<PersonBean>> login(@Body PersonWithDeviceTokenBean bean);

    /**
     * 检查账户有效性
     * @param account
     * @return
     */
    @GET("enter/checkforget/account/{account}")
    Flowable<ResultBean<String>> checkAccount(@Path("account") String account);

    /**
     * 修改密码
     * @param account
     * @param newpsw
     * @return
     */
    @GET("enter/forgetpsw/account/{account}/newpsw/{newpsw}")
    Flowable<ResultBean<String>> changePwdComplete(@Path("account") String account, @Path("newpsw") String newpsw);

    /**
     * 用户注册
     * @param bean
     * @return
     */
    @POST("enter/doRegister")
    Flowable<ResultBean<PersonBean>> register(@Body PersonWithDeviceTokenBean bean);

    @GET("person/MyArticle/account/{account}")
    Flowable<ResultBean<List<ArticleBean>>> loadFavouriteArticles(@Path("account") String account);

    @GET("person/myMessage/account/{account}")
    Flowable<ResultBean<List<MessageBean>>> getMessages(@Path("account") String account);

    @GET("person/focusMe/account/{account}/my/{myAccount}")
    Flowable<ResultBean<List<TypePersonBean>>> loadFanList(@Path("account") String account, @Path("myAccount") String myAccount);

    @GET("person/focus/my/{myAccount}/you/{hisAccount}")
    Flowable<ResultBean<String>> follow(@Path("myAccount") String myAccount, @Path("hisAccount") String hisAccount);

    @GET("person/unfocus/my/{myAccount}/you/{hisAccount}")
    Flowable<ResultBean<String>> unfollow(@Path("myAccount") String myAccount, @Path("hisAccount") String hisAccount);

    @GET("person/MyFavourite/account/{account}")
    Flowable<ResultBean<List<JobBean>>> loadFavouriteJobs(@Path("account") String account);

    @GET("person/myFocus/account/{account}/my/{myAccount}")
    Flowable<ResultBean<List<TypePersonBean>>> loadFollowerList(@Path("account") String account, @Path("myAccount") String myAccount);

    @GET("person/getPersonBean/account/{account}")
    Flowable<ResultBean<TypePersonBean>> loadUserDetailByAccount(@Path("account") String account);

    @Multipart
    @POST("person/upload/account/{account}")
    Flowable<ResultBean<String>> uploadAvatar(@Path("account") String account, @Part MultipartBody.Part pic);

    @GET("person/updatepsw/account/{account}/oldpwd/{oldpwd}/newpwd/{newpwd}")
    Flowable<ResultBean<String>> updatePwd(@Path("account") String account, @Path("oldpwd") String oldpwd, @Path("newpwd") String newpwd);

    @GET("person/updateTel/account/{account}/newTel/{newTel}")
    Flowable<ResultBean<String>> updateTel(@Path("account") String account, @Path("newTel") String newTel);

    @GET("person/updateName/account/{account}/newName/{newName}")
    Flowable<ResultBean<String>> updateUserName(@Path("account") String account, @Path("newName") String newName);
}
