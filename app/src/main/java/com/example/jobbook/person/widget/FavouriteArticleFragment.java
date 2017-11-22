package com.example.jobbook.person.widget;

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

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.article.widget.ArticleDetailActivity;
import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.person.FavouriteArticleAdapter;
import com.example.jobbook.person.presenter.FavouriteArticlePresenter;
import com.example.jobbook.person.presenter.FavouriteArticlePresenterImpl;
import com.example.jobbook.person.view.FavouriteArticleView;
import com.example.jobbook.ui.DividerItemDecoration;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-12-7.
 */

public class FavouriteArticleFragment extends Fragment implements FavouriteArticleView,
        FavouriteArticleAdapter.OnArticleItemClickListener {
    private View view;
    private RecyclerView mRecyclerView;
    private FavouriteArticleAdapter mAdapter;
    private FavouriteArticlePresenter mPresenter;
    private List<ArticleBean> mData;
    private LinearLayoutManager mLayoutManager;
    private ViewStub mLoadingLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_lv, container, false);
        L.i("create");
        init(view);
        return view;
    }

    private void init(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_rv);
        mLoadingLayout = (ViewStub) view.findViewById(R.id.fragment_base_lv_loading);
        mLoadingLayout.inflate();
        mData = new ArrayList<>();
        mPresenter = new FavouriteArticlePresenterImpl(this);
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
            mPresenter.loadArticle(MyApplication.getAccount());
        }
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
            mPresenter.loadArticle(MyApplication.getAccount());
        }
    }
}
