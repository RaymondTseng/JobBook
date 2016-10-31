package com.example.jobbook.search.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.job.widget.JobDetailActivity;
import com.example.jobbook.main.widget.MainActivity;
import com.example.jobbook.search.SearchJobsAdapter;
import com.example.jobbook.search.presenter.SearchPresenter;
import com.example.jobbook.search.presenter.SearchPresenterImpl;
import com.example.jobbook.search.view.SearchView;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/8/24.
 */
public class SearchActivity extends Activity implements View.OnClickListener, SearchView {

    private ImageButton mBackImageButton;
    private ImageButton mSearchImageButton;
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
        mSearchImageButton = (ImageButton) findViewById(R.id.job_search_activity_search_ib);
        mBackImageButton.setOnClickListener(this);
        mSearchImageButton.setOnClickListener(this);
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

        mSearchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (!TextUtils.isEmpty(mSearchEditText.getText())) {
                        presenter.search(mSearchEditText.getText().toString(), pageIndex = 0);
                    } else {
                        Toast.makeText(SearchActivity.this, "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.job_search_activity_back_ib:
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.job_search_activity_search_ib:
                if (!TextUtils.isEmpty(mSearchEditText.getText())) {
                    presenter.search(mSearchEditText.getText().toString(), pageIndex = 0);
                }
                break;
        }
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
        list = jobList;
        if(pageIndex == 0) {
            adapter.updateData(list);
        } else {
            //如果没有更多数据了,则隐藏footer布局
            if(jobList == null || jobList.size() == 0) {
                adapter.setmShowFooter(false);
            }
            adapter.notifyDataSetChanged();
        }
        L.i("pageIndex", pageIndex + "");
        pageIndex += Urls.PAZE_SIZE;
    }

    @Override
    public void getSearchError() {
        Toast.makeText(SearchActivity.this, "搜索错误！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void getSearchEmpty() {
        Toast.makeText(SearchActivity.this, "无结果！", Toast.LENGTH_LONG).show();
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
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()
                    && adapter.ismShowFooter()) {
                //加载更多
                L.i("search", "loading more data");
                presenter.search(mSearchContent, pageIndex);
            }
        }
    };
}
