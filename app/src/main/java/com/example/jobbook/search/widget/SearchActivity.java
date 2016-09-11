package com.example.jobbook.search.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.job.JobsAdapter;
import com.example.jobbook.job.widget.JobDetailActivity;
import com.example.jobbook.main.widget.MainActivity;
import com.example.jobbook.search.SearchJobsAdapter;
import com.example.jobbook.search.presenter.SearchPresenter;
import com.example.jobbook.search.presenter.SearchPresenterImpl;
import com.example.jobbook.search.view.SearchView;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/8/24.
 */
public class SearchActivity extends Activity implements View.OnClickListener, SearchView {

    private ImageButton mBackImageButton;
    private EditText mSearchEditText;
    private SearchPresenter presenter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private SearchJobsAdapter adapter;
    private String mSearchContent;
    private List<JobBean> list;
    private LinearLayout mLoadingLinearLayout;

    private int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_search_activity);
        initViews();
        initEvents();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.job_search_activity_back_ib);
        mBackImageButton.setOnClickListener(this);
        mSearchEditText = (EditText) findViewById(R.id.job_search_activity_et);
        mRecyclerView = (RecyclerView) findViewById(R.id.job_search_activity_rv);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    private void initEvents() {
        adapter = new SearchJobsAdapter(this);
        adapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSearchEditText.setText(bundle.getString("content"));
        }
        presenter = new SearchPresenterImpl(this);
        mSearchContent = bundle.getString("content");
        presenter.search(mSearchContent, pageIndex);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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
    public void getSearchResult(List<JobBean> jobList) {
        adapter.setmShowFooter(true);
        if(list == null){
            list = new ArrayList<>();
        }
        list.addAll(jobList);
        if(pageIndex == 0) {
            adapter.updateData(list);
        } else {
            //如果没有更多数据了,则隐藏footer布局
            if(jobList == null || jobList.size() == 0) {
                adapter.setmShowFooter(false);
            }
            adapter.notifyDataSetChanged();
        }
        Log.i("pageIndex", pageIndex + "");
        pageIndex += Urls.PAZE_SIZE;
    }

    @Override
    public void getSearchError() {

    }

    private SearchJobsAdapter.OnItemClickListener mOnItemClickListener = new SearchJobsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            JobBean job = adapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("job_detail", job);
            Util.toAnotherActivity(SearchActivity.this, JobDetailActivity.class, bundle);
        }
    };

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener(){
        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            presenter.search(mSearchContent, pageIndex);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()
                    && adapter.ismShowFooter()) {
                //加载更多
                Log.i("search", "loading more data");
                presenter.search(mSearchContent, pageIndex);
            }
        }
    };
}
