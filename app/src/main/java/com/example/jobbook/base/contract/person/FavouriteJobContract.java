package com.example.jobbook.base.contract.person;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.JobBean;

import java.util.List;

/**
 * Created by Xu on 2017/12/17.
 */

public interface FavouriteJobContract {

    interface View extends IBaseView {
        void loadJobs(List<JobBean> mJobs);
    }

    interface Presenter extends IBasePresenter<View> {
        void loadFavouriteJobs(String accountName);
    }
}
