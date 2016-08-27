package com.example.jobbook.article.model;

import android.util.Log;

import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/5.
 */
public class ArticleModelImpl implements ArticleModel {

    @Override
    public void loadArticles(int type, final OnLoadArticlesListListener listener) {
        Log.i("article_response:", "load");
        OkHttpUtils.get().addParams("", "").url(Urls.ARTICLE_URL + type).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("article_response:", e.getMessage());
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("article_response:", response);
                List<ArticleBean> list = new Gson().fromJson(response, new TypeToken<List<ArticleBean>>() {
                }.getType());
                listener.onSuccess(list);
            }
        });
    }

    public interface OnLoadArticlesListListener {
        void onSuccess(List<ArticleBean> list);
        void onFailure(String msg, Exception e);
    }

}
