package com.example.jobbook.job.model;


import android.text.TextUtils;

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
 * Created by Xu on 2016/7/6.
 */
public class JobModelImpl implements JobModel{

    @Override
    public void loadJobs(int pageIndex, boolean isRecommend, String type, String location, final OnLoadJobListListener listener) {
        StringBuilder url = new StringBuilder();
        if (isRecommend) {
            url.append(Urls.JOB_URL);
        } else {
            url.append(Urls.JOB_HEADER_SEARCH_URL);
            if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(location)) {
                url.append("/type/" + type + "/location/" + location);
            } else if (!TextUtils.isEmpty(type)) {
                url.append("/type/" + type);
            } else if (!TextUtils.isEmpty(location)) {
                url.append("/location/" + location);
            }
        }
        L.i("job_response:", "isRecommend:" + isRecommend + " type:" + type + " location:" + location);
        L.i("job_response_url:", "url:" + url.toString());
        L.i("job_response:", "pageIndex:" + pageIndex);
        OkHttpUtils.postString().url(url.toString()).content(String.valueOf(pageIndex)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                L.i("job_response:", e.getMessage());
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("job_response:", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (resultBean.getStatus().equals("true")) {
                    List<JobBean> jobBeanList = new Gson().fromJson(resultBean.getResponse(), new TypeToken<List<JobBean>>(){}.getType());
                    listener.onSuccess(jobBeanList);
                } else {
                    listener.onFailure(resultBean.getResponse(), new Exception());
                }
            }
        });
    }

    public interface OnLoadJobListListener {
        void onSuccess(List<JobBean> list);
        void onFailure(String msg, Exception e);
    }
}
