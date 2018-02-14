package com.example.jobbook.ui.person.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.person.FavouriteArticleContract;
import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.presenter.person.FavouriteArticlePresenter;
import com.example.jobbook.ui.article.activity.ArticleDetailActivity;
import com.example.jobbook.ui.person.adapter.FavouriteArticleAdapter;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 16-12-7.
 */

public class FavouriteArticleFragment extends Fragment implements FavouriteArticleContract.View,
        FavouriteArticleAdapter.OnArticleItemClickListener {
    private View view;

    @BindView(R.id.base_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_base_lv_loading)
    ViewStub mLoadingLayout;

    private FavouriteArticleAdapter mAdapter;
    private FavouriteArticlePresenter mPresenter;
    private List<ArticleBean> mData;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_lv, container, false);
        ButterKnife.bind(this, view);
        init(view);
        return view;
    }

    private void init(View view){
        mLoadingLayout.inflate();
        mData = new ArrayList<>();
        mPresenter = new FavouriteArticlePresenter(this);
        mAdapter = new FavouriteArticleAdapter(getActivity(), mData);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnArticleItemClickListener(this);
        if(MyApplication.getmLoginStatus() != 0){
            mPresenter.loadArticles(MyApplication.getAccount());
        }
    }

    @Override
    public void loadArticles(List<ArticleBean> mArticles) {
        mData = mArticles;
        mAdapter.onRefreshData(mData);
    }

    @Override
    public void showProgress() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        Util.showSnackBar(view , msg);
    }

    @Override
    public void onArticleItemClick(ArticleBean articleBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("article_detail", articleBean);
        Util.toAnotherActivity(getActivity(), ArticleDetailActivity.class, bundle);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(MyApplication.getmLoginStatus() != 0){
            mPresenter.loadArticles(MyApplication.getAccount());
        }
    }

}
