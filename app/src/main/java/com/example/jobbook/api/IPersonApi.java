package com.example.jobbook.api;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.bean.MessageBean;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.bean.TypePersonBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import io.reactivex.Observable;

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

    @GET("person/unfocus/my/{myAccount}/you/{hisAccount}")
    Observable<ResultBean<String>> unfollow(@Path("myAccount") String myAccount, @Path("hisAccount") String hisAccount);

    @GET("person/MyFavourite/account/{account}")
    Observable<ResultBean<List<JobBean>>> loadFavouriteJobs(@Path("account") String account);

    @GET("person/myFocus/account/{account}/my/{myAccount}")
    Observable<ResultBean<List<TypePersonBean>>> loadFollowerList(@Path("account") String account, @Path("myAccount") String myAccount);

    @GET("person/getPersonBean/account/{account}")
    Observable<ResultBean<TypePersonBean>> loadUserDetailByAccount(@Path("account") String account);

    @Multipart
    @POST("person/upload/account/{account}")
    Observable<ResultBean<String>> uploadAvatar(@Path("account") String account, @Part MultipartBody.Part pic);

    @GET("person/updatepsw/account/{account}/oldpwd/{oldpwd}/newpwd/{newpwd}")
    Observable<ResultBean<String>> updatePwd(@Path("account") String account, @Path("oldpwd") String oldpwd, @Path("newpwd") String newpwd);

    @GET("person/updateTel/account/{account}/newTel/{newTel}")
    Observable<ResultBean<String>> updateTel(@Path("account") String account, @Path("newTel") String newTel);

    @GET("person/updateName/account/{account}/newName/{newName}")
    Observable<ResultBean<String>> updateUserName(@Path("account") String account, @Path("newName") String newName);
}
