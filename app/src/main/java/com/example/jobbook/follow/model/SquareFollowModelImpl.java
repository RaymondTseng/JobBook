package com.example.jobbook.follow.model;


import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.square.model.SquareModelImpl;
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
public class SquareFollowModelImpl implements SquareFollowModel {

    @Override
    public void loadSquareFollows(int pageIndex, final OnLoadSquareFollowListListener listener) {
        String account = "";
        if(MyApplication.getmLoginStatus() == 0){
            listener.onFailure("请先登录!", new Exception());
            return;
        }else{
            account = MyApplication.getAccount();
        }
        OkHttpUtils.postString().url(Urls.SQUARE_FOLLOW_URL + "/account/" + account).content(pageIndex + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                L.i("square_follow_response:", e.getMessage());
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("square_follow_response", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    List<MomentBean> momentBeanList = new Gson().fromJson((String)resultBean.getResponse(), new TypeToken<List<MomentBean>>() {
                    }.getType());
                    listener.onSuccess(momentBeanList);
                } else {
                    listener.onFailure((String)resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    @Override
    public void like(int squareId, final int position, final SquareModelImpl.OnLikeSquareListener listener) {
        String account = "";
        if(MyApplication.getmLoginStatus() == 0){
            listener.onLikeSquareNoLoginError();
            return;
        }else{
            account = MyApplication.getAccount();
        }
        OkHttpUtils.get().url(Urls.SQUARE_LIKE_URL + squareId + "/account/" + account).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onLikeSquareFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("follow_like", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    MomentBean momentBean = new Gson().fromJson((String)resultBean.getResponse(), MomentBean.class);
                    listener.onLikeSuccess(momentBean, position);
                } else {
                    listener.onLikeSquareFailure((String)resultBean.getResponse(), null);
                }
            }
        });
    }

    @Override
    public void unlike(int squareId, final int position, final SquareModelImpl.OnUnlikeSquareListener listener) {
        String account = "";
        if(MyApplication.getmLoginStatus() == 0){
            listener.onUnlikeSquareNoLoginError();
            return;
        }else{
            account = MyApplication.getAccount();
        }
        OkHttpUtils.get().url(Urls.SQUARE_UNLIKE_URL + squareId + "/account/" + account).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onUnlikeSquareFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("follow_unlike", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    MomentBean momentBean = new Gson().fromJson((String)resultBean.getResponse(), MomentBean.class);
                    listener.onUnlikeSuccess(momentBean, position);
                } else {
                    listener.onUnlikeSquareFailure((String)resultBean.getResponse(), null);
                }
            }
        });
    }

    public interface OnLoadSquareFollowListListener {
        void onSuccess(List<MomentBean> list);

        void onFailure(String msg, Exception e);
    }
}
