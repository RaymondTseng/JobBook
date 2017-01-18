package com.example.jobbook.person.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.person.presenter.ShowFanListPresenter;
import com.example.jobbook.person.presenter.ShowFanListPresenterImpl;
import com.example.jobbook.person.view.ShowFanListView;
import com.example.jobbook.userdetail.UserDetailFansAdapter;
import com.example.jobbook.userdetail.widget.UserDetailActivity;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowFanListActivity extends Activity implements ShowFanListView, View.OnClickListener {

    //    private RecyclerView mShowFanListRecyclerView;
//    private LinearLayoutManager mLayoutManager;
    private ListView mShowFanListListView;
    private ImageButton mBackImageButton;
    private LinearLayout mLoadingLinearLayout;
    private UserDetailFansAdapter adapter;
    private ShowFanListPresenter presenter;
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
        mShowFanListListView = (ListView) findViewById(R.id.fanlist_lv);
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
        presenter.loadFans(MyApplication.getAccount());
        adapter = new UserDetailFansAdapter(this, new ArrayList<PersonBean>());
        mShowFanListListView.setAdapter(adapter);
        adapter.setOnUserFanItemClickListener(new UserDetailFansAdapter.OnUserFanItemClickListener() {
            @Override
            public void onUserFanItemClick(PersonBean personBean) {
                Intent intent = new Intent(ShowFanListActivity.this, UserDetailActivity.class);
                intent.putExtra("person_bean", MyApplication.getmPersonBean());
                startActivity(intent);
            }
        });
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
        Util.showSnackBar(view, "粉丝列表读取错误，请重试！");
    }

    @Override
    public void loadFanList(List<PersonBean> list) {
        adapter.refreshData(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanlist_back_ib:
                finish();
                break;
        }
    }
}
