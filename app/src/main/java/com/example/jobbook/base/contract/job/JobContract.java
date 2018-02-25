package com.example.jobbook.base.contract.job;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.JobBean;

import java.util.List;

/**
 * Created by Xu on 2017/12/3.
 */

public interface JobContract {

    interface View extends IBaseView {
        void addJobs(List<JobBean> jobList);
    }

    interface Presenter extends IBasePresenter<View> {
        void loadJobs(int pageIndex, boolean isRecommend, String type, String location);

        void search();
    }
}
