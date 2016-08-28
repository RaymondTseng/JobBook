package com.example.jobbook.job.model;

import com.example.jobbook.job.presenter.JobDetailPresenter;

/**
 * Created by 椰树 on 2016/8/28.
 */
public interface JobDetailModel {
    void loadJobDetail(String jobId, JobDetailModelImpl.OnLoadJobListener listener);

    void like(boolean isLiked, String jobId, JobDetailModelImpl.OnLikeJobListener listener);
}
