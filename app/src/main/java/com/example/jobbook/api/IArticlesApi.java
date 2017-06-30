package com.example.jobbook.api;

import com.example.jobbook.bean.ArticleList;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

import static com.example.jobbook.network.RetrofitService.CACHE_CONTROL_NETWORK;

/**
 * Created by Xu on 2017/6/29.
 */

public interface IArticlesApi {

    /**
     * 获取文章列表
     * eg: http://115.28.202.143/jobBook/index.php/article/allArticle/type/
     *
     * @param type 文章类型
     * @param index 页码
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("article/allArticle/type/{type}/index/{index}")
    Observable<ArticleList> getArticlesList(@Path("type") int type, @Path("index") int index);

    /**
     * 获取文章详情
     * eg: http://115.28.202.143/jobBook/index.php/article/getArticle/a_id/1/account/123
     *
     * @param
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("article/getArticle/a_id/{a_id}/account/{account}")
    Observable<ArticleList> getArticleDetail(@Path("a_id") int a_id, @Path("account") String account);

}
