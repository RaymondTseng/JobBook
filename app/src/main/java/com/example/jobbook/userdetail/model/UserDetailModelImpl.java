package com.example.jobbook.userdetail.model;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
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
        OkHttpUtils.get().url(Urls.USER_DETAIL_FOLLOW_SB_URL + myAccount + "/hisAccount/" +
                hisAccount).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int i) {
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if(resultBean.getStatus().equals("true")){
                    listener.onSuccess();
                }else{
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
                listener.onLoadFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int i) {
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if(resultBean.getStatus().equals("true")){
                    PersonBean personBean = new Gson().fromJson(resultBean.getResponse(), PersonBean.class);
                    listener.onSuccess(personBean);
                }else{
                    listener.onLoadFailure("加载失败", new Exception());
                }
            }
        });
    }

    public interface OnFollowListener{
        void onSuccess();
        void onFailure(String msg, Exception e);
    }

    public interface OnLoadUserDetailByAccountListener {
        void onSuccess(PersonBean personBean);
        void onLoadFailure(String msg, Exception e);
    }
}
