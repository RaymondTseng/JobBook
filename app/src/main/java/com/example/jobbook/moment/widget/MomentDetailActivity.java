package com.example.jobbook.moment.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.login.widget.LoginActivity;
import com.example.jobbook.moment.MomentDetailCommentListViewAdapter;
import com.example.jobbook.moment.presenter.MomentDetailPresenter;
import com.example.jobbook.moment.presenter.MomentDetailPresenterImpl;
import com.example.jobbook.moment.view.MomentDetailView;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class MomentDetailActivity extends Activity implements MomentDetailView, View.OnClickListener,
        View.OnLayoutChangeListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private ImageButton mSendImageButton;
    private ImageButton mBackImageButton;
    private ImageButton mFavouriteImageButton;
    private MomentDetailPresenter mPresenter;
    private TextView mMomentContentTextView;
    private TextView mMomentUserNameTextView;
    private TextView mMomentTimeTextView;
    private ImageView mMomentUserLogoImageView;
    private TextView mMomentFavouriteTextView;
    private TextView mMomentCommentTextView;
    private MomentDetailCommentListViewAdapter mAdapter;
    private MomentBean mMomentBean;
    private LinearLayout mHeadView;
    //    private MomentDetailPresenter presenter;
    private ScrollView mRootView;
    private List<MomentCommentBean> list;
    private RelativeLayout mTitleBarLayout;
    private LinearLayout mInputLayout;
