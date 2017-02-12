package com.example.jobbook.job.model;

import com.example.jobbook.util.L;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.JobDetailBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
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
            account = MyApplication.getAccount();
        }
        OkHttpUtils.postString().url(Urls.JOB_DETAIL_URL + jobId + "/account/" + account)
                .content(jobId + "/account/" + account).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                L.i("job detail response", e.getMessage());
                listener.onLoadJobFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("job detail response", "result:" + response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    JobDetailBean jobDetailBean = new Gson().fromJson(resultBean.getResponse(), JobDetailBean.class);
                    listener.onSuccess(jobDetailBean);
                } else {
                    listener.onLoadJobFailure(resultBean.getResponse(), null);
                }
            }
        });
    }

    @Override
    public void like(String jobId, final OnLikeJobListener listener) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            listener.onLikeJobNoLoginError();
        } else {
            account = MyApplication.getAccount();
            OkHttpUtils.get().url(Urls.JOB_LIKE_URL + jobId + "/account/" + account).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onLikeJobFailure("network error", e);
                }

                @Override
                public void onResponse(String response, int id) {
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if (resultBean.getStatus().equals("true")) {
                        L.i("job_detail_like", response);
                        listener.onLikeSuccess();
                    } else {
                        listener.onLikeJobFailure(resultBean.getResponse(), null);
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
            account = MyApplication.getAccount();
            OkHttpUtils.get().url(Urls.JOB_UNLIKE_URL + jobId + "/account/" + account).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onUnlikeJobFailure("network error", e);
                }

                @Override
                public void onResponse(String response, int id) {
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if (resultBean.getStatus().equals("true")) {
                        L.i("job_detail_unlike", response);
                        listener.onUnlikeSuccess();
                    } else {
                        listener.onUnlikeJobFailure(resultBean.getResponse(), null);
                    }
                }
            });
        }

    }

    @Override
    public void sendCV(String companyId, final OnSendCVListener listener) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            listener.onSendCVNoLoginError();
        } else {
            account = MyApplication.getAccount();
        }
        OkHttpUtils.get().url(Urls.SEND_CV_URL + "account/" + account + "/com_id/" + companyId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onSendCVFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("jobdetail", "result:" + response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    L.i("jobdetail", response);
                    listener.onSendCVSuccess();
                } else {
                    if (resultBean.getResponse().equals("email failed")) {
                        listener.onSendCVEmailFailed();
                    } else if (resultBean.getResponse().equals("No destination")) {
                        listener.onSendCVNoDestination();
                    } else if (resultBean.getResponse().equals("have sent")) {
                        listener.onSendCVRepeated();
                    }else if(resultBean.getResponse().equals("no write")){
                        listener.onSendCVNoWrite();
                    }else {
                        listener.onSendCVFailure("job_detail_null", new Exception());
                    }
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

    public interface OnSendCVListener {
        void onSendCVSuccess();

        void onSendCVFailure(String msg, Exception e);

        void onSendCVNoLoginError();

        void onSendCVEmailFailed();

        void onSendCVNoDestination();

        void onSendCVRepeated();

        void onSendCVNoWrite();
    }
}
