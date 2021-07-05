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
import com.example.jobbook.base.contract.person.UserDetailMomentContract;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.presenter.person.UserDetailMomentPresenter;
import com.example.jobbook.ui.moment.activity.MomentDetailActivity;
import com.example.jobbook.ui.person.activity.UserDetailActivity;
import com.example.jobbook.ui.person.adapter.UserDetailMomentAdapter;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Xu on 16-11-25.
 */

public class UserDetailMomentFragment extends Fragment implements UserDetailMomentContract.View,
        UserDetailActivity.OnGetAccountListener, UserDetailMomentAdapter.OnMomentItemClickListener {

    @BindView(R.id.base_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_base_lv_loading)
    ViewStub mLoadingLayout;

    private View view;
    private List<MomentBean> mData;
    private UserDetailMomentAdapter mAdapter;
    private UserDetailMomentPresenter mPresenter;
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

    private void init(){
        mData = new ArrayList<>();
        mPresenter = new UserDetailMomentPresenter(this);
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
    public void showLoadFailMsg(String msg) {
        SnackBarUtil.showSnackBar(getActivity(), msg);
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
