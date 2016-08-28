package com.example.jobbook.job.model;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 椰树 on 2016/8/28.
 */
public class JobDetailModelImpl implements JobDetailModel{

    @Override
    public void loadJobDetail(String jobId, final OnLoadJobListener listener) {
        OkHttpUtils.postString().url(Urls.JOB_DETAIL_URL + jobId).content(jobId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                JobBean jobBean = new Gson().fromJson(response, JobBean.class);
                listener.onSuccess(jobBean);
            }
        });
    }

    @Override
    public void like(String jobId, final OnLikeJobListener listener) {

    }

    public interface OnLoadJobListener{
        void onSuccess(JobBean mJobBean);
        void onFailure(String msg, Exception e);
    }

    public interface OnLikeJobListener{
        void onSuccess();
        void onFailure(String msg, Exception e);
    }
}
