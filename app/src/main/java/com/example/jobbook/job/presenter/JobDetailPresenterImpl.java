package com.example.jobbook.job.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.JobDetailBean;
import com.example.jobbook.job.model.JobDetailModel;
import com.example.jobbook.job.model.JobDetailModelImpl;
import com.example.jobbook.job.view.JobDetailView;
import com.example.jobbook.network.RetrofitService;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by 椰树 on 2016/8/28.
 */
public class JobDetailPresenterImpl implements JobDetailPresenter, JobDetailModelImpl.OnLoadJobListener,
        JobDetailModelImpl.OnLikeJobListener, JobDetailModelImpl.OnUnlikeJobListener, JobDetailModelImpl.OnSendCVListener {

    private JobDetailModel mJobDetailModel;
    private JobDetailView mJobDetailView;

    public JobDetailPresenterImpl(JobDetailView mJobDetailView) {
        this.mJobDetailView = mJobDetailView;
        mJobDetailModel = new JobDetailModelImpl();
    }

    @Override
    public void loadJob(String jobId) {
//        mJobDetailView.showProgress();
//        mJobDetailModel.loadJobDetail(jobId, this);
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        RetrofitService.getJobDetail(jobId, account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mJobDetailView.showProgress();
                    }
                })
                .subscribe(new Subscriber<JobDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mJobDetailView.hideProgress();
                        mJobDetailView.showLoadFailMsg();
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(JobDetailBean jobDetailBean) {
                        mJobDetailView.addJob(jobDetailBean);
                        mJobDetailView.hideProgress();
                    }
                });
    }

    @Override
    public void like(String jobId) {
//        mJobDetailModel.like(jobId, this);
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        mJobDetailView.showProgress();
        if (account == null) {
            mJobDetailView.NoLoginError();
            mJobDetailView.hideProgress();
            return;
        }
        RetrofitService.likeJob(jobId, account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mJobDetailView.showProgress();
                    }
                })
                .subscribe(new Subscriber<ResultBean<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mJobDetailView.hideProgress();
                        mJobDetailView.showLoadFailMsg();
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(ResultBean<String> resultBean) {
                        if (resultBean.getStatus().equals("true")) {
                            mJobDetailView.hideProgress();
                            mJobDetailView.likeSuccess();
                        } else {
                            mJobDetailView.hideProgress();
                            mJobDetailView.likeError();
                        }
                    }
                });
    }

    @Override
    public void unlike(String jobId) {
//        mJobDetailModel.unlike(jobId, this);
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        mJobDetailView.showProgress();
        if (account == null) {
            mJobDetailView.NoLoginError();
            mJobDetailView.hideProgress();
            return;
        }
        RetrofitService.unlikeJob(jobId, account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mJobDetailView.showProgress();
                    }
                })
                .subscribe(new Subscriber<ResultBean<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mJobDetailView.hideProgress();
                        mJobDetailView.showLoadFailMsg();
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(ResultBean<String> resultBean) {
                        if (resultBean.getStatus().equals("true")) {
                            mJobDetailView.hideProgress();
                            mJobDetailView.unlikeSuccess();
                        } else {
                            mJobDetailView.hideProgress();
                            mJobDetailView.unlikeError();
                        }
                    }
                });
    }

    @Override
    public void sendCV(String companyId) {
        mJobDetailView.showProgress();
        mJobDetailModel.sendCV(companyId, this);
    }

    @Override
    public void onSuccess(JobDetailBean jobDetailBean) {
        mJobDetailView.addJob(jobDetailBean);
        mJobDetailView.hideProgress();
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
        mJobDetailView.hideProgress();
        mJobDetailView.NoLoginError();
    }

    @Override
    public void onSendCVSuccess() {
        mJobDetailView.hideProgress();
        mJobDetailView.sendCVSuccess();
    }

    @Override
    public void onSendCVFailure(String msg, Exception e) {
        mJobDetailView.hideProgress();
        mJobDetailView.sendCVFailure();
    }

    @Override
    public void onSendCVNoLoginError() {
        mJobDetailView.hideProgress();
        mJobDetailView.NoLoginError();
    }

    @Override
    public void onSendCVEmailFailed() {
        mJobDetailView.hideProgress();
        mJobDetailView.sendCVEmailFailed();
    }

    @Override
    public void onSendCVNoDestination() {
        mJobDetailView.hideProgress();
        mJobDetailView.sendCVNoDestination();
    }

    @Override
    public void onSendCVRepeated() {
        mJobDetailView.hideProgress();
        mJobDetailView.sendCVRepeated();
    }

    @Override
    public void onSendCVNoWrite() {
        mJobDetailView.hideProgress();
        mJobDetailView.sendCVNoWrite();
    }
}
