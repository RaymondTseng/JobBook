package com.example.jobbook.article.model;

import com.example.jobbook.bean.ArticleBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class ArticleModelImpl implements ArticleModel {

    @Override
    public void loadArticles(int pageIndex, int type, final OnLoadArticlesListListener listener) {
//        OkHttpUtils.postString().url(Urls.ARTICLE_URL + type).content(String.valueOf(pageIndex)).build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                L.i("article_response:", "network error");
//                listener.onFailure("network error", e);
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                L.i("article_response:", response);
//                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
//                if (resultBean.getStatus().equals("true")) {
//                    List<ArticleBean> list = new Gson().fromJson(resultBean.getResponse(),
//                            new TypeToken<List<ArticleBean>>() {
//                            }.getType());
//                    listener.onSuccess(list);
//                } else {
//                    listener.onFailure(resultBean.getResponse(), new Exception());
//                }
//
//            }
//        });
        listener.onFailure("error", new Exception());
    }

    public interface OnLoadArticlesListListener {
        void onSuccess(List<ArticleBean> list);

        void onFailure(String msg, Exception e);
    }

}
