package com.example.jobbook.search.presenter;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.search.model.SearchModel;
import com.example.jobbook.search.model.SearchModelImpl;
import com.example.jobbook.search.view.SearchView;

import java.util.List;

/**
 * Created by Xu on 2016/8/31.
 */
public class SearchPresenterImpl implements SearchPresenter, SearchModelImpl.OnSearchListener {

    private SearchModel searchModel;
    private SearchView searchView;

    public SearchPresenterImpl(SearchView view) {
        searchModel = new SearchModelImpl();
        searchView = view;
    }

    @Override
    public void search(int type, String content, int pageIndex) {
        searchView.showProgress();
        searchModel.search(type, content, pageIndex, this);
    }

    @Override
    public void onSuccess(List<JobBean> list) {
        searchView.hideProgress();
        searchView.getSearchResult(list);
    }

    @Override
    public void onSearchFaliure(String msg, Exception e) {
        searchView.hideProgress();
        searchView.getSearchError();
    }

    @Override
    public void onSearchEmpty(String msg, Exception e) {
        searchView.hideProgress();
        searchView.getSearchEmpty();
    }
}
