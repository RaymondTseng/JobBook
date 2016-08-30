package com.example.jobbook.question.model;


import android.util.Log;

import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/5.
 */
public class QuestionModelImpl implements QuestionModel {

    @Override
    public void loadQuestions(final OnLoadQuestionsListListener listener) {
        Log.i("question_response:", "load");
        OkHttpUtils.get().url(Urls.QUESTION_URL).addParams("", "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("question_response:", e.getMessage());
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("question_response", response);
                List<QuestionBean> questionBeanList = new Gson().fromJson(response, new TypeToken<List<QuestionBean>>(){}.getType());
                listener.onSuccess(questionBeanList);
            }
        });
    }

    public interface OnLoadQuestionsListListener {
        void onSuccess(List<QuestionBean> list);
        void onFailure(String msg, Exception e);
    }
}
