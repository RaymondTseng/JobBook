package com.example.jobbook.person.model;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.PersonBean;
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
 * Created by Xu on 2016/12/8.
 */

public class ShowMomentListModelImpl implements ShowMomentListModel {

    @Override
    public void loadMomentList(String hisAccount, String myAccount, final OnLoadMomentListListener listener) {
        OkHttpUtils.get().url(Urls.USER_DETAIL_MOMENT_URL + hisAccount + "/my/" + myAccount).build().
                execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                L.i("showmomentlist", "error");
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int i) {
                L.i("showmomentlist", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    List<MomentBean> list = new Gson().fromJson(resultBean.getResponse(),
                            new TypeToken<List<MomentBean>>() {
                            }.getType());
                    listener.onSuccess(list);
                } else {
                    listener.onFailure(resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    public interface OnLoadMomentListListener {
        void onSuccess(List<MomentBean> list);

        void onFailure(String msg, Exception e);
    }
}
