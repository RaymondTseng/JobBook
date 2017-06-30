package com.example.jobbook.message.model;

import com.example.jobbook.bean.MessageBean;
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
 * Created by Xu on 2016/12/9.
 */

public class GetMessageModelImpl implements GetMessageModel {

    @Override
    public void getMessages(String account, final OnGetMessageListener listener) {
        OkHttpUtils.get().url(Urls.GET_MESSAGE_URL + "/account/" + account).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onFailure(e.getMessage(), e);
            }

            @Override
            public void onResponse(String response, int i) {
                L.i("get message response", "result:" + response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    List<MessageBean> lists = new Gson().fromJson(resultBean.getResponse(), new TypeToken<List<MessageBean>>(){}.getType());
                    listener.onSuccess(lists);
                } else {
                    listener.onFailure(resultBean.getResponse(), null);
                }
            }
        });

    }

    public interface OnGetMessageListener {
        void onSuccess(List<MessageBean> lists);
        void onFailure(String msg, Exception e);
    }
}
