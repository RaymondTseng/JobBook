package com.example.jobbook.person.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.person.presenter.ShowFollowerListPresenter;
import com.example.jobbook.person.presenter.ShowFollowerListPresenterImpl;
import com.example.jobbook.person.view.ShowFollowerListView;
import com.example.jobbook.userdetail.UserDetailFansAdapter;
import com.example.jobbook.userdetail.UserDetailMomentAdapter;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowFollowerListActivity extends Activity implements ShowFollowerListView, View.OnClickListener {

    private ListView mShowFollowerListListView;
    private ImageButton mBackImageButton;
    private LinearLayout mLoadingLinearLayout;
    private ShowFollowerListPresenter presenter;
    private UserDetailFansAdapter mAdapter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followerlist);
        initViews();
        initEvents();
    }

    private void initViews() {
        view = findViewById(android.R.id.content);
        mShowFollowerListListView = (ListView) findViewById(R.id.followerlist_lv);
        mBackImageButton = (ImageButton) findViewById(R.id.followerlist_back_ib);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.followerlist_loading_layout);
    }

    private void initEvents() {
        presenter = new ShowFollowerListPresenterImpl(this);
        mAdapter = new UserDetailFansAdapter(this, new ArrayList<PersonBean>());
        mShowFollowerListListView.setAdapter(mAdapter);
        presenter.loadFollwers(MyApplication.getAccount());
        mBackImageButton.setOnClickListener(this);
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
    public void showLoadFailMsg() {
        Util.showSnackBar(view, "关注列表读取错误，请重试！");
    }

    @Override
    public void loadFanList(List<PersonBean> list) {
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
}
