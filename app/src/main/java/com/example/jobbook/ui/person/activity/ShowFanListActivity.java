package com.example.jobbook.ui.person.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.person.FanListContract;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.presenter.person.FanListPresenter;
import com.example.jobbook.userdetail.UserDetailFansAdapter;
import com.example.jobbook.userdetail.widget.UserDetailActivity;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowFanListActivity extends Activity implements FanListContract.View,
        UserDetailFansAdapter.OnFanItemClickListener, UserDetailFansAdapter.OnFollowFanClickListener {
    //    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.fanlist_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.fanlist_back_ib)
    ImageButton mBackImageButton;

    @BindView(R.id.fanlist_loading_layout)
    ViewStub mLoadingViewStub;

    private UserDetailFansAdapter adapter;
    private FanListPresenter presenter;
    private LinearLayoutManager mLayoutManager;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fanlist);
        ButterKnife.bind(this);
        initViews();
        initEvents();
    }

    private void initViews() {
        view = findViewById(android.R.id.content);
        mLoadingViewStub.inflate();
    }

    private void initEvents() {
//        mLayoutManager = new LinearLayoutManager(this);
//        mShowFanListRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
//        mShowFanListRecyclerView.setHasFixedSize(true);
//        mShowFanListRecyclerView.setLayoutManager(mLayoutManager);
//        mShowFanListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter = new FanListPresenter(this);
        adapter = new UserDetailFansAdapter(this, new ArrayList<TypePersonBean>());
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        adapter.setOnFanItemClickListener(this);
        adapter.setOnFollowFanClickListener(this);
        presenter.loadFans(MyApplication.getAccount());
//        adapter.setOnUserFanItemClickListener(new UserDetailFansAdapter.OnUserFanItemClickListener() {
//            @Override
//            public void onUserFanItemClick(PersonBean personBean) {
//                Intent intent = new Intent(ShowFanListActivity.this, UserDetailActivity.class);
//                intent.putExtra("person_bean", MyApplication.getmPersonBean());
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void showProgress() {
        mLoadingViewStub.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingViewStub.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        Util.showSnackBar(view, msg);
    }

    @Override
    public void loadFanList(List<TypePersonBean> list) {
        adapter.refreshData(list);
    }

    @Override
    public void followSuccess() {
        Util.showSnackBar(view, "关注成功!");
        presenter.loadFans(MyApplication.getAccount());
    }

    @OnClick(R.id.fanlist_back_ib)
    public void click_back() {
        finish();
    }

    @Override
    public void onFanItemClick(TypePersonBean personBean) {
        Bundle bundle = new Bundle();
        L.i(personBean.toString());
        bundle.putParcelable("person_bean", personBean);
        Util.toAnotherActivity(this, UserDetailActivity.class, bundle);
    }

    @Override
    public void onFollowFanClick(TypePersonBean personBean) {
        presenter.follow(MyApplication.getAccount(), personBean.getAccount());
    }
}
