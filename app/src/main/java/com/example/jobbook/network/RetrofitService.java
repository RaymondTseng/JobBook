package com.example.jobbook.network;

import android.support.annotation.NonNull;

import com.example.jobbook.MyApplication;
import com.example.jobbook.api.IArticlesApi;
import com.example.jobbook.api.IFeedBackApi;
import com.example.jobbook.api.IJobsApi;
import com.example.jobbook.api.IMainApi;
import com.example.jobbook.api.IPersonApi;
import com.example.jobbook.api.ISquareApi;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.FeedBackBean;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.bean.JobDetailBean;
import com.example.jobbook.bean.MessageBean;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.util.L;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Xu on 2017/6/29.
 * 整个网络通信服务的启动控制，必须先调用初始化函数才能正常使用网络通信接口
 */

public class RetrofitService {
    // Urls.IP
    public static String base_url = "http://192.168.199.195/jobBook/index.php/";

    //设缓存有效期为1天
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    public static final String CACHE_CONTROL_NETWORK_300 = "Cache-Control: public, max-age=300";
    public static final String CACHE_CONTROL_NETWORK_3000 = "Cache-Control: public, max-age=3000";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    // 递增页码
    private static final int INCREASE_PAGE = 10;

    // 需要的服务
    private static IArticlesApi articlesService;
    private static IFeedBackApi feedBackService;
    private static ISquareApi squareService;
    private static IJobsApi jobsService;
    private static IMainApi mainService;
    private static IPersonApi personService;

    private RetrofitService() {
        throw new AssertionError();
    }

    /**
     * 初始化网络通信服务
     */
    public static void init() {
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(MyApplication.getContext().getCacheDir(), "JobbookCache"),
                1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(sLoggingInterceptor)
                .addInterceptor(sRewriteCacheControlInterceptor)
                .addNetworkInterceptor(sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        getService(okHttpClient);
    }

    private static void getService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(base_url)
                .build();
        articlesService = retrofit.create(IArticlesApi.class);
        feedBackService = retrofit.create(IFeedBackApi.class);
        squareService = retrofit.create(ISquareApi.class);
        jobsService = retrofit.create(IJobsApi.class);
        mainService = retrofit.create(IMainApi.class);
        personService = retrofit.create(IPersonApi.class);
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable(MyApplication.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Logger.e("no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtil.isNetworkAvailable(MyApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                L.i("cacheControl", "is:" + cacheControl);
                if ("".equals(cacheControl)) {
                    L.i("cacheControl", "nocache");
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "no-cache")
                            .removeHeader("Pragma")
                            .build();
                }
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                Logger.d("LogTAG", "request.body() == null");
            }
            //打印url信息
            Logger.w(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);

            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    /************************************ API *******************************************/

    /**
     * 获取文章列表
     *
     * @return
     */
    public static Observable<List<ArticleBean>> getArticlesList(int type, int page) {
        return articlesService.getArticlesList(type, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapArticles());
    }

    /**
     * 获取文章详情
     *
     * @return
     */
    public static Observable<ArticleBean> getArticleDetail(String a_id, String account) {
        return articlesService.getArticleDetail(a_id, account)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapArticleDetail());
    }

