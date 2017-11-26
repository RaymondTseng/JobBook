package com.example.jobbook.model.http.api;

import com.example.jobbook.model.bean.FeedBackBean;
import com.example.jobbook.model.http.api.bean.ResultBean;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Xu on 2017/7/1.
 */

public interface IFeedBackApi {

    /**
     * 发送反馈
     * @param account
     * @param feedBackBean
     * @return
     */
    @POST("suggestion/postSuggestion/account/{account}")
    Flowable<ResultBean<String>> feedBack(@Path("account") String account, @Body FeedBackBean feedBackBean);
}
