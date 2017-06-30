package com.example.jobbook.feedback.model;

import com.example.jobbook.util.L;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.FeedBackBean;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/17.
 */
public class FeedBackModelImpl implements FeedBackModel {

    @Override
    public void feedBack(String mailAddress, String content, final OnFeedBackListener listener) {
        L.i("response:", "feedback_modelimpl");
        FeedBackBean feedBackBean = new FeedBackBean();
        feedBackBean.setEmail(mailAddress);
        feedBackBean.setContent(content);
        OkHttpUtils.postString().url(Urls.FEED_BACK_URL + MyApplication.getAccount()).content(new Gson().toJson(feedBackBean)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                L.i("response:", e.getMessage());
                listener.onFailure();
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("response:", "response:" + response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    listener.onSuccess();
                } else {
                    if (resultBean.getResponse().equals("email failed")) {
                        listener.onEmailError();
                    }
                }
            }
        });
    }

    public interface OnFeedBackListener {
        void onSuccess();

        void onFailure();

        void onEmailError();
    }
}
