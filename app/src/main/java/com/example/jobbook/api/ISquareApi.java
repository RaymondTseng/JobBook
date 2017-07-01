package com.example.jobbook.api;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.MomentBean;

import java.util.List;

import retrofit2.http.GET;
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
    @GET("question/focusQuestion/account/{account}/index/{index}")
    Observable<ResultBean<List<MomentBean>>> getFollowSquare(@Path("account") String account, @Path("index") int index);

    /**
     * 工作圈点赞
     * @param q_id
     * @param account
     * @return
     */
    @GET("question/likeQuestion/q_id/{q_id}/account/{account}")
    Observable<ResultBean<MomentBean>> likeSquare(@Path("q_id") int q_id, @Path("account") String account);

    /**
     * 工作圈取消点赞
     * @param q_id
     * @param account
     * @return
     */
    @GET("question/unlikeQuestion/q_id/{q_id}/account/{account}")
    Observable<ResultBean<MomentBean>> unlikeSquare(@Path("q_id") int q_id, @Path("account") String account);

}
