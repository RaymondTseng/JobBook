package com.example.jobbook.userdetail.widget;

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

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.moment.widget.MomentDetailActivity;
import com.example.jobbook.userdetail.UserDetailMomentAdapter;
import com.example.jobbook.userdetail.presenter.UserDetailMomentPresenterImpl;
import com.example.jobbook.userdetail.view.UserDetailMomentView;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-11-25.
 */

public class UserDetailMomentFragment extends Fragment implements UserDetailMomentView,
        UserDetailActivity.OnGetAccountListener, UserDetailMomentAdapter.OnMomentItemClickListener {
    private View view;
    private List<MomentBean> mData;
    private UserDetailMomentAdapter mAdapter;
    private UserDetailMomentPresenterImpl mPresenter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ViewStub mLoadingLayout;
    private String account;

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
        mPresenter = new UserDetailMomentPresenterImpl(this);
        mAdapter = new UserDetailMomentAdapter(getActivity(), mData);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnMomentItemClickListener(this);
        if(MyApplication.getmLoginStatus() != 0){
            mPresenter.loadMoments(account, MyApplication.getAccount());
        }else{
            mPresenter.loadMoments(account, "");
        }
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void loadMoments(List<MomentBean> moments) {
        mData = moments;
        mAdapter.refreshData(mData);
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
    public void getAccount(String account) {
        this.account = account;
//        mPresenter.loadMoments(account);
    }

    @Override
    public void onMomentItemClick(MomentBean momentBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("square_detail", momentBean);
        Util.toAnotherActivity(getActivity(), MomentDetailActivity.class, bundle);
    }
}
