package com.example.jobbook.person.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.person.FavouriteJobAdapter;
import com.example.jobbook.person.presenter.FavouriteJobPresenter;
import com.example.jobbook.person.presenter.FavouriteJobPresenterImpl;
import com.example.jobbook.person.view.FavouriteJobView;
import com.example.jobbook.util.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-12-7.
 */

public class FavouriteJobsFragment extends Fragment implements FavouriteJobView{
    private View view;
    private ListView mListView;
    private FavouriteJobAdapter mAdapter;
    private List<JobBean> mData;
    private FavouriteJobPresenter mPresenter;
    private LinearLayout mLoadingLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_lv, container, false);
        L.i("momentfragment", "create");
        init(view);
        return view;
    }

    private void init(View view){
        mListView = (ListView) view.findViewById(R.id.base_lv);
        mLoadingLayout = (LinearLayout) view.findViewById(R.id.loading_layout);
        mData = new ArrayList<>();
        mPresenter = new FavouriteJobPresenterImpl(this);
        mAdapter = new FavouriteJobAdapter(getActivity(), mData);
        mListView.setAdapter(mAdapter);
        mPresenter.loadFavouriteJobs(MyApplication.getAccount());
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
    public void showLoadFailMsg() {

    }
}
