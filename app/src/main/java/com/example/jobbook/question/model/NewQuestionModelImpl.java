package com.example.jobbook.question.model;

import android.util.Log;

import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewQuestionModelImpl implements NewQuestionModel {
    @Override
    public void newquestion(QuestionBean questionBean, final OnNewQuestionListener listener) {
        OkHttpUtils.postString().url(Urls.NEW_QUESTION_URL).content(new Gson().toJson(questionBean)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("new_question", "network error");
                listener.onFailure();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("new_question", response);
                listener.onSuccess();
            }
        });
    }

    public interface OnNewQuestionListener {
        void onSuccess();
        void onFailure();
    }
}
