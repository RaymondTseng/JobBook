package com.example.jobbook.person.widget;

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

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.person.presenter.ShowFollowerListPresenter;
import com.example.jobbook.person.presenter.ShowFollowerListPresenterImpl;
import com.example.jobbook.person.view.ShowFollowerListView;
import com.example.jobbook.userdetail.UserDetailFollowAdapter;
import com.example.jobbook.userdetail.widget.UserDetailActivity;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowFollowerListActivity extends Activity implements ShowFollowerListView, View.OnClickListener,
        UserDetailFollowAdapter.OnFollowClickListener, UserDetailFollowAdapter.OnFollowerItemClickListener{

    private RecyclerView mRecyclerView;
    private ImageButton mBackImageButton;
    private ViewStub mLoadingLinearLayout;
    private ShowFollowerListPresenter presenter;
    private LinearLayoutManager mLayoutManager;
    private UserDetailFollowAdapter mAdapter;
    private View view;
    private MyApplication myApplication;
    private static int REFRESH = 1;

    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followerlist);
        initViews();
        initEvents();
    }

    private void initViews() {
        view = findViewById(android.R.id.content);
        mRecyclerView = (RecyclerView) findViewById(R.id.followerlist_rv);
        mBackImageButton = (ImageButton) findViewById(R.id.followerlist_back_ib);
        mLoadingLinearLayout = (ViewStub) findViewById(R.id.followerlist_loading_layout);
        mLoadingLinearLayout.inflate();
    }

    private void initEvents() {
        presenter = new ShowFollowerListPresenterImpl(this);
        handler = new ShowFollowerHandler();
        myApplication = (MyApplication)getApplication();
        mAdapter = new UserDetailFollowAdapter(this, new ArrayList<TypePersonBean>());
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mBackImageButton.setOnClickListener(this);
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
        Util.showSnackBar(view, msg);
    }


    @Override
    public void followSuccess() {
        Util.showSnackBar(view , "关注成功!");
    }

    @Override
    public void loadFanList(List<TypePersonBean> list) {
        mAdapter.refreshData(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.followerlist_back_ib:
                finish();
                break;
        }
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
