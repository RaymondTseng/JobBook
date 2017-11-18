package com.example.jobbook.api;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.bean.JobDetailBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import io.reactivex.Observable;

import static com.example.jobbook.network.RetrofitService.CACHE_CONTROL_NETWORK_300;

/**
 * Created by Xu on 2017/7/1.
 */

public interface IJobsApi {

    /**
     * 获取推荐岗位
     * @param index
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK_300)
    @GET("job/getRecommend/index/{index}")
    Observable<ResultBean<List<JobBean>>> getRecommendJobsList(@Path("index") int index);

    /**
     * 搜索岗位
     * @param type
     * @param location
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK_300)
    @GET("job/search/type/index/{index}")
    Observable<ResultBean<List<JobBean>>> search(@Path("index") int index, @Query("type") String type, @Query("location") String location);

    /**
     * 获取岗位详情
     * @param jobId
     * @param account
     * @return
     */
    @GET("job/getDetail/job_id/{job_id}/account/{account}")
    Observable<ResultBean<JobDetailBean>> getJobDetail(@Path("job_id") String jobId, @Path("account") String account);

    /**
     * 收藏岗位
     * @param jobId
     * @param account
     * @return
     */
    @GET("job/liked/job_id/{job_id}/account/{account}")
    Observable<ResultBean<String>> like(@Path("job_id") String jobId, @Path("account") String account);

    /**
     * 取消收藏岗位
     * @param jobId
     * @param account
     * @return
     */
    @GET("job/unliked/job_id/{job_id}/account/{account}")
    Observable<ResultBean<String>> unlike(@Path("job_id") String jobId, @Path("account") String account);

    /**
     * 发送简历
     * @param account
     * @param companyId
     * @return
     */
    @GET("mail/check/account/{account}/com_id/{com_id}")
    Observable<ResultBean<String>> sendCV(@Path("account") String account, @Path("com_id") String companyId);

}
