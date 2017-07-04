package com.example.jobbook.job.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.JobDetailBean;
import com.example.jobbook.job.view.JobDetailView;
import com.example.jobbook.network.RetrofitService;

import rx.Subscriber;
import rx.functions.Action0;

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
//        mJobDetailView.showProgress();
//        mJobDetailModel.sendCV(companyId, this);
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
                            mJobDetailView.sendCVSuccess();
                        } else {
                            mJobDetailView.hideProgress();
                            if (resultBean.getResponse().equals("email failed")) {
                                mJobDetailView.sendCVEmailFailed();
                            } else if (resultBean.getResponse().equals("No destination")) {
                                mJobDetailView.sendCVNoDestination();
                            } else if (resultBean.getResponse().equals("have sent")) {
                                mJobDetailView.sendCVRepeated();
                            }else if(resultBean.getResponse().equals("no write")){
                                mJobDetailView.sendCVNoWrite();
                            }else {
                                mJobDetailView.sendCVFailure();
                            }
                        }
                    }
                });
    }

}
