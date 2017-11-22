package com.example.jobbook.job.presenter;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.JobDetailBean;
import com.example.jobbook.job.view.JobDetailView;
import com.example.jobbook.base.BaseObserver;
import com.example.jobbook.model.http.RetrofitService;

/**
 * Created by 椰树 on 2016/8/28.
 */
public class JobDetailPresenterImpl implements JobDetailPresenter {

    private JobDetailView mJobDetailView;

    public JobDetailPresenterImpl(JobDetailView mJobDetailView) {
        this.mJobDetailView = mJobDetailView;
    }

    @Override
    public void loadJob(String jobId) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        RetrofitService.getJobDetail(jobId, account)
                .subscribe(new BaseObserver<JobDetailBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mJobDetailView;
                    }

                    @Override
                    public void onNext(JobDetailBean jobDetailBean) {
                        mJobDetailView.addJob(jobDetailBean);
                    }
                });
    }

    @Override
    public void like(String jobId) {
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
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mJobDetailView;
                    }

                    @Override
                    public void onNext(String s) {
                        mJobDetailView.likeSuccess();
                    }
                });
    }

    @Override
    public void unlike(String jobId) {
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
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mJobDetailView;
                    }

                    @Override
                    public void onNext(String s) {
                        mJobDetailView.unlikeSuccess();
                    }
                });
    }

    @Override
    public void sendCV(String companyId) {
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
        RetrofitService.sendCV(account, companyId)
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mJobDetailView;
                    }

                    @Override
                    public void onNext(String s) {
                        mJobDetailView.sendCVSuccess();
                    }
                });
    }

}
