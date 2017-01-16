package com.example.jobbook.person.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.jobbook.R;
import com.example.jobbook.person.presenter.ShowFollowerListPresenter;
import com.example.jobbook.person.view.ShowFollowerListView;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowFollowerListActivity extends Activity implements ShowFollowerListView {

    private RecyclerView mShowFollowerListRecyclerView;
    private ImageButton mBackImageButton;
    private LinearLayout mLoadingLinearLayout;
    private ShowFollowerListPresenter presenter;
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
        mShowFollowerListRecyclerView = (RecyclerView) findViewById(R.id.followerlist_rv);
        mBackImageButton = (ImageButton) findViewById(R.id.followerlist_back_ib);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.followerlist_loading_layout);
    }

    private void initEvents() {
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
}
