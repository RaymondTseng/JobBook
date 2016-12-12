package com.example.jobbook.blacklist.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.jobbook.R;
import com.example.jobbook.blacklist.BlackListRecyclerViewAdapter;
import com.example.jobbook.blacklist.presenter.BlackListPresenter;
import com.example.jobbook.blacklist.view.BlackListView;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2016/12/7.
 */

public class BlackListActivity extends Activity implements BlackListView {

    private RecyclerView mBlackRecyclerView;
    private ImageButton mBackImageButton;
    private LinearLayout mLoadingLinearLayout;
    private BlackListRecyclerViewAdapter mAdapter;
    private BlackListPresenter presenter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        initViews();
        initEvents();
    }

    private void initViews() {
        view = findViewById(android.R.id.content);
        mBlackRecyclerView = (RecyclerView) findViewById(R.id.blacklist_rv);
        mBackImageButton = (ImageButton) findViewById(R.id.blacklist_back_ib);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.blacklist_loading_layout);
    }

    private void initEvents() {
        mAdapter = new BlackListRecyclerViewAdapter(this);
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
        Util.showSnackBar(view, "黑名单列表读取错误，请重试！");
    }
}
