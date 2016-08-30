package com.example.jobbook.job.presenter;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.job.model.JobModel;
import com.example.jobbook.job.model.JobModelImpl;
import com.example.jobbook.job.view.JobView;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class JobPresenterImpl implements JobPresenter, JobModelImpl.OnLoadJobListListener {

    private JobView mJobView;
    private JobModel mJobModel;

    public JobPresenterImpl(JobView view) {
        mJobView = view;
        mJobModel = new JobModelImpl();
    }

    @Override
    public void loadJobs() {
        mJobView.showProgress();
        mJobModel.loadJobs(this);
    }

    @Override
    public void search() {
        mJobView.search();
    }

    @Override
    public void onSuccess(List<JobBean> list) {
        mJobView.hideProgress();
        mJobView.addJobs(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mJobView.hideProgress();
        mJobView.showLoadingFailMsg();
    }
}
