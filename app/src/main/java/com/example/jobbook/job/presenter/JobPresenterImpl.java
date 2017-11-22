package com.example.jobbook.job.presenter;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.job.view.JobView;
import com.example.jobbook.base.BaseObserver;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class JobPresenterImpl implements JobPresenter {

    private JobView mJobView;

    public JobPresenterImpl(JobView view) {
        mJobView = view;
    }

    @Override
    public void loadJobs(int pageIndex, boolean isRecommend, String type, String location) {
        if (isRecommend) {
            RetrofitService.getRecommendJobsList(pageIndex)
                    .subscribe(new BaseObserver<List<JobBean>>() {
                        @Override
                        public IBaseView getBaseView() {
                            return mJobView;
                        }

                        @Override
                        public void onNext(List<JobBean> jobBeans) {
                            mJobView.addJobs(jobBeans);
                        }
                    });
        } else {
            RetrofitService.search(pageIndex, type, location)
                    .subscribe(new BaseObserver<List<JobBean>>() {
                        @Override
                        public IBaseView getBaseView() {
                            return mJobView;
                        }

                        @Override
                        public void onNext(List<JobBean> jobBeans) {
                            mJobView.addJobs(jobBeans);
                        }
                    });
        }
    }

    @Override
    public void search() {
        mJobView.search();
    }
}
