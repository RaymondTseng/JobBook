package com.example.jobbook.userdetail.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jobbook.R;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.userdetail.UserDetailMomentAdapter;
import com.example.jobbook.userdetail.presenter.UserDetailMomentPresenterImpl;
import com.example.jobbook.userdetail.view.UserDetailMomentView;
import com.example.jobbook.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-11-25.
 */

public class UserDetailMomentFragment extends Fragment implements UserDetailMomentView,
        UserDetailActivity.OnGetAccountListener {
    private View view;
    private List<MomentBean> mData;
    private UserDetailMomentAdapter mAdapter;
    private UserDetailMomentPresenterImpl mPresenter;
    private ListView mListView;
    private String account;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_lv, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        mListView = (ListView) view.findViewById(R.id.base_lv);
        mData = new ArrayList<>();
        mPresenter = new UserDetailMomentPresenterImpl(this);
        mAdapter = new UserDetailMomentAdapter(getActivity(), mData);
        mListView.setAdapter(mAdapter);
        mPresenter.loadMoments(account);

    }

    @Override
    public void loadMoments(List<MomentBean> moments) {
        mData = moments;
        mAdapter.refreshData(mData);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void getAccount(String account) {
        this.account = account;
        L.i("user_detail_moment", "account:" + this.account);
//        mPresenter.loadMoments(account);
    }
}
