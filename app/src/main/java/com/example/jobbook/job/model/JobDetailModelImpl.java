package com.example.jobbook.job.model;

import android.util.Log;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.JobDetailBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 椰树 on 2016/8/28.
 */
public class JobDetailModelImpl implements JobDetailModel {

    @Override
    public void loadJobDetail(String jobId, final OnLoadJobListener listener) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getmPersonBean().getAccount();
        }
        OkHttpUtils.postString().url(Urls.JOB_DETAIL_URL + jobId + "/account/" + account)
                .content(jobId + "/account/" + account).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onLoadJobFailure("network error", e);
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
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            listener.onLikeJobNoLoginError();
        } else {
            account = MyApplication.getmPersonBean().getAccount();
            OkHttpUtils.get().url(Urls.JOB_LIKE_URL + jobId + "/account/" + account).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onLikeJobFailure("network error", e);
                }

                @Override
                public void onResponse(String response, int id) {
                    if (response != null) {
                        Log.i("job_detail_like", response);
                        listener.onLikeSuccess();
                    }
                }
            });
        }
    }

    @Override
    public void unlike(String jobId, final OnUnlikeJobListener listener) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            listener.onUnlikeJobNoLoginError();
        } else {
            account = MyApplication.getmPersonBean().getAccount();
            OkHttpUtils.get().url(Urls.JOB_UNLIKE_URL + jobId + "/account/" + account).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onUnlikeJobFailure("network error", e);
                }

                @Override
                public void onResponse(String response, int id) {
                    if (response != null) {
                        Log.i("job_detail_unlike", response);
                        listener.onUnlikeSuccess();
                    }
                }
            });
        }

    }

    @Override
    public void sendCV(String jobId, final OnSendCVListener listener) {
        String account = "";
        if(MyApplication.getmLoginStatus() == 0){
            listener.onSendCVNoLoginError();
        }else{
            account = MyApplication.getmPersonBean().getAccount();
        }
        OkHttpUtils.postString().url(Urls.SEND_CV_URL).content("").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onSendCVFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                if(response != null){
                    listener.onSendCVSuccess();
                }else{
                    listener.onSendCVFailure("job_detail_null", new Exception());
                }
            }
        });
    }

    public interface OnLoadJobListener {
        void onSuccess(JobDetailBean jobDetailBean);

        void onLoadJobFailure(String msg, Exception e);
    }

    public interface OnLikeJobListener {
        void onLikeSuccess();

        void onLikeJobFailure(String msg, Exception e);

        void onLikeJobNoLoginError();
    }

    public interface OnUnlikeJobListener {
        void onUnlikeSuccess();

        void onUnlikeJobFailure(String msg, Exception e);

        void onUnlikeJobNoLoginError();
    }

    public interface  OnSendCVListener{
        void onSendCVSuccess();
        void onSendCVFailure(String msg, Exception e);
        void onSendCVNoLoginError();
    }
}
