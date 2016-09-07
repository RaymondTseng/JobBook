package com.example.jobbook.job.presenter;

/**
 * Created by 椰树 on 2016/8/28.
 */
public interface JobDetailPresenter {
    void loadJob(String jobId);

    void like(String jobId);

    void unlike(String jobId);

    void sendCV(String jobId);
}
