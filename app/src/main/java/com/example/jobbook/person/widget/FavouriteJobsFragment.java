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
import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.job.widget.JobDetailActivity;
import com.example.jobbook.person.FavouriteJobAdapter;
import com.example.jobbook.person.presenter.FavouriteJobPresenter;
import com.example.jobbook.person.presenter.FavouriteJobPresenterImpl;
import com.example.jobbook.person.view.FavouriteJobView;
import com.example.jobbook.ui.DividerItemDecoration;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-12-7.
 */

public class FavouriteJobsFragment extends Fragment implements FavouriteJobView, FavouriteJobAdapter.OnJobItemClickListener{
    private View view;
    private RecyclerView mRecyclerView;
    private FavouriteJobAdapter mAdapter;
    private List<JobBean> mData;
    private FavouriteJobPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private ViewStub mLoadingLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_lv, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_rv);
        mLoadingLayout = (ViewStub) view.findViewById(R.id.fragment_base_lv_loading);
        mLoadingLayout.inflate();
        mData = new ArrayList<>();
        mPresenter = new FavouriteJobPresenterImpl(this);
        mAdapter = new FavouriteJobAdapter(getActivity(), mData);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnJobItemClickListener(this);
        if(MyApplication.getmLoginStatus() != 0){
            mPresenter.loadFavouriteJobs(MyApplication.getAccount());
        }
    }

    @Override
    public void showProgress() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void addJobs(List<JobBean> mJobs) {
        mData = mJobs;
        mAdapter.onRefreshData(mData);
    }

    @Override
    public void hideProgress() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        Util.showSnackBar(view, "读取收藏岗位错误，请重试！");
    }

    @Override
    public void onJobItemClick(JobBean jobBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("job_detail", jobBean);
        Util.toAnotherActivity(getActivity(), JobDetailActivity.class, bundle);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(MyApplication.getmLoginStatus() != 0){
            mPresenter.loadFavouriteJobs(MyApplication.getAccount());
        }
    }
}
