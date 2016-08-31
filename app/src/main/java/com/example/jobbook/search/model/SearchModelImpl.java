package com.example.jobbook.search.model;

import android.util.Log;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.commons.Urls;
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
        Log.i("search:", content + "," + pageIndex);
        OkHttpUtils.postString().content(String.valueOf(pageIndex)).url(Urls.SEARCH_URL + content).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onFaliure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("response:", "result:" + response);
                if (response != null) {
                    List<JobBean> list = new Gson().fromJson(response, new TypeToken<List<JobBean>>() {
                    }.getType());
                    listener.onSuccess(list);
                }
            }
        });
    }

    public interface OnSearchListener {
        void onSuccess(List<JobBean> list);

        void onFaliure(String msg, Exception e);
    }
}
