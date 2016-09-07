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
    public void loadArticles(int pageIndex, int type, final OnLoadArticlesListListener listener) {
        Log.i("article_response:", "load");
        OkHttpUtils.postString().url(Urls.ARTICLE_URL + type).content(String.valueOf(pageIndex)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("article_response:", "network error");
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("article_response:", response);
                if(response != null){
                    List<ArticleBean> list = new Gson().fromJson(response, new TypeToken<List<ArticleBean>>() {
                    }.getType());
                    listener.onSuccess(list);
                }else {
                    listener.onFailure("article_response:null", new Exception());
                }

            }
        });
    }

    public interface OnLoadArticlesListListener {
        void onSuccess(List<ArticleBean> list);

        void onFailure(String msg, Exception e);
    }

}
