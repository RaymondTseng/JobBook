package com.example.jobbook.userdetail.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.userdetail.UserDetailFollowAdapter;
import com.example.jobbook.userdetail.presenter.UserDetailFollowPresenter;
import com.example.jobbook.userdetail.presenter.UserDetailFollowPresenterImpl;
import com.example.jobbook.userdetail.view.UserDetailFollowView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-11-28.
 */

public class UserDetailFollowFragment extends Fragment implements UserDetailFollowView,
        UserDetailActivity.OnGetAccountListener {
    private View view;
    private ListView mListView;
    private List<PersonBean> mData;
    private UserDetailFollowAdapter mAdapter;
    private UserDetailFollowPresenter mPresenter;
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
        mListView = (ListView) view.findViewById(R.id.base_lv);
        mAdapter = new UserDetailFollowAdapter(getActivity(), mData);
        mPresenter = new UserDetailFollowPresenterImpl(this);
        mListView.setAdapter(mAdapter);
        mPresenter.loadFollow(account);
    }

    @Override
    public void loadFollow(List<PersonBean> mFollow) {
        mData = mFollow;
        mAdapter.refreshData(mData);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void getAccount(String account) {
        this.account = account;
//        mPresenter.loadFollow(account);
    }
}
