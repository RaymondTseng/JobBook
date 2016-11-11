package com.example.jobbook.person.widget;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.jobbook.util.L;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.job.JobsAdapter;
import com.example.jobbook.job.widget.JobDetailActivity;
import com.example.jobbook.person.FavouriteJobsAdapter;
import com.example.jobbook.person.presenter.FavouritePresenter;
import com.example.jobbook.person.presenter.FavouritePresenterImpl;
import com.example.jobbook.person.view.FavouriteView;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class FavouriteActivity extends Activity implements View.OnClickListener, FavouriteView {

    private ImageButton mBackImageButton;
    private RecyclerView mRecyclerView;
    private FavouriteJobsAdapter mAdapter;
    private FavouritePresenter mPresenter;
    private List<JobBean> list;
    private LinearLayoutManager mLayoutManager;

    private int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_favourite);
        initViews();
        initEvents();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.favourite_back_ib);
        mRecyclerView = (RecyclerView) findViewById(R.id.favourite_rv);
    }

    private void initEvents() {
        mPresenter = new FavouritePresenterImpl(this);
        mPresenter.loadFavouriteJobs(pageIndex, MyApplication.getAccount());
        mBackImageButton.setOnClickListener(this);
        mAdapter = new FavouriteJobsAdapter(this);
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favourite_back_ib:
                finish();
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void addJobs(List<JobBean> mJobs) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.addAll(mJobs);
        if (pageIndex == 0) {
            mAdapter.updateData(list);
        } else {
            //如果没有更多数据了,则隐藏footer布局
            if (mJobs == null || mJobs.size() == 0) {
                mAdapter.setmShowFooter(false);
            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {

    }

    private FavouriteJobsAdapter.OnItemClickListener mOnItemClickListener = new FavouriteJobsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            JobBean job = mAdapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("job_detail", job);
            Util.toAnotherActivity(FavouriteActivity.this, JobDetailActivity.class, bundle);
        }
    };

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()
                    && mAdapter.ismShowFooter()) {
                //加载更多
                mPresenter.loadFavouriteJobs(pageIndex, MyApplication.getAccount());
            }
        }
    };

    private void refresh() {
        pageIndex = 0;
        if (list != null) {
            list.clear();
        }
        mPresenter.loadFavouriteJobs(pageIndex, MyApplication.getAccount());
    }
}
