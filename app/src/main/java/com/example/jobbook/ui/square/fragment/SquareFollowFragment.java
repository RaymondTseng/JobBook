package com.example.jobbook.ui.square.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.app.NetConstants;
import com.example.jobbook.base.LazyLoadFragment;
import com.example.jobbook.base.contract.square.SquareFollowContract;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.presenter.square.SquareFollowPresenter;
import com.example.jobbook.ui.moment.activity.MomentDetailActivity;
import com.example.jobbook.ui.person.activity.UserDetailActivity;
import com.example.jobbook.ui.square.adapter.SquareFollowsAdapter;
import com.example.jobbook.util.L;
import com.example.jobbook.util.ScreenUtil;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Xu on 2016/7/5.
 */
public class SquareFollowFragment extends LazyLoadFragment implements SquareFollowContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    private static int REFRESH = 1;

    @BindView(R.id.square_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.square_swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private SquareFollowPresenter mSquareFollowPresenter;
    private List<MomentBean> mData;
    private SquareFollowsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private MyApplication myApplication;

    private int pageIndex = 0;

    public Handler handler;

    @Override
    protected int setContentView() {
        return R.layout.fragment_square;
    }

    @Override
    protected void lazyLoad() {
        initEvents();
    }

    @Override
    public void initViews() {
    }

    private void initEvents() {
        myApplication = (MyApplication) getActivity().getApplication();
        handler = new SquareFollowHandler();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSquareFollowPresenter = new SquareFollowPresenter(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, Util.getHeight(getActivity()) / 4);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        mAdapter = new SquareFollowsAdapter(getActivity().getApplicationContext());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnContentClickListener(mOnContentClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
//        mAdapter.setOnNoInterestButtonClickListener(new SquareFollowsAdapter.OnNoInterestButtonClickListener() {
//            @Override
//            public void onNoInterestButtonClick(View view, int position) {
//                createNoInterestDialog(position);
//            }
//        });
        mAdapter.setOnHeadClickListener(new SquareFollowsAdapter.OnHeadClickListener() {
            @Override
            public void onHeadClick(View view, int position) {
                Bundle bundle = new Bundle();
                L.i(mData.get(position).getAuthor().toString());
                bundle.putParcelable("person_bean", mData.get(position).getAuthor());
                Util.toAnotherActivity(getActivity(), UserDetailActivity.class, bundle);
            }
        });
        mAdapter.setOnFavouriteButtonClickListener(new SquareFollowsAdapter.OnFavouriteButtonClickListener() {
            @Override
            public void onFavouriteButtonClick(ImageButton ib, int position) {
                if (mData.get(position).getIfLike() == 0) {
//                    L.i("like_ib_click", "click like");
                    ib.setImageResource(R.mipmap.favourite_tapped);
                    like(position);
                } else {
//                    L.i("like_ib_click", "click unlike");
                    ib.setImageResource(R.mipmap.favourite);
                    unlike(position);
                }
//                refresh();
            }
        });
        mAdapter.setOnShowAllOrHideAllListener(new SquareFollowsAdapter.OnShowAllOrHideAllListener() {
            @Override
            public void onShowAllOrHideAll(TextView view, TextView contentTextView, String content) {
                if (view.getText().toString().equals("点开全文")) {
                    contentTextView.setText(content);
                    view.setText("收起全文");
                } else {
                    contentTextView.setText(content.substring(0, 75));
                    view.setText("点开全文");
                }
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
                if (MyApplication.getmLoginStatus() != 0) {
                    mSquareFollowPresenter.loadSquareFollows(pageIndex);
                } else {
                    NoLoginError();
                }

            }
        }
    };

    private SquareFollowsAdapter.OnContentClickListener mOnContentClickListener = new SquareFollowsAdapter.OnContentClickListener() {
        @Override
        public void onContentClick(View view, int position) {
            MomentBean square = mAdapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("square_detail", square);
            myApplication.setHandler(handler);
            Util.toAnotherActivity(getActivity(), MomentDetailActivity.class, bundle);
        }
    };

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void addSquareFollows(List<MomentBean> squareList) {
        mAdapter.setmShowFooter(true);
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(squareList);
        if (squareList == null || squareList.size() < NetConstants.PAZE_SIZE) {
            mAdapter.setmShowFooter(false);
        }
        if (pageIndex == 0) {
            mAdapter.updateData(mData);
        } else {
            //如果没有更多数据了,则隐藏footer布局
//            if (squareList == null || squareList.size() == 0) {
//                mAdapter.setmShowFooter(false);
//            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex += NetConstants.PAZE_SIZE;
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        SnackBarUtil.showSnackBar(getActivity(), msg, "重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }

    private void like(int position) {
        mSquareFollowPresenter.like(mData.get(position).getS_id(), position);
    }

    private void unlike(int position) {
        mSquareFollowPresenter.unlike(mData.get(position).getS_id(), position);
    }

    @Override
    public void NoLoginError() {
        SnackBarUtil.showSnackBar(getActivity(), "请先登录！");
    }

    @Override
    public void likeSuccess(MomentBean momentBean, int position) {
        mAdapter.getmData().set(position, momentBean);
        //RecyclerView局部更新
        mAdapter.notifyItemChanged(position, "refresh");
        SnackBarUtil.showSnackBar(getActivity(), "点赞成功！");
    }

    @Override
    public void unlikeSuccess(MomentBean momentBean, int position) {
        mAdapter.getmData().set(position, momentBean);
        //RecyclerView局部更新
        mAdapter.notifyItemChanged(position, "refresh");
        SnackBarUtil.showSnackBar(getActivity(), "取消点赞成功！");
    }

    @Override
    public void onRefresh() {
        pageIndex = 0;
        if (mData != null) {
            mData.clear();
        }
        if (MyApplication.getmLoginStatus() != 0) {
            mSquareFollowPresenter.loadSquareFollows(pageIndex);
        } else {
            NoLoginError();
        }
    }

    private void createNoInterestDialog(int position) {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = ScreenUtil.dip2px(getActivity(), 300);
        p.height = ScreenUtil.dip2px(getActivity(), 140);
//        window.setLayout(Util.dip2px(getActivity(), Util.getWidth(getActivity()) * 1 / 4), Util.dip2px(getActivity(), Util.getHeight(getActivity()) * 1 / 13));
        window.setAttributes(p);
        window.setContentView(R.layout.square_no_interest_layout);
        TextView mSureTextView = (TextView) window.findViewById(R.id.square_no_interest_sure_tv);
        TextView mCancelTextView = (TextView) window.findViewById(R.id.square_no_interest_cancel_tv);
        mSureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                onCreate(null);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public class SquareFollowHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == REFRESH) {
                onRefresh();
            }
        }
    }

}
