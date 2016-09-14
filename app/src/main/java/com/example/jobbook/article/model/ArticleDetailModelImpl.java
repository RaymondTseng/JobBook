package com.example.jobbook.article.model;

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
        OkHttpUtils.postString().url(Urls.ARTICLE_DETAIL_URL + articleId).content(articleId).build().execute(new StringCallback() {
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

    public interface OnLoadArticleListener {
        void onSuccess(ArticleBean mArticle);

        void onFailure(String msg, Exception e);
    }

    public interface OnLoadArticleCommentListener {
        void onSuccess(List<ArticleCommentBean> mComments);

        void onFailure(String msg, Exception e);
    }
}
