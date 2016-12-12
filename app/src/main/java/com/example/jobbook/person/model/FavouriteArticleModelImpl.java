package com.example.jobbook.person.model;

import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by root on 16-12-9.
 */

public class FavouriteArticleModelImpl implements FavouriteArticleModel{

    @Override
    public void loadArticles(String account, final OnLoadArticleListener listener) {
        OkHttpUtils.get().url(Urls.FAVOURITE_ARTICLE_URL + account).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int i) {
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if(resultBean.getStatus().equals("true")){
                    List<ArticleBean> mData = new Gson().fromJson(resultBean.getResponse(), new
                            TypeToken<List<ArticleBean>>(){}.getType());
                    listener.onSuccess(mData);
                }else{
                    listener.onFailure(resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    public interface OnLoadArticleListener{
        void onSuccess(List<ArticleBean> mData);
        void onFailure(String msg, Exception e);
    }
}
