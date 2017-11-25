package com.example.jobbook.model.http;

import android.support.annotation.NonNull;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.model.T;
import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.model.bean.FeedBackBean;
import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.model.bean.JobDetailBean;
import com.example.jobbook.model.bean.MessageBean;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.bean.MomentCommentBean;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.model.bean.TextCVBean;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.model.http.api.IArticlesApi;
import com.example.jobbook.model.http.api.IFeedBackApi;
import com.example.jobbook.model.http.api.IJobsApi;
import com.example.jobbook.model.http.api.IMainApi;
import com.example.jobbook.model.http.api.IPersonApi;
import com.example.jobbook.model.http.api.ISquareApi;
import com.example.jobbook.model.http.api.ITextCVApi;
import com.example.jobbook.model.http.api.bean.ResultBean;
import com.example.jobbook.model.exception.ApiException;
import com.example.jobbook.util.JsonUtil;
import com.example.jobbook.util.L;
import com.example.jobbook.util.NetUtil;
import com.example.jobbook.util.RxUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Xu on 2017/6/29.
 * 整个网络通信服务的启动控制，必须先调用初始化函数才能正常使用网络通信接口
 */

public class RetrofitService {
    // Urls.IP
    public static String base_url = "http://192.168.0.114/jobBook/index.php/";

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
    private static ITextCVApi textCVService;

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
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(logInterceptor)
                .addInterceptor(sLoggingInterceptor)
                .addInterceptor(sRewriteCacheControlInterceptor)
                .addNetworkInterceptor(sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        getService(okHttpClient);
    }

