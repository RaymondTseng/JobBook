package com.example.jobbook.model.http.api;

import com.example.jobbook.model.http.api.bean.ResultBean;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.bean.MomentCommentBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import io.reactivex.Flowable;

/**
 * Created by Xu on 2017/7/1.
 */

public interface ISquareApi {

    /**
     * 获取已关注人的工作圈
     * @param account
     * @param index
     * @return
     */
//    @Headers(CACHE_CONTROL_NETWORK)
    @GET("square/focusSquare/account/{account}/index/{index}")
    Flowable<ResultBean<List<MomentBean>>> getFollowSquare(@Path("account") String account, @Path("index") int index);

    /**
     * 工作圈点赞
     * @param s_id
     * @param account
     * @return
     */
    @GET("square/likeSquare/s_id/{s_id}/account/{account}")
    Flowable<ResultBean<MomentBean>> likeSquare(@Path("s_id") int s_id, @Path("account") String account);

    /**
     * 工作圈取消点赞
     * @param s_id
     * @param account
     * @return
     */
    @GET("square/unlikeSquare/s_id/{s_id}/account/{account}")
    Flowable<ResultBean<MomentBean>> unlikeSquare(@Path("s_id") int s_id, @Path("account") String account);

    @GET("square/allSquares/index/{index}")
    Flowable<ResultBean<List<MomentBean>>> loadSquares(@Path("index") int index);

    @GET("square/getHisSquare/his/{hisAccount}/my/{myAccount}")
    Flowable<ResultBean<List<MomentBean>>> loadMomentList(@Path("hisAccount") String hisAccount, @Path("myAccount") String myAccount);

    @POST("square/releaseSquare")
    Flowable<ResultBean<String>> newMoment(@Body MomentBean momentBean);

    @GET("square/getComments/s_id/{s_id}/index/{index}")
    Flowable<ResultBean<List<MomentCommentBean>>> loadMomentComments(@Path("s_id") int s_id, @Path("index") int index);

    @GET("square/getSingleMoment/account/{account}/s_id/{s_id}")
    Flowable<ResultBean<MomentBean>> loadMomentById(@Path("account") String account, @Path("s_id") int s_id);

    @POST("square/comment")
    Flowable<ResultBean<MomentBean>> sendComment(@Body MomentCommentBean momentCommentBean);
}
