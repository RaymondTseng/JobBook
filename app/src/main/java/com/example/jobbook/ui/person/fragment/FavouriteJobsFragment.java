package com.example.jobbook.ui.person.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.person.FavouriteJobContract;
import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.presenter.person.FavouriteJobPresenter;
import com.example.jobbook.ui.job.activity.JobDetailActivity;
import com.example.jobbook.ui.person.adapter.FavouriteJobAdapter;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Xu on 16-12-7.
 */

public class FavouriteJobsFragment extends Fragment implements FavouriteJobContract.View, FavouriteJobAdapter.OnJobItemClickListener {
    private View view;

    @BindView(R.id.base_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_base_lv_loading)
    ViewStub mLoadingLayout;

    private FavouriteJobAdapter mAdapter;
    private List<JobBean> mData;
    private FavouriteJobPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_lv, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mData = new ArrayList<>();
        mPresenter = new FavouriteJobPresenter(this);
        mAdapter = new FavouriteJobAdapter(getActivity(), mData);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnJobItemClickListener(this);
        if (MyApplication.getmLoginStatus() != 0) {
            mPresenter.loadFavouriteJobs(MyApplication.getAccount());
        }
    }

    @Override
    public void loadJobs(List<JobBean> mJobs) {
        mData = mJobs;
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
        SnackBarUtil.showSnackBar(this.getActivity(), msg);
    }

    @Override
    public void onJobItemClick(JobBean jobBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("job_detail", jobBean);
        Util.toAnotherActivity(getActivity(), JobDetailActivity.class, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getmLoginStatus() != 0) {
            mPresenter.loadFavouriteJobs(MyApplication.getAccount());
        }
    }
}
