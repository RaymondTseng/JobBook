package com.example.jobbook.job.model;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/6.
 */
public class JobModelImpl implements JobModel{

    @Override
    public void loadJobs(final OnLoadJobListListener listener) {
        OkHttpUtils.get().url(Urls.JOB_URL).addParams("", "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                List<JobBean> jobBeanList = new Gson().fromJson(response, new TypeToken<List<JobBean>>(){}.getType());
                listener.onSuccess(jobBeanList);
            }
        });
    }

    public interface OnLoadJobListListener {
        void onSuccess(List<JobBean> list);
        void onFailure(String msg, Exception e);
    }
}
