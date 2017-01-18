package com.example.jobbook.person.model;

import com.example.jobbook.util.L;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class FavouriteJobModelImpl implements FavouriteJobModel {

    @Override
    public void loadFavouriteJobs(String accountName, final OnLoadJobsListener listener) {
        L.i("loadfavourite", "load");
        OkHttpUtils.get().url(Urls.FAVOURITE_JOB_URL + accountName).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                L.i("loadfavourite", e.getMessage());
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("loadfavourite", "result:" + response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    List<JobBean> list = new Gson().fromJson(resultBean.getResponse(), new TypeToken<List<JobBean>>() {
                    }.getType());
                    listener.onSuccess(list);
                } else {
                    listener.onFailure(resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    public interface OnLoadJobsListener {
        void onSuccess(List<JobBean> jobs);

        void onFailure(String msg, Exception e);
    }
}
