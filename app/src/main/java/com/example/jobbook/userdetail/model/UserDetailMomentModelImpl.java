package com.example.jobbook.userdetail.model;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by root on 16-11-28.
 */

public class UserDetailMomentModelImpl implements  UserDetailMomentModel{

    @Override
    public void loadUserDetailMoments(String hisAccount, String myAccount,
                                      final OnLoadUserDetailMomentListener listener) {
        OkHttpUtils.get().url(Urls.USER_DETAIL_MOMENT_URL + hisAccount + "/my/" + myAccount)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onFailure("netword error", e);
            }

            @Override
            public void onResponse(String response, int i) {
                L.i("user_detail_moment_load", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if(resultBean.getStatus().equals("true")){
                    List<MomentBean> list = new Gson().fromJson(resultBean.getResponse(),
                            new TypeToken<List<MomentBean>>(){}.getType());
                    listener.onSuccess(list);
                }else{
                    listener.onFailure("netword error", new Exception());
                }
            }
        });
    }

    public interface OnLoadUserDetailMomentListener{
        void onSuccess(List<MomentBean> list);
        void onFailure(String msg, Exception e);
    }
}
