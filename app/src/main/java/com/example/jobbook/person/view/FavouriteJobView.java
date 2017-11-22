package com.example.jobbook.person.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.JobBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface FavouriteJobView extends IBaseView {

    void addJobs(List<JobBean> mJobs);

}
