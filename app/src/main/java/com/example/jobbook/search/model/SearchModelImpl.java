package com.example.jobbook.search.model;

import com.example.jobbook.util.L;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Xu on 2016/8/31.
 */
public class SearchModelImpl implements SearchModel {

    @Override
    public void search(String content, int pageIndex, final OnSearchListener listener) {
        L.i("search:", content + "," + pageIndex);
        OkHttpUtils.postString().content(String.valueOf(pageIndex)).url(Urls.SEARCH_URL + content).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onFaliure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("response:", "result:" + response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    List<JobBean> list = new Gson().fromJson(resultBean.getResponse(), new TypeToken<List<JobBean>>() {
                    }.getType());
                    listener.onSuccess(list);
                } else {
                    listener.onFaliure(resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    public interface OnSearchListener {
        void onSuccess(List<JobBean> list);

        void onFaliure(String msg, Exception e);
    }
}
