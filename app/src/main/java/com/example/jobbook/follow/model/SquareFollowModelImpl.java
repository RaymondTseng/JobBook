package com.example.jobbook.follow.model;


import com.example.jobbook.bean.MomentBean;
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
public class SquareFollowModelImpl implements SquareFollowModel {

    @Override
    public void loadSquareFollows(int pageIndex, final OnLoadSquareFollowListListener listener) {
        L.i("square_response:", "load");
        OkHttpUtils.postString().url(Urls.SQUARE_URL).content(pageIndex + "").build().execute(new StringCallback() {
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
                    List<MomentBean> momentBeanList = new Gson().fromJson(resultBean.getResponse(), new TypeToken<List<MomentBean>>(){}.getType());
                    listener.onSuccess(momentBeanList);
                } else {
                    listener.onFailure(resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    public interface OnLoadSquareFollowListListener {
        void onSuccess(List<MomentBean> list);
        void onFailure(String msg, Exception e);
    }
}
