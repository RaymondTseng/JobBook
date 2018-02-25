package com.example.jobbook.base.contract.job;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.JobDetailBean;

/**
 * Created by Xu on 2017/12/3.
 */

public interface JobDetailContract {

    interface View extends IBaseView {
//        void switch2Chat();

        void addJob(JobDetailBean jobDetailBean);

        void NoLoginError();

        void likeSuccess();

        void unlikeSuccess();

        void sendCVSuccess();

    }

    interface Presenter extends IBasePresenter<View> {
        void loadJob(String jobId);

        void like(String jobId);

        void unlike(String jobId);

        void sendCV(String companyId);
    }
}
