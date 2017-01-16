package com.example.jobbook.person.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.jobbook.R;
import com.example.jobbook.blacklist.BlackListRecyclerViewAdapter;
import com.example.jobbook.person.presenter.ShowMomentListPresenter;
import com.example.jobbook.person.view.ShowMomentListView;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowMomentListActivity extends Activity implements ShowMomentListView {

    private RecyclerView mShowMomentListRecyclerView;
    private ImageButton mBackImageButton;
    private LinearLayout mLoadingLinearLayout;
    private BlackListRecyclerViewAdapter mAdapter;
    private ShowMomentListPresenter presenter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momentlist);
        initViews();
        initEvents();
    }

    private void initViews() {
        view = findViewById(android.R.id.content);
        mShowMomentListRecyclerView = (RecyclerView) findViewById(R.id.momentlist_rv);
        mBackImageButton = (ImageButton) findViewById(R.id.momentlist_back_ib);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.blacklist_loading_layout);
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
        Util.showSnackBar(view, "动态列表读取错误，请重试！");
    }
}
