package com.example.jobbook.question.model;

import com.example.jobbook.util.L;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.bean.QuestionCommentBean;
import com.example.jobbook.bean.QuestionDetailBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class QuestionDetailModelImpl implements QuestionDetailModel {
    private static int LOAD_QUESTION_ERROR = 0;
    private static int LOAD_QUESTION_COMMENTS_ERROR = 1;
    private static int SEND_COMMENT_ERROR = 2;

    @Override
    public void loadQuestionComments(int id, final OnLoadQuestionCommentsListener mListener) {
        OkHttpUtils.postString().url(Urls.QUESTION_DETAIL_URL + id).
                content(String.valueOf(id)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mListener.onFailure("network error", e, LOAD_QUESTION_COMMENTS_ERROR);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("questiondetail_response", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    List<QuestionCommentBean> list = new Gson().fromJson(resultBean.getResponse(), new TypeToken<List<QuestionCommentBean>>() {
                    }.getType());
                    mListener.onSuccess(list);
                } else {
                    mListener.onFailure(resultBean.getResponse(), new Exception(), id);
                }
            }
        });
    }

    @Override
    public void loadQuestion(QuestionBean questionBean, OnLoadQuestionListener mListener) {
        if (questionBean != null) {
            mListener.onSuccess(questionBean);
        } else {
            mListener.onFailure("network error", new Exception(), LOAD_QUESTION_ERROR);
        }
    }

    @Override
    public void sendComment(QuestionCommentBean questionCommentBean, final OnSendQuestionCommentListener mListener) {
        OkHttpUtils.postString().url(Urls.SEND_QUESTION_COMMENT_URL).content(new Gson().
                toJson(questionCommentBean)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mListener.onFailure("network error", e, SEND_COMMENT_ERROR);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("question_send_comment", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    mListener.onSuccess();
                } else {
                    mListener.onFailure(resultBean.getResponse(), new Exception(), SEND_COMMENT_ERROR);
                }
            }
        });
    }

    @Override
    public void commentLike(int com_id, String account, final OnLikeListener listener) {
        OkHttpUtils.get().url(Urls.COMMENT_LIKE_AND_UNLIKE + "comment_id/" + com_id + "/account/" + account + "/type/1").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onLikeFailure(e.getMessage(), e, 0);
            }

            @Override
            public void onResponse(String response, int i) {
                L.i("comment_like", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    String[] array = resultBean.getResponse().split("/");
                    L.i("comment_like_result", "good" + array[0] + " bad:" + array[1]);
                    listener.onLikeSuccess(Integer.valueOf(array[0]), Integer.valueOf(array[1]));
                } else {
                    listener.onLikeFailure(resultBean.getResponse(), null, 0);
                }
            }
        });
    }

    @Override
    public void commentUnlike(int com_id, String account, final OnUnlikeListener listener) {
        OkHttpUtils.get().url(Urls.COMMENT_LIKE_AND_UNLIKE + "comment_id/" + com_id + "/account/" + account + "/type/0").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onUnlikeFailure(e.getMessage(), e, 0);
            }

            @Override
            public void onResponse(String response, int i) {
                L.i("comment_unlike", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    String[] array = resultBean.getResponse().split("/");
                    L.i("comment_like_result", "good" + array[0] + " bad:" + array[1]);
                    listener.onUnlikeSuccess(Integer.valueOf(array[0]), Integer.valueOf(array[1]));
                } else {
                    listener.onUnlikeFailure(resultBean.getResponse(), null, 0);
                }
            }
        });
    }

    public interface OnLoadQuestionListener {
        void onSuccess(QuestionBean mQuestion);

        void onFailure(String msg, Exception e, int error);
    }

    public interface OnLoadQuestionCommentsListener {
        void onSuccess(List<QuestionCommentBean> mComments);

        void onFailure(String msg, Exception e, int error);
    }

    public interface OnSendQuestionCommentListener {
        void onSuccess();

        void onFailure(String msg, Exception e, int error);
    }

    public interface OnLikeListener {
        void onLikeSuccess(int num_like, int num_unlike);

        void onLikeFailure(String msg, Exception e, int error);
    }

    public interface OnUnlikeListener {
        void onUnlikeSuccess(int num_like, int num_unlike);

        void onUnlikeFailure(String msg, Exception e, int error);
    }
}
