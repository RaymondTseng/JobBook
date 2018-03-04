package com.example.jobbook.ui.person.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.person.FollowerListContract;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.presenter.person.FollowerListPresenter;
import com.example.jobbook.ui.person.adapter.UserDetailFollowAdapter;
import com.example.jobbook.util.SnackBarUtil;
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

public class ShowFollowerListActivity extends Activity implements FollowerListContract.View,
        UserDetailFollowAdapter.OnFollowClickListener, UserDetailFollowAdapter.OnFollowerItemClickListener {

    @BindView(R.id.followerlist_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.followerlist_back_ib)
    ImageButton mBackImageButton;

    @BindView(R.id.followerlist_loading_layout)
    ViewStub mLoadingLinearLayout;

    private FollowerListPresenter presenter;
    private LinearLayoutManager mLayoutManager;
    private UserDetailFollowAdapter mAdapter;
    private MyApplication myApplication;
    private static int REFRESH = 1;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followerlist);
        ButterKnife.bind(this);
        initEvents();
    }

    private void initEvents() {
        presenter = new FollowerListPresenter(this);
        handler = new ShowFollowerHandler();
        myApplication = (MyApplication) getApplication();
        mAdapter = new UserDetailFollowAdapter(this, new ArrayList<TypePersonBean>());
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnFollwerItemClickListener(this);
        mAdapter.setOnFollowClickListener(this);
        presenter.loadFollwers(MyApplication.getAccount());

//        mAdapter.setOnUserFollowItemClickListener(new UserDetailFollowAdapter.OnUserFollowItemClickListener() {
//            @Override
//            public void onUserFollowItemClick(PersonBean personBean) {
//                Intent intent = new Intent(ShowFollowerListActivity.this, UserDetailActivity.class);
//                intent.putExtra("person_bean", personBean);
//                startActivity(intent);
//            }
//        });
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
        SnackBarUtil.showSnackBar(this, msg);
    }

    @Override
    public void followSuccess() {
        SnackBarUtil.showSnackBar(this, "关注成功!");
    }

    @Override
    public void loadFollowerList(List<TypePersonBean> list) {
        mAdapter.refreshData(list);
    }

    @OnClick(R.id.followerlist_back_ib)
    public void back() {
        finish();
    }

    @Override
    public void onFollowClick(TypePersonBean personBean) {
        presenter.follow(MyApplication.getAccount(), personBean.getAccount());
    }

    @Override
    public void onFollowerItemClick(TypePersonBean personBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("person_bean", personBean);
        myApplication.setHandler(handler);
        Util.toAnotherActivity(this, UserDetailActivity.class, bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    public class ShowFollowerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == REFRESH) {
                presenter.loadFollwers(MyApplication.getAccount());
            }
        }
    }
}
