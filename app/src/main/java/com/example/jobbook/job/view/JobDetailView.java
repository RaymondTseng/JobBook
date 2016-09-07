package com.example.jobbook.job.view;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.bean.JobDetailBean;

/**
 * Created by 椰树 on 2016/8/28.
 */
public interface JobDetailView {
    void like(String jobId);

    void unlike(String jobId);

    void switch2Chat();

    void addJob(JobDetailBean jobDetailBean);

    void hideProgress();

    void showLoadFailMsg();

    void showProhress();

    void NoLoginError();

    void likeSuccess();

    void unlikeSuccess();

    void likeError();

    void unlikeError();

    void sendCV(String jobId);

    void sendCVSuccess();

    void sendCVFailure();

}
