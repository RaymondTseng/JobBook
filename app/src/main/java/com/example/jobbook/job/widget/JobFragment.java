package com.example.jobbook.job.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.app.constants.Urls;
import com.example.jobbook.job.JobsAdapter;
import com.example.jobbook.job.SpinnerTitleAdapter;
import com.example.jobbook.job.presenter.JobPresenter;
import com.example.jobbook.job.presenter.JobPresenterImpl;
import com.example.jobbook.job.view.JobView;
import com.example.jobbook.ui.DividerItemDecoration;
import com.example.jobbook.util.L;
import com.example.jobbook.base.LazyLoadFragment;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class JobFragment extends LazyLoadFragment implements JobView,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private JobPresenter mJobPresenter;
    private List<JobBean> list;
    private JobsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
//    private ImageButton mSearchImageButton;
    private TextView mRecommendTextView;
    private AppCompatSpinner mCategorySpinner;
    private ArrayAdapter mCategoryAdapter;
    private AppCompatSpinner mLocationSpinner;
    private ArrayAdapter mLocationAdapter;

    private ImageView cursorOne;
    private ImageView cursorTwo;
    private ImageView cursorThree;

    private int pageIndex = 0;

    private String mCurrentCategory;
    private String mCurrentLocation;
    private boolean isRecommend;
    private int currentSelection = 0;

    private boolean isFirst;
    private boolean isInitCursorFirst = true;

    @Override
    protected int setContentView() {
        return R.layout.fragment_job;
    }

    @Override
    protected void lazyLoad() {
        initViews();
        initEvents();
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.job_rv);
//        mSearchImageButton = findViewById(R.id.job_fragment_search_ib);
        mSwipeRefreshLayout = findViewById(R.id.job_swipe_container);
        mRecommendTextView = findViewById(R.id.job_fragment_recommend_tv);
        mCategorySpinner = findViewById(R.id.job_fragment_category_sp);
        mLocationSpinner = findViewById(R.id.job_fragment_location_sp);

        cursorOne = findViewById(R.id.job_cursor_one);
        cursorTwo = findViewById(R.id.job_cursor_two);
        cursorThree = findViewById(R.id.job_cursor_three);
    }

    private void initEvents() {
        mAdapter = new JobsAdapter(getActivity());
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mJobPresenter = new JobPresenterImpl(this);
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mAdapter.setOnFooterItemClickListener(mOnFooterItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, Util.getHeight(getActivity()) / 4);

        mCategoryAdapter = new ArrayAdapter(getActivity(), R.layout.job_header_spinner_item_select, getResources().getStringArray(R.array.category));
        mCategoryAdapter.setDropDownViewResource(R.layout.job_header_spinner_item_drop);
        mCategorySpinner.setAdapter(mCategoryAdapter);
        mCategorySpinner.setAdapter(
                new SpinnerTitleAdapter(
                        mCategoryAdapter,
                        R.layout.job_header_spinner_category_title,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        getActivity()));

        mLocationAdapter = new ArrayAdapter(getActivity(), R.layout.job_header_spinner_item_select, getResources().getStringArray(R.array.location));
        mLocationAdapter.setDropDownViewResource(R.layout.job_header_spinner_item_drop);
        mLocationSpinner.setAdapter(mLocationAdapter);
        mLocationSpinner.setAdapter(
                new SpinnerTitleAdapter(
                        mLocationAdapter,
                        R.layout.job_header_spinner_location_title,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        getActivity()));

//        mSearchImageButton.setOnClickListener(this);
        mRecommendTextView.setOnClickListener(this);
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                L.i("itemselected");
                currentSelection = 1;
                cursorCheckStatus(currentSelection);
                if (position == 0) {

                } else {
                    String[] categorys = getResources().getStringArray(R.array.category);
                    if (position == categorys.length) {
                        mCurrentCategory = null;
                    } else {
                        mCurrentCategory = categorys[position - 1];
                    }
                    pageIndex = 0;
                    list = null;
                    isRecommend = false;
                    L.i("category spinner:" + "isRecommend:" + isRecommend + " mCurrentCategory:" + mCurrentCategory + " mCurrentLocation:" + mCurrentLocation + " pageIndex:" + pageIndex);
                    mJobPresenter.loadJobs(pageIndex, isRecommend, mCurrentCategory, mCurrentLocation);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                L.i("itemselected");
                currentSelection = 2;
                cursorCheckStatus(currentSelection);
                if (position == 0) {

                } else {
                    String[] locations = getResources().getStringArray(R.array.location);
                    if (position == locations.length) {
                        mCurrentLocation = null;
                    } else {
                        mCurrentLocation = locations[position - 1];
                    }
                    pageIndex = 0;
                    list = null;
                    isRecommend = false;
                    L.i("location spinner:" + "isRecommend:" + isRecommend + " mCurrentCategory:" + mCurrentCategory + " mCurrentLocation:" + mCurrentLocation + " pageIndex:" + pageIndex);
                    mJobPresenter.loadJobs(pageIndex, isRecommend, mCurrentCategory, mCurrentLocation);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        onRefresh();

        isFirst = true;
        if (isFirst) {
            currentSelection = 0;
            cursorCheckStatus(currentSelection);
            isRecommend = true;
            pageIndex = 0;
            list = null;
            mJobPresenter.loadJobs(pageIndex, isRecommend, mCurrentCategory, mCurrentLocation);
            isFirst = false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        isInitCursorFirst = true;
    }

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
                mJobPresenter.loadJobs(pageIndex, isRecommend, mCurrentCategory, mCurrentLocation);
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
        if (list == null) {
            list = new ArrayList<>();
        }
        list.addAll(jobList);
        if(jobList == null || jobList.size() < Urls.PAZE_SIZE){
            mAdapter.setmShowFooter(false);
        }
        if (pageIndex == 0) {
            mAdapter.updateData(list);
        } else {
            //如果没有更多数据了,则隐藏footer布局
//            if (jobList == null || jobList.size() == 0) {
//                mAdapter.setmShowFooter(false);
//            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg(String msg) {

    }

    @Override
    public void showLoadingFailMsg() {
        if (pageIndex == 0) {
            mAdapter.setmShowFooter(false);
            mAdapter.notifyDataSetChanged();
        }
        Util.showSnackBar(MyApplication.mSnackBarView, "网络无法连接！", "重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJobPresenter.loadJobs(pageIndex, isRecommend, mCurrentCategory, mCurrentLocation);
            }
        });
    }

    @Override
    public void search() {
//        SearchDialogFragment fragment = new SearchDialogFragment();
//        fragment.show(getActivity().getFragmentManager(), "search_dialog");
//        L.i("createSearchDialog");
    }

//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        switch (v.getId()) {
//            case R.id.job_et:
//                L.i("jobfragment", "123");
//                break;
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.job_fragment_search_ib:
//                search();
//                break;

            case R.id.job_fragment_recommend_tv:
                currentSelection = 0;
                cursorCheckStatus(currentSelection);
                isRecommend = true;
                pageIndex = 0;
                list = null;
                mJobPresenter.loadJobs(pageIndex, isRecommend, mCurrentCategory, mCurrentLocation);
                isRecommend = false;
                break;

        }

    }

    private JobsAdapter.OnItemClickListener mOnItemClickListener = new JobsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            JobBean job = mAdapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("job_detail", job);
            Util.toAnotherActivity(getActivity(), JobDetailActivity.class, bundle);
        }
    };

    private JobsAdapter.OnFooterItemClickListener mOnFooterItemClickListener = new JobsAdapter.OnFooterItemClickListener() {
        @Override
        public void onFooterItemClick(View view, int position) {
            mJobPresenter.loadJobs(pageIndex, isRecommend, mCurrentCategory, mCurrentLocation);
        }
    };

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if (list != null) {
            list.clear();
        }
        mJobPresenter.loadJobs(pageIndex, isRecommend, mCurrentCategory, mCurrentLocation);
        mAdapter.notifyDataSetChanged();
    }

    private void cursorCheckStatus(int count) {
//        int one = offset * 2 + cursorWidth;// 1 -> 2 偏移量
//        int two = one * 2; //1 -> 3 偏移量
//        Animation animation = null;
        switch (count) {
            case 0:
                cursorOne.setVisibility(View.VISIBLE);
                cursorTwo.setVisibility(View.GONE);
                cursorThree.setVisibility(View.GONE);
//                if (currentSelection == 2) {
////                    matrix.postTranslate(-two, 0);
////                    cursorOne.scrollBy(two, 0);
////                    cursorOne.setImageMatrix(matrix);
//                    animation = new TranslateAnimation(two + offset, offset, 0, 0);  // 设置动画位置
//                } else if (currentSelection == 1) {
////                    matrix.postTranslate(-one, 0);
////                    cursorOne.scrollBy(one, 0);
////                    cursorOne.setImageMatrix(matrix);
//                    animation = new TranslateAnimation(one + offset, offset, 0, 0);  // 设置动画位置
//                }
                L.i("click 0");
//                currentSelection = 0;
                break;

            case 1:
                cursorOne.setVisibility(View.GONE);
                cursorTwo.setVisibility(View.VISIBLE);
                cursorThree.setVisibility(View.GONE);
//                if (currentSelection == 0) {
////                    matrix.postTranslate(-one, 0);
////                    cursorOne.setImageMatrix(matrix);// 设置动画位置
////                    cursorOne.scrollBy(one, cursorHeight);
//                    animation = new TranslateAnimation(one + offset, offset, 0, 0);  // 设置动画位置
//                } else if (currentSelection == 2) {
////                    matrix.postTranslate(one, 0);
////                    cursorOne.setImageMatrix(matrix);// 设置动画位置
////                    cursorOne.scrollBy(-one, cursorHeight);
//                    animation = new TranslateAnimation(two + offset, one + offset, 0, 0);  // 设置动画位置
//                }
//                currentSelection = 1;
                break;
            case 2:
                cursorOne.setVisibility(View.GONE);
                cursorTwo.setVisibility(View.GONE);
                cursorThree.setVisibility(View.VISIBLE);
//                if (currentSelection == 0) {
////                    matrix.postTranslate(two, 0);
////                    cursorOne.setImageMatrix(matrix);// 设置动画位置
////                    cursorOne.scrollBy(-two, cursorHeight);
//                    animation = new TranslateAnimation(offset, two + offset, 0, 0);  // 设置动画位置
//                } else if (currentSelection == 1) {
////                    matrix.postTranslate(one, 0);
////                    cursorOne.setImageMatrix(matrix);// 设置动画位置
////                    cursorOne.scrollBy(-one, cursorHeight);
//                    animation = new TranslateAnimation(one + offset, two + offset, 0, 0);  // 设置动画位置
//                }
                L.i("click 2");
//                currentSelection = 2;
                if (isInitCursorFirst) {
                    cursorOne.setVisibility(View.VISIBLE);
                    cursorTwo.setVisibility(View.GONE);
                    cursorThree.setVisibility(View.GONE);
                    isInitCursorFirst = false;
                }
                break;

            default:
                break;
        }

    }

}
