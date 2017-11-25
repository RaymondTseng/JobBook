package com.example.jobbook.model.http.api;

import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.model.bean.JobDetailBean;
import com.example.jobbook.model.http.api.bean.ResultBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.jobbook.model.http.RetrofitService.CACHE_CONTROL_NETWORK_300;

/**
 * Created by Xu on 2017/7/1.
 */

public interface IJobsApi {

    /**
     * 获取推荐岗位
     * @param index
     * @return
     */
//    @Headers(CACHE_CONTROL_NETWORK_300)
    @GET("job/getRecommend/index/{index}")
    Flowable<ResultBean<List<JobBean>>> getRecommendJobsList(@Path("index") int index);

    /**
     * 搜索岗位
     * @param type
     * @param location
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK_300)
    @GET("job/search/type/index/{index}")
    Flowable<ResultBean<List<JobBean>>> search(@Path("index") int index, @Query("type") String type, @Query("location") String location);

    /**
     * 获取岗位详情
     * @param jobId
     * @param account
     * @return
     */
    @GET("job/getDetail/job_id/{job_id}/account/{account}")
    Flowable<ResultBean<JobDetailBean>> getJobDetail(@Path("job_id") String jobId, @Path("account") String account);

    /**
     * 收藏岗位
     * @param jobId
     * @param account
     * @return
     */
    @GET("job/liked/job_id/{job_id}/account/{account}")
    Flowable<ResultBean<String>> like(@Path("job_id") String jobId, @Path("account") String account);

    /**
     * 取消收藏岗位
     * @param jobId
     * @param account
     * @return
     */
    @GET("job/unliked/job_id/{job_id}/account/{account}")
    Flowable<ResultBean<String>> unlike(@Path("job_id") String jobId, @Path("account") String account);

    /**
     * 发送简历
     * @param account
     * @param companyId
     * @return
     */
    @GET("mail/check/account/{account}/com_id/{com_id}")
    Flowable<ResultBean<String>> sendCV(@Path("account") String account, @Path("com_id") String companyId);

}
