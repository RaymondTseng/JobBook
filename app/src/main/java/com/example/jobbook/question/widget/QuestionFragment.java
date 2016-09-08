package com.example.jobbook.question.widget;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.question.QuestionAdapter;
import com.example.jobbook.question.presenter.QuestionPresenter;
import com.example.jobbook.question.presenter.QuestionPresenterImpl;
import com.example.jobbook.question.view.QuestionView;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class QuestionFragment extends Fragment implements QuestionView,
        SwipeRefreshLayout.OnRefreshListener {

    private static int REFRESH = 1;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton mNewQuestionFAB;
    private QuestionPresenter mQuestionPresenter;
    private List<QuestionBean> mData;
    private View view;
    private QuestionAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private MyApplication myApplication;

    private int pageIndex = 0;

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == REFRESH) {
                onRefresh();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question, container, false);
        initViews(view);
        initEvents();
        Log.i("questionfragment", "create");
        return view;
    }

    public void initViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.question_rv);
        mNewQuestionFAB = (FloatingActionButton) view.findViewById(R.id.question_add_fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.question_swipe_container);
    }

    private void initEvents() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mQuestionPresenter = new QuestionPresenterImpl(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, Util.getHeight(getActivity()) / 4);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        mAdapter = new QuestionAdapter(getActivity().getApplicationContext());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mNewQuestionFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApplication = (MyApplication) getActivity().getApplication();
                myApplication.setHandler(handler);
                Util.toAnotherActivity(getActivity(), NewQuestionActivity.class);
            }
        });
        onRefresh();
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
                Log.i("question_fragment", "loading more data");
                mQuestionPresenter.loadQuestion(pageIndex);
            }
        }
    };

    private QuestionAdapter.OnItemClickListener mOnItemClickListener = new QuestionAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            QuestionBean question = mAdapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("question_detail", question);
            Util.toAnotherActivity(getActivity(), QuestionDetailActivity.class, bundle);
        }
    };

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void addQuestions(List<QuestionBean> questionList) {
        mAdapter.setmShowFooter(true);
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(questionList);
        if (pageIndex == 0) {
            mAdapter.updateData(mData);
        } else {
            //如果没有更多数据了,则隐藏footer布局
            if (questionList == null || questionList.size() == 0) {
                mAdapter.setmShowFooter(false);
            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if (pageIndex == 0) {
            mAdapter.setmShowFooter(false);
            mAdapter.notifyDataSetChanged();
        }
        view = view == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.main_layout);
        final Snackbar snackbar = Snackbar.make(view, "问问读取错误，请重试！", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void onRefresh() {
        Log.i("TAG", "onRefresh");
        pageIndex = 0;
        if (mData != null) {
            mData.clear();
        }
        mQuestionPresenter.loadQuestion(pageIndex);
    }
}
