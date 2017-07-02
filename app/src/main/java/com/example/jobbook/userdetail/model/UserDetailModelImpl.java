package com.example.jobbook.userdetail.model;

import com.example.jobbook.MyApplication;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by root on 16-12-5.
 */

public class UserDetailModelImpl implements UserDetailModel {

    @Override
    public void follow(String myAccount, String hisAccount, final OnFollowListener listener) {
        OkHttpUtils.get().url(Urls.USER_DETAIL_FOLLOW_SB_URL + myAccount + "/you/" +
                hisAccount).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onFailure("网络错误", e);
            }

            @Override
            public void onResponse(String response, int i) {
                L.i("userdetail_follow", response); // !!!
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    listener.onSuccess();
                } else {
                    listener.onFailure("关注失败", new Exception());
                }
            }
        });
    }

    @Override
    public void loadUserDetailByAccount(String account, final OnLoadUserDetailByAccountListener listener) {
        OkHttpUtils.get().url(Urls.GET_USER_DETAIL_BY_ACCOUNT_URL + account).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onLoadFailure("网络错误", e);
            }

            @Override
            public void onResponse(String response, int i) {
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    TypePersonBean personBean = new Gson().fromJson((String)resultBean.getResponse(), TypePersonBean.class);
                    L.i("userdetailimpl", personBean.toString());
                    listener.onSuccess(personBean);
                } else {
                    listener.onLoadFailure("加载失败", new Exception());
                }
            }
        });
    }

    @Override
    public void unFollow(String myAccount, String hisAccount, final OnUnFollowListener listener) {
        OkHttpUtils.get().url(Urls.USER_DETAIL_UNFOLLOW_SB_URL + myAccount + "/you/" +
                hisAccount).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onFailure("网络错误", e);
            }

            @Override
            public void onResponse(String response, int i) {
                L.i("userdetail_unfollow", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    listener.onUnfollowSuccess();
                } else {
                    listener.onFailure("取消关注失败", new Exception());
                }
            }
        });
    }

    @Override
    public void refreshPersonBean(final OnRefreshListener listener) {
        OkHttpUtils.get().url(Urls.GET_USER_DETAIL_BY_ACCOUNT_URL + MyApplication.getAccount()).
                build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onFailure("网络错误", e);
            }

            @Override
            public void onResponse(String response, int i) {
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    TypePersonBean personBean = new Gson().fromJson((String)resultBean.getResponse(), TypePersonBean.class);
                    listener.onRefreshSuccess(personBean);
                } else {
                    listener.onFailure("加载失败", new Exception());
                }
            }
        });
    }

    public interface OnFollowListener {
        void onSuccess();

        void onFailure(String msg, Exception e);
    }

    public interface OnLoadUserDetailByAccountListener {
        void onSuccess(TypePersonBean personBean);

        void onLoadFailure(String msg, Exception e);
    }

    public interface OnUnFollowListener {
        void onUnfollowSuccess();

        void onFailure(String msg, Exception e);
    }

    public interface OnRefreshListener {
        void onRefreshSuccess(TypePersonBean personBean);

        void onFailure(String msg, Exception e);
    }
}
