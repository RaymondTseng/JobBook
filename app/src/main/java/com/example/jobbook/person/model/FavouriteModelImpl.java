package com.example.jobbook.person.model;

import android.util.Log;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class FavouriteModelImpl implements FavouriteModel {

    @Override
    public void loadFavouriteJobs(int pageIndex, String accountName, final OnLoadJobsListener listener) {
        Log.i("loadfavourite", "load");
        OkHttpUtils.postString().url(Urls.FAVOURITE_URL + accountName).content(String.valueOf(pageIndex)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("loadfavourite", "result:" + response);
                if (response != null) {
                    Log.i("loadfavourite", "result:" + response);
                    List<JobBean> list = new Gson().fromJson(response, new TypeToken<List<JobBean>>() {
                    }.getType());
                    listener.onSuccess(list);
                }
            }
        });
    }

    public interface OnLoadJobsListener {
        void onSuccess(List<JobBean> jobs);

        void onFailure(String msg, Exception e);
    }
}
