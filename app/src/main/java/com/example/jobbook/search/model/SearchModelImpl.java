package com.example.jobbook.search.model;

import com.example.jobbook.bean.JobBean;
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
 * Created by Xu on 2016/8/31.
 */
public class SearchModelImpl implements SearchModel {

    public static final int JOB_LOCATION = 0;
    public static final int JOB_TYPE = 1;

    @Override
    public void search(int type, String content, int pageIndex, final OnSearchListener listener) {
        StringBuilder url = new StringBuilder();
        if (type == JOB_TYPE) {
            url.append(Urls.JOB_HEADER_SEARCH_URL + "/type/" + content);
        } else if (type == JOB_LOCATION) {
            url.append(Urls.JOB_HEADER_SEARCH_URL + "/location/" + content);
        }
        L.i("search:", url.toString());
        OkHttpUtils.postString().content(String.valueOf(pageIndex)).url(url.toString()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onSearchFaliure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("response:", "result:" + response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    if (resultBean.getResponse() != null) {
                        List<JobBean> list = new Gson().fromJson(resultBean.getResponse(), new TypeToken<List<JobBean>>() {
                        }.getType());
                        listener.onSuccess(list);
                    } else {
                        listener.onSearchEmpty("", new Exception());
                    }
                } else {
                    listener.onSearchFaliure(resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    public interface OnSearchListener {
        void onSuccess(List<JobBean> list);

        void onSearchFaliure(String msg, Exception e);

        void onSearchEmpty(String msg, Exception e);
    }
}
