package com.example.jobbook.person.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.person.presenter.ShowFanListPresenter;
import com.example.jobbook.person.presenter.ShowFanListPresenterImpl;
import com.example.jobbook.person.view.ShowFanListView;
import com.example.jobbook.userdetail.UserDetailFansAdapter;
import com.example.jobbook.userdetail.widget.UserDetailActivity;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowFanListActivity extends Activity implements ShowFanListView, View.OnClickListener,
        UserDetailFansAdapter.OnFanItemClickListener, UserDetailFansAdapter.OnFollowFanClickListener{

    //    private RecyclerView mShowFanListRecyclerView;
//    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private ImageButton mBackImageButton;
    private LinearLayout mLoadingLinearLayout;
    private UserDetailFansAdapter adapter;
    private ShowFanListPresenter presenter;
    private LinearLayoutManager mLayoutManager;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fanlist);
        initViews();
        initEvents();
    }

    private void initViews() {
        view = findViewById(android.R.id.content);
        mRecyclerView = (RecyclerView) findViewById(R.id.fanlist_rv);
        mBackImageButton = (ImageButton) findViewById(R.id.fanlist_back_ib);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.fanlist_loading_layout);
    }

    private void initEvents() {
//        mLayoutManager = new LinearLayoutManager(this);
//        mShowFanListRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
//        mShowFanListRecyclerView.setHasFixedSize(true);
//        mShowFanListRecyclerView.setLayoutManager(mLayoutManager);
//        mShowFanListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter = new ShowFanListPresenterImpl(this);
        mBackImageButton.setOnClickListener(this);
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
    public void loadFanList(List<TypePersonBean> list) {
        adapter.refreshData(list);
    }

    @Override
    public void followSuccess() {
        Util.showSnackBar(view, "关注成功!");
        presenter.loadFans(MyApplication.getAccount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanlist_back_ib:
                finish();
                break;
        }
    }


    @Override
    public void onFanItemClick(TypePersonBean personBean) {
        Bundle bundle = new Bundle();
        L.i("showfanlista", personBean.toString());
        bundle.putSerializable("person_bean", personBean);
        Util.toAnotherActivity(this, UserDetailActivity.class, bundle);
    }

    @Override
    public void onFollowFanClick(TypePersonBean personBean) {
        presenter.follow(MyApplication.getAccount(), personBean.getAccount());
    }
}
