package com.example.jobbook.userdetail.model;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by root on 16-11-30.
 */

public class UserDetailFollowModelImpl implements UserDetailFollowModel {

    @Override
    public void loadFollow(String account, final OnLoadFollowListener listener) {
        OkHttpUtils.get().url(Urls.USER_DETAIL_FOLLOW_URL + account).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onFailure("网络异常!" , e);
            }

            @Override
            public void onResponse(String response, int i) {
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if(resultBean.getStatus().equals("true")){
                    List<TypePersonBean> list = new Gson().fromJson(resultBean.getResponse(),
                            new TypeToken<List<TypePersonBean>>(){}.getType());
                    L.i("user_detail_follow", response);
                    listener.onSuccess(list);
                }else{
                    listener.onFailure(resultBean.getResponse(), new Exception());
                }
            }
        });

    }

    @Override
    public void follow(String myAccount, String hisAccount, final OnFollowListener listener) {
        OkHttpUtils.get().url(Urls.USER_DETAIL_FOLLOW_SB_URL + myAccount + "/hisAccount/" +
                hisAccount).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onFailure("网络错误!", e);
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

    public interface OnLoadFollowListener{
        void onSuccess(List<TypePersonBean> mFollow);
        void onFailure(String msg, Exception e);
    }

    public interface OnFollowListener{
        void onSuccess();
        void onFailure(String msg, Exception e);
    }


}
