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
import com.example.jobbook.userdetail.UserDetailFansAdapter;
import com.example.jobbook.userdetail.presenter.UserDetailFansPresenter;
import com.example.jobbook.userdetail.presenter.UserDetailFansPresenterImpl;
import com.example.jobbook.userdetail.view.UserDetailFansView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-11-30.
 */

public class UserDetailFansFragment extends Fragment implements UserDetailFansView,
        UserDetailActivity.OnGetAccountListener {
    private View view;
    private ListView mListView;
    private UserDetailFansAdapter mAdapter;
    private List<PersonBean> mData;
    private UserDetailFansPresenter mPresenter;
    private String account;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        mListView = (ListView) view.findViewById(R.id.user_detail_lv);
        mData = new ArrayList<>();
        mPresenter = new UserDetailFansPresenterImpl(this);
        mAdapter = new UserDetailFansAdapter(getActivity(), mData);
        mListView.setAdapter(mAdapter);
        mPresenter.loadFans(account);

    }

    @Override
    public void loadFans(List<PersonBean> mFans) {
        mAdapter.refreshData(mFans);
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
//        mPresenter.loadFans(account);
    }
}
