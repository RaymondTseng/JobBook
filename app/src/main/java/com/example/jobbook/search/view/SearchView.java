package com.example.jobbook.search.view;

import com.example.jobbook.bean.JobBean;

import java.util.List;

/**
 * Created by Xu on 2016/8/31.
 */
public interface SearchView {

    void showProgress();

    void hideProgress();

    void getSearchResult(List<JobBean> list);

    void getSearchError();

}
