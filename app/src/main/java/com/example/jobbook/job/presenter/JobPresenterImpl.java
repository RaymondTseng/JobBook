package com.example.jobbook.job.presenter;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.job.model.JobModelImpl;
import com.example.jobbook.job.view.JobView;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class JobPresenterImpl implements JobPresenter, JobModelImpl.OnLoadJobListListener {

    private JobView mJobView;

    public JobPresenterImpl(JobView view) {
        mJobView = view;
    }

    @Override
    public void loadJobs() {

    }

    @Override
    public void onSuccess(List<JobBean> list) {

    }

    @Override
    public void onFailure(String msg, Exception e) {

    }
}