    private static class HttpLogger implements HttpLoggingInterceptor.Logger {
        private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            // 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if ((message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"))) {
                message = JsonUtil.formatJson(message);
            }
            mMessage.append(message.concat("\n"));
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                L.d(mMessage.toString());
            }
        }
    }

    private static void getService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(base_url)
                .build();
        articlesService = retrofit.create(IArticlesApi.class);
        feedBackService = retrofit.create(IFeedBackApi.class);
        squareService = retrofit.create(ISquareApi.class);
        jobsService = retrofit.create(IJobsApi.class);
        mainService = retrofit.create(IMainApi.class);
        personService = retrofit.create(IPersonApi.class);
        textCVService = retrofit.create(ITextCVApi.class);
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
                L.i("no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtil.isNetworkAvailable(MyApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                L.i(cacheControl);
                if ("".equals(cacheControl)) {
                    L.i("nocache");
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
                L.d("request.body() == null");
            }
            //打印url信息
            L.i(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
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
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    /**
     * 获取文章详情
     *
     * @return
     */
    public static Observable<ArticleBean> getArticleDetail(String a_id, String account) {
        return articlesService.getArticleDetail(a_id, account)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    /**
     * 文章点赞
     *
     * @return
     */
    public static Observable<String> like(String a_id, String account) {
        return articlesService.like(a_id, account)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    /**
     * 文章取消点赞
     *
     * @return
     */
    public static Observable<String> unlike(String a_id, String account) {
        return articlesService.unlike(a_id, account)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    /**
     * 发送反馈
     *
     * @param account
     * @param feedBackBean
     * @return
     */
    public static Observable<String> feedback(String account, FeedBackBean feedBackBean) {
        return feedBackService.feedBack(account, feedBackBean)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
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
                .compose(schedulersTransformer)
                .compose(errorTransformer);
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
                .compose(errorTransformer);
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
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    /**
     * 获取所有岗位
     *
     * @param index
     * @return
     */
    public static Flowable<List<JobBean>> getRecommendJobsList(int index) {
        return jobsService.getRecommendJobsList(index)
                .compose(RxUtil.<ResultBean<List<JobBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<JobBean>>handleResult());
    }

    /**
     * 搜索岗位
     *
     * @param type
     * @param location
     * @return
     */
    public static Flowable<List<JobBean>> search(int index, String type, String location) {
        return jobsService.search(index, type, location)
                .compose(RxUtil.<ResultBean<List<JobBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<JobBean>>handleResult());
    }

    /**
     * 获取岗位详情
     *
     * @param jobid
     * @param account
     * @return
     */
    public static Flowable<JobDetailBean> getJobDetail(String jobid, String account) {
        return jobsService.getJobDetail(jobid, account)
                .compose(RxUtil.<ResultBean<JobDetailBean>>rxSchedulerHelper())
                .compose(RxUtil.<JobDetailBean>handleResult());
    }

    /**
     * 岗位收藏
     *
     * @param job_id
     * @param account
     * @return
     */
    public static Flowable<String> likeJob(String job_id, String account) {
        return jobsService.like(job_id, account)
                .compose(RxUtil.<ResultBean<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleResult());
    }

    /**
     * 取消岗位收藏
     *
     * @param job_id
     * @param account
     * @return
     */
    public static Flowable<String> unlikeJob(String job_id, String account) {
        return jobsService.unlike(job_id, account)
                .compose(RxUtil.<ResultBean<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleResult());
    }

    /**
     * 发送简历
     *
     * @param account
     * @param com_id
     * @return
     */
    public static Flowable<String> sendCV(String account, String com_id) {
        return jobsService.sendCV(account, com_id)
                .compose(RxUtil.<ResultBean<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleResult());
    }

    /**
     * 用户登录
     *
     * @param bean
     * @return
     */
    public static Observable<PersonBean> login(PersonWithDeviceTokenBean bean) {
        return personService.login(bean)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    /**
     * 检查账户有效性
     *
     * @param phone
     * @return
     */
    public static Observable<String> checkAccount(String phone) {
        return personService.checkAccount(phone)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    /**
     * 修改密码
     *
     * @param account
     * @param newpsd
     * @return
     */
    public static Observable<String> changePwdComplete(String account, String newpsd) {
        return personService.changePwdComplete(account, newpsd)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<String> loginCheck(String account) {
        return mainService.loginCheck(account)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<PersonBean> register(PersonWithDeviceTokenBean bean) {
        return personService.register(bean)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<List<ArticleBean>> loadFavouriteArticles(String account) {
        return personService.loadFavouriteArticles(account)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<List<MessageBean>> getMessages(String account) {
        return personService.getMessages(account)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<List<TypePersonBean>> loadFanList(String account, String myAccount) {
        return personService.loadFanList(account, myAccount)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<String> follow(String myAccount, String hisAccount) {
        return personService.follow(myAccount, hisAccount)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<String> unfollow(String myAccount, String hisAccount) {
        return personService.unfollow(myAccount, hisAccount)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<List<JobBean>> loadFavouriteJobs(String account) {
        return personService.loadFavouriteJobs(account)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<List<TypePersonBean>> loadFollowerList(String account, String myAccount) {
        return personService.loadFollowerList(account, myAccount)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<TypePersonBean> loadUserDetailByAccount(String account) {
        return personService.loadUserDetailByAccount(account)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<List<MomentBean>> loadMomentList(String hisAccount, String myAccount) {
        return squareService.loadMomentList(hisAccount, myAccount)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<List<MomentBean>> loadSquares(String account, int index) {
        return squareService.loadSquares(account, index)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<String> newMoment(MomentBean momentBean) {
        return squareService.newMoment(momentBean)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<List<MomentCommentBean>> loadMomentComments(int s_id, int index) {
        return squareService.loadMomentComments(s_id, index)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<MomentBean> loadMomentById(String account, int s_id) {
        return squareService.loadMomentById(account, s_id)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<MomentBean> sendComment(MomentCommentBean momentCommentBean) {
        return squareService.sendComment(momentCommentBean)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<String> uploadAvatar(String account, MultipartBody.Part pic) {
        return personService.uploadAvatar(account, pic)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<String> updatePwd(String account, String oldpwd, String newpwd) {
        return personService.updatePwd(account, oldpwd, newpwd)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<String> updateTel(String account, String newTel) {
        return personService.updateTel(account, newTel)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<String> updateUserName(String account, String newName) {
        return personService.updateUserName(account, newName)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<PersonBean> postCV(String account, TextCVBean textCVBean) {
        return textCVService.postCV(account, textCVBean)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    public static Observable<TextCVBean> loadCV(String account) {
        return textCVService.loadCV(account)
                .compose(schedulersTransformer)
                .compose(errorTransformer);
    }

    /************************************ 类型转换 *******************************************/

    /**
     * 处理线程调度的变换
     */
    static ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 处理错误的变换
     * @param <T>
     */
    static ObservableTransformer errorTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.map(new HandleFunc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
        }
    };


    /**
     * 错误统一处理
     *
     * @param <T>
     */
    private static class HandleFunc<T> implements Function<ResultBean<T>, T> {

        @Override
        public T apply(ResultBean<T> resultBean) throws Exception {
            if (resultBean.getCode() != 0) {
                throw new RuntimeException(resultBean.getCode() + "");
            }
            return resultBean.getData();
        }
    }

    public static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            throwable.printStackTrace();
            return Observable.error(ApiException.handleException(throwable));
        }
    }


}
