package com.example.jobbook.square.model;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;
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
 * Created by root on 16-11-23.
 */

public class SquareDetailModelImpl implements SquareDetailModel {

    @Override
    public void loadSquareComments(int id, final OnLoadSquareDetailCommentListener listener) {
        L.i("squaredetail", "load");
        OkHttpUtils.get().url(Urls.SQUARE_DETAIL_URL + id).addParams("","").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onFailure("netword error", e);
            }

            @Override
            public void onResponse(String response, int i) {
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if(resultBean.getStatus().equals("true")){
                    List<MomentCommentBean> list = new Gson().fromJson(resultBean.getResponse(),
                            new TypeToken<List<MomentCommentBean>>(){}.getType());
                    listener.onSuccess(list);
                }else{
                    listener.onFailure(resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    public interface OnLoadSquareDetailCommentListener{
        void onSuccess(List<MomentCommentBean> list);
        void onFailure(String msg, Exception e);
    }
}
