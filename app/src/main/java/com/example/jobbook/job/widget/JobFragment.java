package com.example.jobbook.job.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.job.JobsAdapter;
import com.example.jobbook.job.presenter.JobPresenter;
import com.example.jobbook.job.presenter.JobPresenterImpl;
import com.example.jobbook.job.view.JobView;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class JobFragment extends Fragment implements JobView, View.OnFocusChangeListener,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private EditText mEditText;
    private JobPresenter mJobPresenter;
    private View view;
    private List<JobBean> list;
    private JobsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int pageIndex = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_job, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.job_rv);
        mEditText = (EditText) view.findViewById(R.id.job_et);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.job_swipe_container);
}

    private void initEvents(){
        mAdapter = new JobsAdapter(getActivity().getApplicationContext());
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mEditText.setOnFocusChangeListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mJobPresenter = new JobPresenterImpl(this);
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mEditText.setFocusable(false);
        mEditText.setOnClickListener(this);
        onRefresh();
    }

    private RecyclerView.OnScrollListener  mOnScrollListener = new RecyclerView.OnScrollListener(){

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
                Log.i("job_fragment", "loading more data");
                mJobPresenter.loadJobs(pageIndex);
            }
        }
    };
    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void addJobs(List<JobBean> jobList) {
        mAdapter.setmShowFooter(true);
        if(list == null){
            list = new ArrayList<>();
        }
        list.addAll(jobList);
        if(pageIndex == 0) {
            mAdapter.updateData(list);
        } else {
            //如果没有更多数据了,则隐藏footer布局
            if(jobList == null || jobList.size() == 0) {
                mAdapter.setmShowFooter(false);
            }
            mAdapter.notifyDataSetChanged();
        }
        Log.i("pageIndex", pageIndex + "");
        pageIndex += Urls.PAZE_SIZE;
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadingFailMsg() {
        if(pageIndex == 0) {
            mAdapter.setmShowFooter(false);
            mAdapter.notifyDataSetChanged();
        }
        view = view == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.main_layout);
        final Snackbar snackbar = Snackbar.make(view, "岗位读取错误，请重试！", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void search() {
        SearchDialogFragment fragment = new SearchDialogFragment();
        fragment.show(getActivity().getFragmentManager(), "search_dialog");
        Log.i("jobfragment", "createSearchDialog");
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.job_et:
                Log.i("jobfragment", "123");
                break;

        }
    }

    @Override
    public void onClick(View v) {
        search();
    }


    private JobsAdapter.OnItemClickListener mOnItemClickListener = new JobsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            JobBean job = mAdapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("job_detail", job);
            Util.toAnotherActivity(getActivity(), JobDetailActivity.class, bundle);
        }
    };

    @Override
    public void onRefresh() {
        Log.i("TAG", "onRefresh");
        pageIndex = 0;
        if(list != null){
            list.clear();
        }
        mJobPresenter.loadJobs(pageIndex);
    }
}
