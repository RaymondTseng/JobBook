package com.example.jobbook.job.model;


/**
 * Created by 椰树 on 2016/8/28.
 */
public interface JobDetailModel {
    void loadJobDetail(String jobId, JobDetailModelImpl.OnLoadJobListener listener);

    void like(String jobId, JobDetailModelImpl.OnLikeJobListener listener);

    void unlike(String jobId, JobDetailModelImpl.OnUnlikeJobListener listener);

    void sendCV(String jobId, JobDetailModelImpl.OnSendCVListener listener);
}
