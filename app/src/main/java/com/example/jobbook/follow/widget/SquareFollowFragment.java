package com.example.jobbook.follow.widget;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.follow.SquareFollowsAdapter;
import com.example.jobbook.follow.presenter.SquareFollowPresenter;
import com.example.jobbook.follow.presenter.SquareFollowPresenterImpl;
import com.example.jobbook.follow.view.SquareFollowView;
import com.example.jobbook.moment.widget.MomentDetailActivity;
import com.example.jobbook.moment.widget.MomentFragment;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class SquareFollowFragment extends Fragment implements SquareFollowView,
        SwipeRefreshLayout.OnRefreshListener {

    private static int REFRESH = 1;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SquareFollowPresenter mSquareFollowPresenter;
    private List<MomentBean> mData;
    private View view;
    private SquareFollowsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private MyApplication myApplication;

    private int pageIndex = 0;

    public final Handler handler = new Handler() {
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
        view = inflater.inflate(R.layout.fragment_square, container, false);
        initViews(view);
        initEvents();
        L.i("squareFollowfragment", "create");
        return view;
    }

    public void initViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.square_rv);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.square_swipe_container);
    }

    private void initEvents() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSquareFollowPresenter = new SquareFollowPresenterImpl(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, Util.getHeight(getActivity()) / 4);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        mAdapter = new SquareFollowsAdapter(getActivity().getApplicationContext());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
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

            }
        });
        mAdapter.setOnFavouriteButtonClickListener(new SquareFollowsAdapter.OnFavouriteButtonClickListener() {
            @Override
            public void onFavouriteButtonClick(ImageButton ib, int position) {
                if (mData.get(position).getIfLike() == 0) {
//                    L.i("like_ib_click", "click like");
                    ib.setImageResource(R.mipmap.favourite_tapped);
                    like(mData.get(position).getId());
                } else {
//                    L.i("like_ib_click", "click unlike");
                    ib.setImageResource(R.mipmap.favourite);
                    unlike(mData.get(position).getId());
                }
//                refresh();
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
                L.i("square_fragment", "loading more data");
                if(MyApplication.getmLoginStatus() != 0){
                    mSquareFollowPresenter.loadSquareFollows(pageIndex, MyApplication.getAccount());
                }else{
                    NoLoginError();
                }

            }
        }
    };

    private SquareFollowsAdapter.OnItemClickListener mOnItemClickListener = new SquareFollowsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            MomentBean square = mAdapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("square_detail", square);
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
        if (pageIndex == 0) {
            mAdapter.updateData(mData);
        } else {
            //如果没有更多数据了,则隐藏footer布局
            if (squareList == null || squareList.size() == 0) {
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
        Util.showSnackBar(view, "网络无法连接！", "重试");
    }

    @Override
    public void like(String squareId) {
        mSquareFollowPresenter.like(squareId, MyApplication.getAccount());
    }

    @Override
    public void unlike(String squareId) {
        mSquareFollowPresenter.unlike(squareId, MyApplication.getAccount());
    }

    @Override
    public void NoLoginError() {
        view = getActivity().getWindow().getDecorView();
        Util.showSnackBar(view, "请先登录！");
    }

    @Override
    public void likeSuccess() {

    }

    @Override
    public void unlikeSuccess() {

    }

    @Override
    public void likeError() {
        Util.showSnackBar(view, "点赞失败！");
    }

    @Override
    public void unlikeError() {
        Util.showSnackBar(view, "取消点赞失败！");
    }

    @Override
    public void onRefresh() {
        L.i("TAG", "onRefresh");
        pageIndex = 0;
        if (mData != null) {
            mData.clear();
        }
        if(MyApplication.getmLoginStatus() != 0){
            mSquareFollowPresenter.loadSquareFollows(pageIndex, MyApplication.getAccount());
        }else{
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
//        p.width = Util.dip2px(getActivity(), 280);
//        p.height = Util.dip2px(getActivity(), 109);
        p.width = Util.dip2px(getActivity(), 300);
        p.height = Util.dip2px(getActivity(), 140);
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


}
