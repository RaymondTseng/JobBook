package com.example.jobbook.job.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.JobBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public interface JobView extends IBaseView{

    void addJobs(List<JobBean> jobList);

    void showLoadingFailMsg();

    void search();

}
