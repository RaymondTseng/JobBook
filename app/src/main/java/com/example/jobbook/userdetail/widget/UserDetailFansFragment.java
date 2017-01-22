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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.userdetail.UserDetailFansAdapter;
import com.example.jobbook.userdetail.presenter.UserDetailFansPresenter;
import com.example.jobbook.userdetail.presenter.UserDetailFansPresenterImpl;
import com.example.jobbook.userdetail.view.UserDetailFansView;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-11-30.
 */

public class UserDetailFansFragment extends Fragment implements UserDetailFansView,
        UserDetailActivity.OnGetAccountListener, UserDetailFansAdapter.OnFanItemClickListener,
        UserDetailFansAdapter.OnFollowFanClickListener{
    private View view;
    private RecyclerView mRecyclerView;
    private UserDetailFansAdapter mAdapter;
    private List<TypePersonBean> mData;
    private UserDetailFansPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout mLoadingLayout;
    private String account;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_lv, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_rv);
        mLoadingLayout = (LinearLayout) view.findViewById(R.id.loading_layout) ;
        mData = new ArrayList<>();
        mPresenter = new UserDetailFansPresenterImpl(this);
        mAdapter = new UserDetailFansAdapter(getActivity(), mData);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnFollowFanClickListener(this);
        mAdapter.setOnFanItemClickListener(this);
        mPresenter.loadFans(account);
    }

    @Override
    public void loadFans(List<TypePersonBean> mFans) {
        mAdapter.refreshData(mFans);
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
    public void followSuccess() {
        Util.showSnackBar(view , "关注成功!");
    }

    @Override
    public void onError(String msg) {
        Util.showSnackBar(view, msg);
    }

    @Override
    public void getAccount(String account) {
        this.account = account;
//        mPresenter.loadFans(account);
    }


    @Override
    public void onFanItemClick(TypePersonBean personBean) {
        Bundle bundle = new Bundle();
        L.i("userdetailfans", personBean.toString());
        bundle.putSerializable("person_bean", personBean);
        Util.toAnotherActivity(getActivity(), UserDetailActivity.class, bundle);
    }

    @Override
    public void onFollowFanClick(TypePersonBean personBean) {
        mPresenter.follow(MyApplication.getAccount(), personBean.getAccount());
    }
}
