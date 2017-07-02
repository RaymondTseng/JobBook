package com.example.jobbook.job.presenter;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.job.model.JobModelImpl;
import com.example.jobbook.job.view.JobView;
import com.example.jobbook.network.RetrofitService;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2016/7/5.
 */
public class JobPresenterImpl implements JobPresenter, JobModelImpl.OnLoadJobListListener {

    private JobView mJobView;
//    private JobModel mJobModel;

    public JobPresenterImpl(JobView view) {
        mJobView = view;
//        mJobModel = new JobModelImpl();
    }

    @Override
    public void loadJobs(int pageIndex, boolean isRecommend, String type, String location) {
//        mJobView.showProgress();
//        mJobModel.loadJobs(pageIndex, isRecommend, type, location, this);
        if (isRecommend) {
            RetrofitService.getRecommendJobsList(pageIndex)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mJobView.showProgress();
                        }
                    })
                    .subscribe(new Subscriber<List<JobBean>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable throwable) {
                            mJobView.hideProgress();
                            mJobView.showLoadingFailMsg();
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onNext(List<JobBean> list) {
                            mJobView.hideProgress();
                            mJobView.addJobs(list);
                        }
                    });
        } else {
            RetrofitService.search(pageIndex, type, location)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mJobView.showProgress();
                        }
                    })
                    .subscribe(new Subscriber<List<JobBean>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable throwable) {
                            mJobView.hideProgress();
                            mJobView.showLoadingFailMsg();
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onNext(List<JobBean> list) {
                            mJobView.hideProgress();
                            mJobView.addJobs(list);
                        }
                    });
        }
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
