package com.example.jobbook.question.model;


import com.example.jobbook.util.L;

import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
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
    public void loadQuestions(int pageIndex, final OnLoadQuestionsListListener listener) {
        L.i("question_response:", "load");
        OkHttpUtils.postString().url(Urls.QUESTION_URL).content(pageIndex + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                L.i("question_response:", e.getMessage());
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("question_response", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    List<QuestionBean> questionBeanList = new Gson().fromJson(resultBean.getResponse(), new TypeToken<List<QuestionBean>>(){}.getType());
                    listener.onSuccess(questionBeanList);
                } else {
                    listener.onFailure(resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    public interface OnLoadQuestionsListListener {
        void onSuccess(List<QuestionBean> list);
        void onFailure(String msg, Exception e);
    }
}
