package com.example.jobbook.moment.model;

import com.example.jobbook.util.L;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewMomentModelImpl implements NewMomentModel {
    @Override
    public void newmoment(MomentBean momentBean, final OnNewMomentListener listener) {
        OkHttpUtils.postString().url(Urls.NEW_QUESTION_URL).content(new Gson().toJson(momentBean)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                L.i("new_question", "network error");
                listener.onFailure();
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("new_question", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    listener.onSuccess();
                } else {
                    listener.onFailure();
                }
            }
        });
    }

    public interface OnNewMomentListener {
        void onSuccess();
        void onFailure();
    }
}
