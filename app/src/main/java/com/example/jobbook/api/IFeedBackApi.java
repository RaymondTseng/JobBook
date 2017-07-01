package com.example.jobbook.api;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.FeedBackBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

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
    @POST("/suggestion/postSuggestion/account/{account}")
    Observable<ResultBean<String>> feedBack(@Path("account") String account, @Body FeedBackBean feedBackBean);
}
