package com.example.jobbook.job.presenter;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.job.model.JobDetailModel;
import com.example.jobbook.job.model.JobDetailModelImpl;
import com.example.jobbook.job.view.JobDetailView;

/**
 * Created by 椰树 on 2016/8/28.
 */
public class JobDetailPresenterImpl implements JobDetailPresenter, JobDetailModelImpl.OnLoadJobListener,
        JobDetailModelImpl.OnLikeJobListener{

    private JobDetailModel mJobDetailModel;
    private JobDetailView mJobDetailView;

    public JobDetailPresenterImpl(JobDetailView mJobDetailView){
        this.mJobDetailView = mJobDetailView;
        mJobDetailModel = new JobDetailModelImpl();
    }
    @Override
    public void loadJob(String jobId) {
        mJobDetailModel.loadJobDetail(jobId,this);
    }

    @Override
    public void like(String jobId) {
        mJobDetailModel.like(jobId, this);
    }

    @Override
    public void onSuccess(JobBean mJobBean) {
        mJobDetailView.addJob(mJobBean);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String msg, Exception e) {
        mJobDetailView.hideProgress();
        mJobDetailView.showLoadFailMsg();
    }
}
