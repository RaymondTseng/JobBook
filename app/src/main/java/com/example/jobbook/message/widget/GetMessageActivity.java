package com.example.jobbook.message.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.jobbook.R;
import com.example.jobbook.message.GetMessageRecyclerViewAdapter;
import com.example.jobbook.message.view.GetMessageView;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2016/12/6.
 */

public class GetMessageActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, GetMessageView {

    private SwipeRefreshLayout refreshLayout;
    private ImageButton mBackImageButton;
    //    private StickyListHeadersListView stickyList;
    private RecyclerView mGetMessageRecyclerView;
    private LinearLayout mLoadingLinearLayout;
    private GetMessageRecyclerViewAdapter mAdapter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmessage);
        initViews();
        initEvents();
    }

    private void initViews() {
        view = findViewById(android.R.id.content);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.getmessage_refresh_layout);
        mBackImageButton = (ImageButton) findViewById(R.id.getmessage_back_ib);
//        stickyList = (StickyListHeadersListView) findViewById(R.id.getmessage_lv);
        mGetMessageRecyclerView = (RecyclerView) findViewById(R.id.getmessage_rv);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.getmessage_loading_layout);
    }

    private void initEvents() {
        mAdapter = new GetMessageRecyclerViewAdapter(this);
    }

    @Override
    public void onRefresh() {

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
        Util.showSnackBar(view, "获取消息错误，请重试！");
    }
}
