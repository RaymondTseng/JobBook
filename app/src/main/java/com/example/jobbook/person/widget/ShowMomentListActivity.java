package com.example.jobbook.person.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.moment.widget.MomentDetailActivity;
import com.example.jobbook.person.presenter.ShowMomentListPresenter;
import com.example.jobbook.person.presenter.ShowMomentListPresenterImpl;
import com.example.jobbook.person.view.ShowMomentListView;
import com.example.jobbook.userdetail.UserDetailMomentAdapter;
import com.example.jobbook.widget.DividerItemDecoration;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowMomentListActivity extends Activity implements ShowMomentListView, View.OnClickListener,
                UserDetailMomentAdapter.OnMomentItemClickListener{

    private RecyclerView mRecyclerView;
    private ImageButton mBackImageButton;
    private ViewStub mLoadingLinearLayout;
    private UserDetailMomentAdapter mAdapter;
    private ShowMomentListPresenter presenter;
    private LinearLayoutManager mLayoutManager;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momentlist);
        initViews();
        initEvents();
        L.i("create");
    }

    private void initViews() {
        view = findViewById(android.R.id.content);
        mRecyclerView = (RecyclerView) findViewById(R.id.momentlist_rv);
        mBackImageButton = (ImageButton) findViewById(R.id.momentlist_back_ib);
        mLoadingLinearLayout = (ViewStub) findViewById(R.id.momentlist_loading_layout);
        mLoadingLinearLayout.inflate();
    }

    private void initEvents() {
        mAdapter = new UserDetailMomentAdapter(this, new ArrayList<MomentBean>());
        presenter = new ShowMomentListPresenterImpl(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnMomentItemClickListener(this);
        mBackImageButton.setOnClickListener(this);
        mAdapter.setOnMomentItemClickListener(this);
        if(MyApplication.getmLoginStatus() != 0){
            presenter.loadMomentList(MyApplication.getAccount(), MyApplication.getAccount());
        }
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        Util.showSnackBar(view, "动态列表读取错误，请重试！");
    }

    @Override
    public void loadFanList(List<MomentBean> list) {
        mAdapter.refreshData(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.momentlist_back_ib:
                finish();
                break;
        }
    }

    @Override
    public void onMomentItemClick(MomentBean momentBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("square_detail", momentBean);
        L.i(momentBean.toString());
        Util.toAnotherActivity(this, MomentDetailActivity.class, bundle);
    }
}
