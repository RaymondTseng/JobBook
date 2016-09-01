package com.example.jobbook.feedback.model;

import com.example.jobbook.MyApplication;
import com.example.jobbook.commons.Urls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/17.
 */
public class FeedBackModelImpl implements FeedBackModel {

    @Override
    public void feedBack(String mailAddress, String content, final OnFeedBackListener listener) {
        //是否建立一个新的bean
//        String feedback = MyApplication.getmPersonBean().getAccount() + ";" + mailAddress + ";" + content;
        OkHttpUtils.postString().url(Urls.FEED_BACK_URL).content("").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onFailure();
            }

            @Override
            public void onResponse(String response, int id) {
                listener.onSuccess();
            }
        });
    }

    public interface OnFeedBackListener {
        void onSuccess();
        void onFailure();
    }
}
