package com.example.jobbook.person.model;

import com.example.jobbook.MyApplication;
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
 * Created by Xu on 2016/12/8.
 */

public class ShowFollowerListModelImpl implements ShowFollowerListModel {

    @Override
    public void loadFollowerList(String account , final OnLoadFollowerListListener listener) {
        String myAccount = MyApplication.getAccount() == null ? "" : MyApplication.getAccount();
        OkHttpUtils.get().url(Urls.USER_DETAIL_FOLLOW_URL + account + "/my/" + myAccount).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                L.i("showfollowlist", "error");
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int i) {
                L.i("showfollowlist", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    List<TypePersonBean> list = new Gson().fromJson((String)resultBean.getResponse(),
                            new TypeToken<List<TypePersonBean>>() {
                            }.getType());
                    listener.onSuccess(list);
                } else {
                    listener.onFailure((String)resultBean.getResponse(), new Exception());
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

    public interface OnLoadFollowerListListener {
        void onSuccess(List<TypePersonBean> list);

        void onFailure(String msg, Exception e);
    }

    public interface OnFollowListener{
        void onSuccess();

        void onFailure(String msg, Exception e);
    }
}
