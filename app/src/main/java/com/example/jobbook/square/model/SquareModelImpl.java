package com.example.jobbook.square.model;


import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.util.L;

import com.example.jobbook.bean.ResultBean;
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
public class SquareModelImpl implements SquareModel {

    @Override
    public void loadSquares(int pageIndex, String name, final OnLoadSquaresListListener listener) {
        L.i("square_response:", "load");
        OkHttpUtils.postString().url(Urls.SQUARE_URL + "/account/" + name).content(pageIndex + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                L.i("square_response:", e.getMessage());
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("square_response", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    List<MomentBean> momentBeanList = new Gson().fromJson(resultBean.getResponse(),
                            new TypeToken<List<MomentBean>>(){}.getType());
                    listener.onSuccess(momentBeanList);
                } else {
                    listener.onFailure(resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    @Override
    public void like(int squareId, final OnLikeSquareListener listener) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            listener.onLikeSquareNoLoginError();
        } else {
            account = MyApplication.getAccount();
        }
        OkHttpUtils.get().url(Urls.SQUARE_LIKE_URL + squareId + "/account/" + account).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onLikeSquareFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    listener.onLikeSuccess();
                } else {
                    listener.onLikeSquareFailure(resultBean.getResponse(), null);
                }
            }
        });
    }

    @Override
    public void unlike(int squareId, final OnUnlikeSquareListener listener) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 0) {
            listener.onUnlikeSquareNoLoginError();
        } else {
            account = MyApplication.getAccount();
        }
        OkHttpUtils.get().url(Urls.SQUARE_UNLIKE_URL + squareId + "/account/" + account).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onUnlikeSquareFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("square_unlike", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    listener.onUnlikeSuccess();
                } else {
                    listener.onUnlikeSquareFailure(resultBean.getResponse(), null);
                }
            }
        });
    }

    public interface OnLoadSquaresListListener {
        void onSuccess(List<MomentBean> list);
        void onFailure(String msg, Exception e);
    }

    public interface OnLikeSquareListener {
        void onLikeSuccess();
        void onLikeSquareFailure(String msg, Exception e);
        void onLikeSquareNoLoginError();
    }

    public interface OnUnlikeSquareListener {
        void onUnlikeSuccess();
        void onUnlikeSquareFailure(String msg, Exception e);
        void onUnlikeSquareNoLoginError();
    }
}
