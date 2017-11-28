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

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.userdetail.UserDetailFollowAdapter;
import com.example.jobbook.userdetail.presenter.UserDetailFollowPresenter;
import com.example.jobbook.userdetail.presenter.UserDetailFollowPresenterImpl;
import com.example.jobbook.userdetail.view.UserDetailFollowView;
import com.example.jobbook.widget.DividerItemDecoration;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-11-28.
 */

public class UserDetailFollowFragment extends Fragment implements UserDetailFollowView,
        UserDetailActivity.OnGetAccountListener, UserDetailFollowAdapter.OnFollowClickListener,
        UserDetailFollowAdapter.OnFollowerItemClickListener{
    private View view;
    private RecyclerView mRecyclerView;
    private List<TypePersonBean> mData;
    private UserDetailFollowAdapter mAdapter;
    private UserDetailFollowPresenter mPresenter;
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
        mData = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_rv);
        mLoadingLayout = (ViewStub) view.findViewById(R.id.fragment_base_lv_loading);
        mLoadingLayout.inflate();
        mAdapter = new UserDetailFollowAdapter(getActivity(), mData);
        mPresenter = new UserDetailFollowPresenterImpl(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnFollowClickListener(this);
        mAdapter.setOnFollwerItemClickListener(this);
        mPresenter.loadFollow(account);
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void loadFollow(List<TypePersonBean> mFollow) {
        mData = mFollow;
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
    public void showLoadFailMsg(String msg) {

    }

    @Override
    public void onError(String msg) {
        Util.showSnackBar(view , msg);
    }

    @Override
    public void followSuccess() {
        Util.showSnackBar(view, "关注成功!");
    }

    @Override
    public void getAccount(String account) {
        this.account = account;
//        mPresenter.loadFollow(account);
    }


    @Override
    public void onFollowClick(TypePersonBean personBean) {
        mPresenter.follow(MyApplication.getAccount(), personBean.getAccount());
    }

    @Override
    public void onFollowerItemClick(TypePersonBean personBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("person_bean", personBean);
        Util.toAnotherActivity(getActivity(), UserDetailActivity.class, bundle);
    }
}
