package com.example.jobbook.article.model;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.ArticleCommentBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class ArticleDetailModelImpl implements ArticleDetailModel {

    @Override
    public void loadArticle(String articleId, final OnLoadArticleListener listener) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        OkHttpUtils.postString().url(Urls.ARTICLE_DETAIL_URL + articleId + "/account/" + account).content(articleId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("article detail response", "result:" + response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    ArticleBean articleBean = new Gson().fromJson(resultBean.getResponse(), ArticleBean.class);
                    listener.onSuccess(articleBean);
                } else {
                    listener.onFailure(resultBean.getResponse(), null);
                }
            }
        });
    }

    @Override
    public void loadComments(String url, OnLoadArticleCommentListener mListener) {
        List<ArticleCommentBean> mComments = new ArrayList<>();
        mListener.onSuccess(mComments);
    }

    @Override
    public void unlike(String articleId, final OnUnlikeArticleListener listener) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            listener.onUnlikeArticleNoLoginError();
        } else {
            L.i("article", "unlike");
            account = MyApplication.getAccount();
            OkHttpUtils.get().url(Urls.ARTICLE_UNLIKE_URL + articleId + "/account/" + account).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onUnlikeArticleFailure("network error", e);
                }

                @Override
                public void onResponse(String response, int id) {
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if (resultBean.getStatus().equals("true")) {
                        L.i("job_detail_like", response);
                        listener.onUnlikeSuccess();
                    } else {
                        listener.onUnlikeArticleFailure(resultBean.getResponse(), null);
                    }
                }
            });
        }
    }

    @Override
    public void like(String articleId, final OnLikeArticleListener listener) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            listener.onLikeArticleNoLoginError();
        } else {
            account = MyApplication.getAccount();
            OkHttpUtils.get().url(Urls.ARTICLE_LIKE_URL + articleId + "/account/" + account).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onLikeArticleFailure("network error", e);
                }

                @Override
                public void onResponse(String response, int id) {
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if (resultBean.getStatus().equals("true")) {
                        L.i("job_detail_like", response);
                        listener.onLikeSuccess();
                    } else {
                        listener.onLikeArticleFailure(resultBean.getResponse(), null);
                    }
                }
            });
        }
    }

    public interface OnLoadArticleListener {
        void onSuccess(ArticleBean mArticle);

        void onFailure(String msg, Exception e);
    }

    public interface OnLoadArticleCommentListener {
        void onSuccess(List<ArticleCommentBean> mComments);

        void onFailure(String msg, Exception e);
    }

    public interface OnLikeArticleListener {
        void onLikeSuccess();

        void onLikeArticleFailure(String msg, Exception e);

        void onLikeArticleNoLoginError();
    }

    public interface OnUnlikeArticleListener {
        void onUnlikeSuccess();

        void onUnlikeArticleFailure(String msg, Exception e);

        void onUnlikeArticleNoLoginError();
    }
}
