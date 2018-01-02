package com.example.jobbook.presenter.job;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.job.JobContract;
import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2018/1/2.
 */

public class JobPresenter extends RxPresenter<JobContract.View> implements JobContract.Presenter {

    public JobPresenter(JobContract.View view) {
        attach(view);
    }

    @Override
    public void loadJobs(int pageIndex, boolean isRecommend, String type, String location) {
        if (isRecommend) {
            RetrofitService.getRecommendJobsList(pageIndex)
                    .subscribe(new BaseSubscriber<List<JobBean>>() {
                        @Override
                        public IBaseView getBaseView() {
                            return mView;
                        }

                        @Override
                        public void onNext(List<JobBean> jobBeans) {
                            mView.addJobs(jobBeans);
                        }
                    });
        } else {
            RetrofitService.search(pageIndex, type, location)
                    .subscribe(new BaseSubscriber<List<JobBean>>() {
                        @Override
                        public IBaseView getBaseView() {
                            return mView;
                        }

                        @Override
                        public void onNext(List<JobBean> jobBeans) {
                            mView.addJobs(jobBeans);
                        }
                    });
        }
    }

    @Override
    public void search() {
        mView.search();
    }
}
