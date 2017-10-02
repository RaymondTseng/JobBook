package com.example.jobbook.api;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.MomentBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

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
    Observable<ResultBean<List<MomentBean>>> getFollowSquare(@Path("account") String account, @Path("index") int index);

    /**
     * 工作圈点赞
     * @param s_id
     * @param account
     * @return
     */
    @GET("square/likeSquare/s_id/{s_id}/account/{account}")
    Observable<ResultBean<MomentBean>> likeSquare(@Path("s_id") int s_id, @Path("account") String account);

    /**
     * 工作圈取消点赞
     * @param s_id
     * @param account
     * @return
     */
    @GET("square/unlikeSquare/s_id/{s_id}/account/{account}")
    Observable<ResultBean<MomentBean>> unlikeSquare(@Path("s_id") int s_id, @Path("account") String account);

    @GET("square/allSquares/account/{account}/index/{index}")
    Observable<ResultBean<List<MomentBean>>> loadSquares(@Path("account") String account, @Path("index") int index);

    @GET("square/getHisSquare/his/{hisAccount}/my/{myAccount}")
    Observable<ResultBean<List<MomentBean>>> loadMomentList(@Path("hisAccount") String hisAccount, @Path("myAccount") String myAccount);

    @POST("square/releaseSquare")
    Observable<ResultBean<String>> newMoment(@Body MomentBean momentBean);
}