    /**
     * 文章点赞
     *
     * @return
     */
    public static Observable<ResultBean<String>> like(String a_id, String account) {
        return articlesService.like(a_id, account)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 文章取消点赞
     *
     * @return
     */
    public static Observable<ResultBean<String>> unlike(String a_id, String account) {
        return articlesService.unlike(a_id, account)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送反馈
     *
     * @param account
     * @param feedBackBean
     * @return
     */
    public static Observable<ResultBean<String>> feedback(String account, FeedBackBean feedBackBean) {
        return feedBackService.feedBack(account, feedBackBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取已关注人的工作圈
     *
     * @param account
     * @param index
     * @return
     */
    public static Observable<List<MomentBean>> getFollowSquare(String account, int index) {
        return squareService.getFollowSquare(account, index)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapSquareFollow());
    }

    /**
     * 点赞工作圈
     *
     * @param s_id
     * @param account
     * @return
     */
    public static Observable<MomentBean> likeSquare(int s_id, String account) {
        return squareService.likeSquare(s_id, account)
                .map(new HttpResultFunc<MomentBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 取消点赞工作圈
     *
     * @param s_id
     * @param account
     * @return
     */
    public static Observable<MomentBean> unlikeSquare(int s_id, String account) {
        return squareService.unlikeSquare(s_id, account)
                .map(new HttpResultFunc<MomentBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取所有岗位
     *
     * @param index
     * @return
     */
    public static Observable<List<JobBean>> getRecommendJobsList(int index) {
        return jobsService.getRecommendJobsList(index)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapJobs());
    }

    /**
     * 搜索岗位
     *
     * @param type
     * @param location
     * @return
     */
    public static Observable<List<JobBean>> search(int index, String type, String location) {
        return jobsService.search(index, type, location)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapJobs());
    }

    /**
     * 获取岗位详情
     *
     * @param jobid
     * @param account
     * @return
     */
    public static Observable<JobDetailBean> getJobDetail(String jobid, String account) {
        return jobsService.getJobDetail(jobid, account)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(_flatMapJobDetail());
    }

    /**
     * 岗位收藏
     *
     * @param job_id
     * @param account
     * @return
     */
    public static Observable<ResultBean<String>> likeJob(String job_id, String account) {
        return jobsService.like(job_id, account)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 取消岗位收藏
     *
     * @param job_id
     * @param account
     * @return
     */
    public static Observable<ResultBean<String>> unlikeJob(String job_id, String account) {
        return jobsService.unlike(job_id, account)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送简历
     *
     * @param account
     * @param com_id
     * @return
     */
    public static Observable<ResultBean<String>> sendCV(String account, String com_id) {
        return jobsService.sendCV(account, com_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用户登录
     *
     * @param bean
     * @return
     */
    public static Observable<ResultBean<PersonBean>> login(PersonWithDeviceTokenBean bean) {
        return personService.login(bean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 检查账户有效性
     *
     * @param phone
     * @return
     */
    public static Observable<ResultBean<String>> checkAccount(String phone) {
        return personService.checkAccount(phone)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 修改密码
     *
     * @param account
     * @param newpsd
     * @return
     */
    public static Observable<ResultBean<String>> changePwdComplete(String account, String newpsd) {
        return personService.changePwdComplete(account, newpsd)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> loginCheck(String account) {
        return mainService.loginCheck(account)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<PersonBean> register(PersonWithDeviceTokenBean bean) {
        return personService.register(bean)
                .map(new HttpResultFunc<PersonBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<ArticleBean>> loadFavouriteArticles(String account) {
        return personService.loadFavouriteArticles(account)
                .map(new HttpResultFunc<List<ArticleBean>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<MessageBean>> getMessages(String account) {
        return personService.getMessages(account)
                .map(new HttpResultFunc<List<MessageBean>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<TypePersonBean>> loadFanList(String account, String myAccount) {
        return personService.loadFanList(account, myAccount)
                .map(new HttpResultFunc<List<TypePersonBean>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> follow(String myAccount, String hisAccount) {
        return personService.follow(myAccount, hisAccount)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<JobBean>> loadFavouriteJobs(String account) {
        return personService.loadFavouriteJobs(account)
                .map(new HttpResultFunc<List<JobBean>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<TypePersonBean>> loadFollowerList(String account, String myAccount) {
        return personService.loadFollowerList(account, myAccount)
                .map(new HttpResultFunc<List<TypePersonBean>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<MomentBean>> loadMomentList(String hisAccount, String myAccount) {
        return squareService.loadMomentList(hisAccount, myAccount)
                .map(new HttpResultFunc<List<MomentBean>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<MomentBean>> loadSquares(String account, int index) {
        return squareService.loadSquares(account, index)
                .map(new HttpResultFunc<List<MomentBean>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> newMoment(MomentBean momentBean) {
        return squareService.newMoment(momentBean)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<MomentCommentBean>> loadMomentComments(int s_id, int index) {
        return squareService.loadMomentComments(s_id, index)
                .map(new HttpResultFunc<List<MomentCommentBean>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<MomentBean> loadMomentById(String account, int s_id) {
        return squareService.loadMomentById(account, s_id)
                .map(new HttpResultFunc<MomentBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<MomentBean> sendComment(MomentCommentBean momentCommentBean) {
        return squareService.sendComment(momentCommentBean)
                .map(new HttpResultFunc<MomentBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> uploadAvatar(String account, MultipartBody.Part pic) {
        return personService.uploadAvatar(account, pic)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> updatePwd(String account, String oldpwd, String newpwd) {
        return personService.updatePwd(account, oldpwd, newpwd)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> updateTel(String account, String newTel) {
        return personService.updateTel(account, newTel)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> updateUserName(String account, String newName) {
        return personService.updateUserName(account, newName)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /************************************ 类型转换 *******************************************/

    /**
     * 类型转换
     *
     * @return
     */
    private static Func1<ResultBean<List<ArticleBean>>, Observable<List<ArticleBean>>> _flatMapArticles() {
        return new Func1<ResultBean<List<ArticleBean>>, Observable<List<ArticleBean>>>() {
            @Override
            public Observable<List<ArticleBean>> call(ResultBean<List<ArticleBean>> articleListWrapper) {
                if (!articleListWrapper.getStatus().equals("true")) {
                    return Observable.empty();
                }
                return Observable.just(articleListWrapper.getResponse());
            }
        };
    }

    /**
     * 类型转换
     *
     * @return
     */
    private static Func1<ResultBean<ArticleBean>, Observable<ArticleBean>> _flatMapArticleDetail() {
        return new Func1<ResultBean<ArticleBean>, Observable<ArticleBean>>() {
            @Override
            public Observable<ArticleBean> call(ResultBean<ArticleBean> articleWrapper) {
                if (!articleWrapper.getStatus().equals("true")) {
                    return Observable.empty();
                }
                return Observable.just(articleWrapper.getResponse());
            }
        };
    }

    /**
     * 类型转换
     *
     * @return
     */
    private static Func1<ResultBean<List<MomentBean>>, Observable<List<MomentBean>>> _flatMapSquareFollow() {
        return new Func1<ResultBean<List<MomentBean>>, Observable<List<MomentBean>>>() {
            @Override
            public Observable<List<MomentBean>> call(ResultBean<List<MomentBean>> resultBean) {
                if (!resultBean.getStatus().equals("true")) {
                    return Observable.empty();
                }
                return Observable.just(resultBean.getResponse());
            }
        };
    }

    /**
     * 类型转换
     *
     * @return
     */
    private static Func1<ResultBean<MomentBean>, Observable<MomentBean>> _flatMaplikeSquare() {
        return new Func1<ResultBean<MomentBean>, Observable<MomentBean>>() {
            @Override
            public Observable<MomentBean> call(ResultBean<MomentBean> resultBean) {
                if (!resultBean.getStatus().equals("true")) {
                    Logger.i("error", resultBean.getResponse());
                    return Observable.empty();
                }
                L.i("square_like_response", resultBean.getResponse().toString() + "");
                return Observable.just(resultBean.getResponse());
            }
        };
    }

    /**
     * 类型转换
     *
     * @return
     */
    private static Func1<ResultBean<List<JobBean>>, Observable<List<JobBean>>> _flatMapJobs() {
        return new Func1<ResultBean<List<JobBean>>, Observable<List<JobBean>>>() {
            @Override
            public Observable<List<JobBean>> call(ResultBean<List<JobBean>> resultBean) {
                if (!resultBean.getStatus().equals("true")) {
                    Logger.i("error", resultBean.getResponse());
                    return Observable.empty();
                }
                L.i("square_like_response", resultBean.getResponse().toString() + "");
                return Observable.just(resultBean.getResponse());
            }
        };
    }

    /**
     * 类型转换
     *
     * @return
     */
    private static Func1<ResultBean<JobDetailBean>, Observable<JobDetailBean>> _flatMapJobDetail() {
        return new Func1<ResultBean<JobDetailBean>, Observable<JobDetailBean>>() {
            @Override
            public Observable<JobDetailBean> call(ResultBean<JobDetailBean> resultBean) {
                if (!resultBean.getStatus().equals("true")) {
                    return Observable.empty();
                }
                return Observable.just(resultBean.getResponse());
            }
        };
    }

    /**
     * 类型转换
     *
     * @return
     */
    private static Func1<ResultBean<PersonBean>, Observable<PersonBean>> _flatMapLogin() {
        return new Func1<ResultBean<PersonBean>, Observable<PersonBean>>() {
            @Override
            public Observable<PersonBean> call(ResultBean<PersonBean> resultBean) {
                if (!resultBean.getStatus().equals("true")) {
                    return Observable.empty();
                }
                return Observable.just(resultBean.getResponse());
            }
        };
    }

    /**
     * 错误统一处理
     *
     * @param <T>
     */
    private static class HttpResultFunc<T> implements Func1<ResultBean<T>, T> {

        @Override
        public T call(ResultBean<T> resultBean) {
            if (!resultBean.getStatus().equals("true")) {
                throw new RuntimeException((String) resultBean.getResponse());
            }
            return resultBean.getResponse();
        }
    }


}