//    private LinearLayout mRecyclerViewLayout;
    private ViewStub mLoadingLinearLayout;
    private LinearLayoutManager mLayoutManager;
    private MyApplication myApplication;
    private int mScreenHeight;
    private int mKeyBoardHeight;
    private float mTitleBarHeight;
    private float mInputLayoutHeight;
    private int id;
    private View view;
    private int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_detail);
        view = getWindow().getDecorView();
        initViews();
        initEvents();
    }

    private void initViews() {
        mHeadView = (LinearLayout) getLayoutInflater().inflate(R.layout.moment_detail_head_ll, null);
        mRecyclerView = (RecyclerView) findViewById(R.id.moment_detail_rv);
        mEditText = (EditText) findViewById(R.id.moment_detail_comment_et);
        mSendImageButton = (ImageButton) findViewById(R.id.moment_detail_send_ib);
        mBackImageButton = (ImageButton) findViewById(R.id.moment_detail_back_ib);
        mFavouriteImageButton = (ImageButton) findViewById(R.id.moment_detail_favourite_ib);
        mMomentContentTextView = (TextView) mHeadView.findViewById(R.id.moment_detail_content_tv);
        mMomentUserNameTextView = (TextView) mHeadView.findViewById(R.id.moment_detail_name_tv);
        mMomentTimeTextView = (TextView) mHeadView.findViewById(R.id.moment_detail_time_tv);
        mMomentUserLogoImageView = (ImageView) mHeadView.findViewById(R.id.moment_detail_head_iv);
        mMomentFavouriteTextView = (TextView) mHeadView.findViewById(R.id.moment_detail_favourite_tv);
        mMomentCommentTextView = (TextView) mHeadView.findViewById(R.id.moment_detail_comment_tv);
        mRootView = (ScrollView)findViewById(R.id.question_detail_root_ll);
        mTitleBarLayout = (RelativeLayout) findViewById(R.id.moment_detail_title_bar);
        mInputLayout = (LinearLayout) findViewById(R.id.moment_detail_input_ll);
//        mRecyclerViewLayout = (LinearLayout) findViewById(R.id.moment_detail_rv_ll);
        mLoadingLinearLayout = (ViewStub) findViewById(R.id.activity_moment_detail_loading);
        mLoadingLinearLayout.inflate();
    }

    private void initEvents() {
        list = new ArrayList<>();
        mScreenHeight = Util.getHeight(this);
        mKeyBoardHeight = mScreenHeight / 3;
        mTitleBarHeight = ((float) mScreenHeight / 568) * 56;
        mInputLayoutHeight = mTitleBarHeight;
        mPresenter = new MomentDetailPresenterImpl(this);

        mMomentBean = (MomentBean) getIntent().getSerializableExtra("square_detail");
        if (mMomentBean != null) {
            id = mMomentBean.getId();
            L.i("squaredetail_activity", "123:" + mMomentBean.getId());
            mPresenter.loadMoment(mMomentBean);
        } else {
            String id = getIntent().getStringExtra("moment_id_from_message");
            mPresenter.loadMomentById(id, MyApplication.getAccount());
        }

//        mPresenter.loadMomentComments(momentBean.getId());
        mAdapter = new MomentDetailCommentListViewAdapter(this);
        mAdapter.setmHeaderView(mHeadView);
        mRootView.setOnClickListener(this);
        mBackImageButton.setOnClickListener(this);
        mSendImageButton.setOnClickListener(this);
        mFavouriteImageButton.setOnClickListener(this);
//        mRootView.addOnLayoutChangeListener(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        myApplication = (MyApplication) getApplication();
        onRefresh();
//        mAdapter.setOnLikeClickListener(new OnLikeClickListener() {
//            @Override
//            public void onLikeClickListener(int com_id) {
//                if (MyApplication.getmLoginStatus() == 0) {
//                    Toast.makeText(MomentDetailActivity.this, "请先登录！", Toast.LENGTH_LONG).show();
//                } else {
//                    mPresenter.commentLike(com_id, MyApplication.getAccount());
//                }
//            }
//        });
//        mAdapter.setOnUnlikeClickListener(new OnUnlikeClickListener() {
//            @Override
//            public void onUnlikeClickListener(int com_id) {
//                if (MyApplication.getmLoginStatus() == 0) {
//                    Toast.makeText(MomentDetailActivity.this, "请先登录！", Toast.LENGTH_LONG).show();
//                } else {
////                    L.i("squaredetail", "comment unlike" + com_id);
//                    mPresenter.commentUnlike(com_id, MyApplication.getAccount());
//                }
//            }
//        });
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void addMoment(MomentBean mMoment) {
        L.i("momentdetail", "result:" + mMoment.toString());
        mMomentContentTextView.setText(mMoment.getContent());
        mMomentUserNameTextView.setText(mMoment.getAuthor().getUsername());
        mMomentTimeTextView.setText(mMoment.getDate());
        ImageLoadUtils.display(this, mMomentUserLogoImageView, mMoment.getAuthor().getHead());
        mMomentFavouriteTextView.setText(mMoment.getLikesNum() + "");
        mMomentCommentTextView.setText(mMoment.getCommentNum() + "");
        if (mMoment.getIfLike() == 0) {
            mFavouriteImageButton.setImageResource(R.mipmap.favourite);
        } else {
            mFavouriteImageButton.setImageResource(R.mipmap.favourite_tapped);
        }
    }

    @Override
    public void addComments(List<MomentCommentBean> mComments) {
//        mListView.removeHeaderView(mHeadView);
//        list = mComments;
//        mListView.addHeaderView(mHeadView);
//        Util.setListViewHeightBasedOnChildren(mListView);
        // 加载数据的地方
        L.i("momentdetailacti", "size:" + mComments.size());
        mAdapter.setmShowFooter(true);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.addAll(mComments);
        if (mComments == null || mComments.size() < Urls.PAZE_SIZE) {
            mAdapter.setmShowFooter(false);
        }
        if (pageIndex == 0) {
            mAdapter.updateData(list);
        } else {
            //如果没有更多数据了,则隐藏footer布局
//            if (mComments == null || mComments.size() == 0) {
//                mAdapter.setmShowFooter(false);
//            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }


    @Override
    public void sendSuccess(MomentBean momentBean) {
        onRefresh();
        mMomentBean = momentBean;
        mEditText.setText("");
        mMomentCommentTextView.setText(mMomentBean.getCommentNum() + "");
        mMomentFavouriteTextView.setText(mMomentBean.getLikesNum() + "");
        Util.showSnackBar(view, "评论成功!");
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(int error) {
        switch (error) {
            case 0:
                Util.showSnackBar(view, "工作圈加载错误,请重试!");
                break;
            case 1:
                Util.showSnackBar(view, "评论加载错误,请重试！");
                break;
            case 2:
                Util.showSnackBar(view, "发表评论失败！");
                break;
        }
    }

    @Override
    public String getComment() {
        return mEditText.getText().toString();
    }

    @Override
    public void editTextBlankError() {
        Util.showSnackBar(view, "评论不能为空！");
    }

    @Override
    public void noLoginError() {
        Util.showSnackBar(view, "请先登录", "现在登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.toAnotherActivity(MomentDetailActivity.this, LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    public void sendComment(String comment) {
        if (MyApplication.getmLoginStatus() == 0) {
            noLoginError();
        } else {
            MomentCommentBean momentCommentBean = new MomentCommentBean();
            momentCommentBean.setQ_id(id);
            momentCommentBean.setApplier(MyApplication.getmPersonBean());
            momentCommentBean.setContent(comment);
//            L.i("square_detail", momentBean.getId() + "");
//            momentCommentBean.setQ_id(momentBean.getId());
            mPresenter.sendComment(momentCommentBean);
        }
    }

    @Override
    public void likeSuccess(MomentBean momentBean) {
        mFavouriteImageButton.setImageResource(R.mipmap.favourite_tapped);
        mMomentFavouriteTextView.setText(momentBean.getLikesNum() + "");
        mMomentCommentTextView.setText(momentBean.getCommentNum() + "");
        Toast.makeText(MomentDetailActivity.this, "评论点赞成功！", Toast.LENGTH_LONG).show();
//        mPresenter.loadMomentComments(momentBean.getId());
    }

    @Override
    public void likeFailure(String msg) {
        Toast.makeText(MomentDetailActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void unlikeSuccess(MomentBean momentBean) {
        mFavouriteImageButton.setImageResource(R.mipmap.favourite);
        mMomentFavouriteTextView.setText(momentBean.getLikesNum() + "");
        mMomentCommentTextView.setText(momentBean.getCommentNum() + "");
        Toast.makeText(MomentDetailActivity.this, "取消点赞成功！", Toast.LENGTH_LONG).show();
//        mPresenter.loadMomentComments(momentBean.getId());
    }

    @Override
    public void unlikeFailure(String msg) {
        Toast.makeText(MomentDetailActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.question_detail_root_ll:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.moment_detail_back_ib:
                if (myApplication.getHandler() != null) {
                    myApplication.getHandler().sendEmptyMessage(1);
                    myApplication.setHandler(null);
                }
                finish();
                break;
            case R.id.moment_detail_send_ib:
                if (TextUtils.isEmpty(getComment())) {
                    editTextBlankError();
                } else {
                    sendComment(getComment());
                }
                break;

            case R.id.moment_detail_favourite_ib:
                if (mMomentBean.getIfLike() == 0) {
                    mPresenter.commentLike(mMomentBean.getId(), myApplication.getAccount());

                } else {
                    mPresenter.commentUnlike(mMomentBean.getId(), myApplication.getAccount());
                }
                break;
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > mKeyBoardHeight)) {
            int dValue = oldBottom - bottom;
            int newHeight = mScreenHeight - dValue;
            L.i("square_detail", "newHeight:" + newHeight + "Height:" + mScreenHeight + "mTitleBarHeight:" + mTitleBarHeight);
//            mTitleBarLayout.setLayoutParams(new RelativeLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
//                    MATCH_PARENT, 0, (mTitleBarHeight / newHeight) * 568)));
            mTitleBarLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, (mTitleBarHeight / newHeight) * 568));
            mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, ((newHeight - 2 * mTitleBarHeight) / newHeight) * 568));
            mInputLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, (mInputLayoutHeight / newHeight) * 568));
            mTitleBarLayout.invalidate();
            mRecyclerView.invalidate();
            mInputLayout.invalidate();
            L.i("square_detail", "mTitleBarLayout:" + mTitleBarLayout.getHeight());
            L.i("square_detail", "mRecyclerView:" + mRecyclerView.getHeight());
            L.i("square_detail", "mInputLayout:" + mInputLayout.getHeight());
            L.i("square_detail", "软键盘弹起");

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > mKeyBoardHeight)) {
            mTitleBarLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 56));
            mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 456));
            mInputLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 56));
            mTitleBarLayout.invalidate();
            mRecyclerView.invalidate();
            mInputLayout.invalidate();
            L.i("square_detail", "mTitleBarLayout:" + mTitleBarLayout.getHeight());
            L.i("square_detail", "mRecyclerView:" + mRecyclerView.getHeight());
            L.i("square_detail", "mInputLayout:" + mInputLayout.getHeight());
            L.i("square_detail", "软键盘关闭");

        }
    }


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisiableItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisiableItem + 1 == mAdapter.getItemCount()
                    && mAdapter.ismShowFooter()) {
                //加载更多
                L.i("article_fragment", "loading more data");
                mPresenter.loadMomentComments(id, pageIndex);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisiableItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };

    private MomentDetailCommentListViewAdapter.OnItemClickListener mOnItemClickListener = new MomentDetailCommentListViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
//            MomentCommentBean comment = mAdapter.getItem(position);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("article_detail", comment);
//            Util.toAnotherActivity(this, ArticleDetailActivity.class, bundle);
        }
    };

    @Override
    public void onRefresh() {
        L.i("TAG", "onRefresh");
        pageIndex = 0;
        if (list != null) {
            list.clear();
        }
//        presenter = new MomentDetailPresenterImpl(this);
        mPresenter.loadMomentComments(id, pageIndex);
        mAdapter.notifyDataSetChanged();
    }
}
