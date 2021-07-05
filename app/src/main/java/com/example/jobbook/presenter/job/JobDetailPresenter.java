package com.example.jobbook.presenter.job;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.job.JobDetailContract;
import com.example.jobbook.model.bean.JobDetailBean;
import com.example.jobbook.model.http.RetrofitService;

/**
 * Created by zhaoxuzhang on 2018/1/2.
 */

public class JobDetailPresenter extends RxPresenter<JobDetailContract.View> implements JobDetailContract.Presenter {

    public JobDetailPresenter(JobDetailContract.View view) {
        attach(view);
    }

    @Override
    public void loadJob(String jobId) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        addSubscribe(RetrofitService.getJobDetail(jobId, account)
                .subscribeWith(new BaseSubscriber<JobDetailBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(JobDetailBean jobDetailBean) {
                        mView.addJob(jobDetailBean);
                    }
                }));
    }

    @Override
    public void like(String jobId) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        mView.showProgress();
        if (account == null) {
            mView.NoLoginError();
            mView.hideProgress();
            return;
        }
        addSubscribe(RetrofitService.likeJob(jobId, account)
                .subscribeWith(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.likeSuccess();
                    }
                }));
    }

    @Override
    public void unlike(String jobId) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        mView.showProgress();
        if (account == null) {
            mView.NoLoginError();
            mView.hideProgress();
            return;
        }
        addSubscribe(RetrofitService.unlikeJob(jobId, account)
                .subscribeWith(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.unlikeSuccess();
                    }
                }));
    }

    @Override
    public void sendCV(String companyId) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        mView.showProgress();
        if (account == null) {
            mView.NoLoginError();
            mView.hideProgress();
            return;
        }
        addSubscribe(RetrofitService.sendCV(account, companyId)
                .subscribeWith(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.sendCVSuccess();
                    }
                }));
    }
}
