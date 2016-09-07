package com.example.jobbook.job.presenter;

import com.example.jobbook.bean.JobDetailBean;
import com.example.jobbook.job.model.JobDetailModel;
import com.example.jobbook.job.model.JobDetailModelImpl;
import com.example.jobbook.job.view.JobDetailView;

/**
 * Created by 椰树 on 2016/8/28.
 */
public class JobDetailPresenterImpl implements JobDetailPresenter, JobDetailModelImpl.OnLoadJobListener,
        JobDetailModelImpl.OnLikeJobListener, JobDetailModelImpl.OnUnlikeJobListener ,JobDetailModelImpl.OnSendCVListener{

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
    public void unlike(String jobId) {
        mJobDetailModel.unlike(jobId, this);
    }

    @Override
    public void sendCV(String jobId) {
        mJobDetailModel.sendCV(jobId, this);
    }

    @Override
    public void onSuccess(JobDetailBean jobDetailBean) {
        mJobDetailView.addJob(jobDetailBean);
    }

    @Override
    public void onLoadJobFailure(String msg, Exception e) {
        mJobDetailView.hideProgress();
        mJobDetailView.showLoadFailMsg();
    }

    @Override
    public void onUnlikeSuccess() {
        mJobDetailView.unlikeSuccess();
    }

    @Override
    public void onUnlikeJobFailure(String msg, Exception e) {
        mJobDetailView.hideProgress();
        mJobDetailView.unlikeError();
    }

    @Override
    public void onUnlikeJobNoLoginError() {
        mJobDetailView.NoLoginError();
    }

    @Override
    public void onLikeSuccess() {
        mJobDetailView.likeSuccess();
    }

    @Override
    public void onLikeJobFailure(String msg, Exception e) {
        mJobDetailView.hideProgress();
        mJobDetailView.likeError();
    }

    @Override
    public void onLikeJobNoLoginError() {
        mJobDetailView.NoLoginError();
    }

    @Override
    public void onSendCVSuccess() {
        mJobDetailView.sendCVSuccess();
    }

    @Override
    public void onSendCVFailure(String msg, Exception e) {
        mJobDetailView.sendCVFailure();
    }

    @Override
    public void onSendCVNoLoginError() {
        mJobDetailView.NoLoginError();
    }
}
