package com.example.jobbook.person.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.person.FavouriteArticleAdapter;
import com.example.jobbook.person.FavouriteJobAdapter;
import com.example.jobbook.person.presenter.FavouriteArticlePresenter;
import com.example.jobbook.person.presenter.FavouriteArticlePresenterImpl;
import com.example.jobbook.person.presenter.FavouriteJobPresenter;
import com.example.jobbook.person.view.FavouriteArticleView;
import com.example.jobbook.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-12-7.
 */

public class FavouriteArticleFragment extends Fragment implements FavouriteArticleView {
    private View view;
    private ListView mListView;
    private FavouriteArticleAdapter mAdapter;
    private FavouriteArticlePresenter mPresenter;
    private List<ArticleBean> mData;
    private LinearLayout mLoadingLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_lv, container, false);
        L.i("momentfragment", "create");
        init(view);
        return view;
    }

    private void init(View view){
        mListView = (ListView) view.findViewById(R.id.base_lv);
        mLoadingLayout = (LinearLayout) view.findViewById(R.id.loading_layout);
        mData = new ArrayList<>();
        mPresenter = new FavouriteArticlePresenterImpl(this);
        mAdapter = new FavouriteArticleAdapter(getActivity(), mData);
        mListView.setAdapter(mAdapter);
        mPresenter.loadArticle(MyApplication.getAccount());
    }

    @Override
    public void showProgress() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void addArticles(List<ArticleBean> mArticles) {
        mData = mArticles;
        mAdapter.onRefreshData(mData);
    }

    @Override
    public void hideProgress() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg() {

    }
}
