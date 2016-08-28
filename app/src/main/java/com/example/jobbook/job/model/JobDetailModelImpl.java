package com.example.jobbook.job.model;

import android.util.Log;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.bean.JobDetailBean;
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
        String account = "";
        if(MyApplication.getmLoginStatus() == 1){
            account = MyApplication.getmPersonBean().getAccount();
        }
        OkHttpUtils.postString().url(Urls.JOB_DETAIL_URL + jobId + "/account/" + account)
                .content(jobId + "/account/" + account).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("job_detail_response", response);
                JobDetailBean jobDetailBean = new Gson().fromJson(response, JobDetailBean.class);
                listener.onSuccess(jobDetailBean);
            }
        });
    }

    @Override
    public void like(String jobId, final OnLikeJobListener listener) {

    }

    public interface OnLoadJobListener{
        void onSuccess(JobDetailBean jobDetailBean);
        void onFailure(String msg, Exception e);
    }

    public interface OnLikeJobListener{
        void onSuccess();
        void onFailure(String msg, Exception e);
    }
}
