package com.example.jobbook.job.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.bean.JobDetailBean;

/**
 * Created by 椰树 on 2016/8/28.
 */
public interface JobDetailView extends IBaseView {
    void like(String jobId);

    void unlike(String jobId);

    void switch2Chat();

    void addJob(JobDetailBean jobDetailBean);

    void showLoadFailMsg();

    void NoLoginError();

    void likeSuccess();

    void unlikeSuccess();

    void likeError();

    void unlikeError();

    void sendCV(String companyId);

    void sendCVSuccess();

    void sendCVFailure();

    void sendCVEmailFailed();

    void sendCVNoDestination();

    void sendCVRepeated();

    void sendCVNoWrite();

}
