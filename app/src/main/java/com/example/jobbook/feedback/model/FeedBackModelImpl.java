package com.example.jobbook.feedback.model;

import android.util.Log;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.FeedBackBean;
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
        Log.i("response:", "feedback_modelimpl");
        FeedBackBean feedBackBean = new FeedBackBean();
        feedBackBean.setEmail(mailAddress);
        feedBackBean.setContent(content);
        OkHttpUtils.postString().url(Urls.FEED_BACK_URL + MyApplication.getmPersonBean().getAccount()).content(new Gson().toJson(feedBackBean)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("response:", e.getMessage());
                listener.onFailure();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("response:", "response:" + response);
                if (response != null) {
                    if (response.equals("email failed")) {
                        listener.onEmailError();
                    }else {
                        listener.onSuccess();
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
