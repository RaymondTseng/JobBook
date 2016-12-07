package com.example.jobbook.userdetail.model;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by root on 16-11-30.
 */

public class UserDetailFansModelImpl implements UserDetailFansModel{

    @Override
    public void loadFans(String account, final OnLoadFansListener listener) {
        OkHttpUtils.get().url(Urls.USER_DETAIL_FANS_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int i) {
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if(resultBean.getStatus().equals("true")){
                    List<PersonBean> list = new Gson().fromJson(resultBean.getResponse(),
                            new TypeToken<List<PersonBean>>(){}.getType());
                    listener.onSuccess(list);
                }else{
                    listener.onFailure(resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    public interface OnLoadFansListener{
        void onSuccess(List<PersonBean> mFans);
        void onFailure(String msg, Exception e);
    }
}
