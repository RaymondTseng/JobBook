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
import com.example.jobbook.base.contract.person.UserDetailFollowContract;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.presenter.person.UserDetailFollowPresenter;
import com.example.jobbook.ui.person.activity.UserDetailActivity;
import com.example.jobbook.ui.person.adapter.UserDetailFollowAdapter;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Xu on 16-11-28.
 */

public class UserDetailFollowFragment extends Fragment implements UserDetailFollowContract.View,
        UserDetailActivity.OnGetAccountListener, UserDetailFollowAdapter.OnFollowClickListener,
        UserDetailFollowAdapter.OnFollowerItemClickListener {

    @BindView(R.id.base_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_base_lv_loading)
    ViewStub mLoadingLayout;

    private View view;
    private List<TypePersonBean> mData;
    private UserDetailFollowAdapter mAdapter;
    private UserDetailFollowPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private String account;

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
        mLoadingLayout.inflate();
        mAdapter = new UserDetailFollowAdapter(getActivity(), mData);
        mPresenter = new UserDetailFollowPresenter(this);
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
        SnackBarUtil.showSnackBar(getActivity(), msg);
    }

    @Override
    public void followSuccess() {
        SnackBarUtil.showSnackBar(getActivity(), "关注成功!");
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
