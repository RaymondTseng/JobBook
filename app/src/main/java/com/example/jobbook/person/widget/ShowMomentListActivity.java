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
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.blacklist.BlackListRecyclerViewAdapter;
import com.example.jobbook.person.presenter.ShowMomentListPresenter;
import com.example.jobbook.person.presenter.ShowMomentListPresenterImpl;
import com.example.jobbook.person.view.ShowMomentListView;
import com.example.jobbook.userdetail.UserDetailFansAdapter;
import com.example.jobbook.userdetail.UserDetailMomentAdapter;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowMomentListActivity extends Activity implements ShowMomentListView, View.OnClickListener{

    private ListView mShowMomentListListiew;
    private ImageButton mBackImageButton;
    private LinearLayout mLoadingLinearLayout;
    private UserDetailMomentAdapter mAdapter;
    private ShowMomentListPresenter presenter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momentlist);
        initViews();
        initEvents();
        L.i("showmoment", "create");
    }

    private void initViews() {
        view = findViewById(android.R.id.content);
        mShowMomentListListiew = (ListView) findViewById(R.id.momentlist_lv);
        mBackImageButton = (ImageButton) findViewById(R.id.momentlist_back_ib);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.momentlist_loading_layout);
    }

    private void initEvents() {
        mAdapter = new UserDetailMomentAdapter(this, new ArrayList<MomentBean>());
        mShowMomentListListiew.setAdapter(mAdapter);
        presenter = new ShowMomentListPresenterImpl(this);
        presenter.loadMomentList(MyApplication.getAccount());
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
}
