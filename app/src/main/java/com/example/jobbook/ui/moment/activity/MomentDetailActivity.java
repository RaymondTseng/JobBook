package com.example.jobbook.ui.moment.activity;

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

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.app.NetConstants;
import com.example.jobbook.base.contract.moment.MomentDetailContract;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.bean.MomentCommentBean;
import com.example.jobbook.presenter.moment.MomentDetailPresenter;
import com.example.jobbook.ui.account.activity.LoginActivity;
import com.example.jobbook.ui.moment.adapter.MomentDetailCommentListViewAdapter;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class MomentDetailActivity extends Activity implements MomentDetailContract.View,
        View.OnLayoutChangeListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.moment_detail_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.moment_detail_comment_et)
    EditText mEditText;

    @BindView(R.id.moment_detail_send_ib)
    ImageButton mSendImageButton;

    @BindView(R.id.moment_detail_back_ib)
    ImageButton mBackImageButton;

    @BindView(R.id.moment_detail_favourite_ib)
    ImageButton mFavouriteImageButton;

    @BindView(R.id.question_detail_root_ll)
    ScrollView mRootView;

    @BindView(R.id.moment_detail_title_bar)
    RelativeLayout mTitleBarLayout;

    @BindView(R.id.moment_detail_input_ll)
    LinearLayout mInputLayout;

    @BindView(R.id.activity_moment_detail_loading)
    ViewStub mLoadingLinearLayout;

    private TextView mMomentContentTextView;
    private TextView mMomentUserNameTextView;
    private TextView mMomentTimeTextView;
    private ImageView mMomentUserLogoImageView;
    private TextView mMomentFavouriteTextView;
    private TextView mMomentCommentTextView;
    private MomentDetailPresenter mPresenter;
    private MomentDetailCommentListViewAdapter mAdapter;
    private MomentBean mMomentBean;
    private LinearLayout mHeadView;
    private List<MomentCommentBean> list;
//    private LinearLayout mRecyclerViewLayout;
    private LinearLayoutManager mLayoutManager;
    private MyApplication myApplication;
    private int mScreenHeight;
    private int mKeyBoardHeight;
    private float mTitleBarHeight;
    private float mInputLayoutHeight;
    private int id;
    private int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_detail);
        ButterKnife.bind(this);
        initViews();
        initEvents();
    }

    private void initViews() {
        mHeadView = (LinearLayout) getLayoutInflater().inflate(R.layout.moment_detail_head_ll, null);
        mMomentContentTextView = (TextView) mHeadView.findViewById(R.id.moment_detail_content_tv);
        mMomentUserNameTextView = (TextView) mHeadView.findViewById(R.id.moment_detail_name_tv);
        mMomentTimeTextView = (TextView) mHeadView.findViewById(R.id.moment_detail_time_tv);
        mMomentUserLogoImageView = (ImageView) mHeadView.findViewById(R.id.moment_detail_head_iv);
        mMomentFavouriteTextView = (TextView) mHeadView.findViewById(R.id.moment_detail_favourite_tv);
        mMomentCommentTextView = (TextView) mHeadView.findViewById(R.id.moment_detail_comment_tv);
    }

    private void initEvents() {
        list = new ArrayList<>();
        mScreenHeight = Util.getHeight(this);
        mKeyBoardHeight = mScreenHeight / 3;
        mTitleBarHeight = ((float) mScreenHeight / 568) * 56;
        mInputLayoutHeight = mTitleBarHeight;
        mPresenter = new MomentDetailPresenter(this);

        mMomentBean = getIntent().getParcelableExtra("square_detail");
        if (mMomentBean != null) {
            id = mMomentBean.getS_id();
            mPresenter.loadMoment(mMomentBean);
        } else {
            String id = getIntent().getStringExtra("moment_id_from_message");
            mPresenter.loadMomentById(Integer.valueOf(id), MyApplication.getAccount());
        }

//        mPresenter.loadMomentComments(momentBean.getId());
        mAdapter = new MomentDetailCommentListViewAdapter(this);
        mAdapter.setmHeaderView(mHeadView);
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
    public void loadMoment(MomentBean mMoment) {
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
    public void loadComments(List<MomentCommentBean> mComments) {
//        mListView.removeHeaderView(mHeadView);
//        list = mComments;
//        mListView.addHeaderView(mHeadView);
//        Util.setListViewHeightBasedOnChildren(mListView);
        // 加载数据的地方
        L.i("size:" + mComments.size());
        mAdapter.setmShowFooter(true);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.addAll(mComments);
        if (mComments == null || mComments.size() < NetConstants.PAZE_SIZE) {
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
        pageIndex += NetConstants.PAZE_SIZE;
    }


    @Override
    public void sendSuccess(MomentBean momentBean) {
        onRefresh();
        mMomentBean = momentBean;
        mEditText.setText("");
        mMomentCommentTextView.setText(mMomentBean.getCommentNum() + "");
        mMomentFavouriteTextView.setText(mMomentBean.getLikesNum() + "");
        SnackBarUtil.showSnackBar(this, "评论成功!");
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        SnackBarUtil.showSnackBar(this, msg);
    }

    public void noLoginError() {
        SnackBarUtil.showSnackBar(this, "请先登录", "现在登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.toAnotherActivity(MomentDetailActivity.this, LoginActivity.class);
                finish();
            }
        });
    }

    public void sendComment(String comment) {
        if (MyApplication.getmLoginStatus() == 0) {
            noLoginError();
        } else {
            MomentCommentBean momentCommentBean = new MomentCommentBean();
            momentCommentBean.setS_id(id);
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
    public void unlikeSuccess(MomentBean momentBean) {
        mFavouriteImageButton.setImageResource(R.mipmap.favourite);
        mMomentFavouriteTextView.setText(momentBean.getLikesNum() + "");
        mMomentCommentTextView.setText(momentBean.getCommentNum() + "");
        Toast.makeText(MomentDetailActivity.this, "取消点赞成功！", Toast.LENGTH_LONG).show();
//        mPresenter.loadMomentComments(momentBean.getId());
    }

    @OnClick(R.id.question_detail_root_ll)
    public void click_layout(View v) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @OnClick(R.id.moment_detail_back_ib)
    public void back() {
        if (myApplication.getHandler() != null) {
            myApplication.getHandler().sendEmptyMessage(1);
            myApplication.setHandler(null);
        }
        finish();
    }

    @OnClick(R.id.moment_detail_send_ib)
    public void send() {
        if (TextUtils.isEmpty(mEditText.getText().toString())) {
            SnackBarUtil.showSnackBar(this, "评论不能为空！");
        } else {
            sendComment(mEditText.getText().toString());
        }
    }

    @OnClick(R.id.moment_detail_favourite_ib)
    public void comment_favourite() {
        if (mMomentBean.getIfLike() == 0) {
            mPresenter.commentLike(mMomentBean.getS_id(), MyApplication.getAccount());

        } else {
            mPresenter.commentUnlike(mMomentBean.getS_id(), MyApplication.getAccount());
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > mKeyBoardHeight)) {
            int dValue = oldBottom - bottom;
            int newHeight = mScreenHeight - dValue;
            L.i("newHeight:" + newHeight + "Height:" + mScreenHeight + "mTitleBarHeight:" + mTitleBarHeight);
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

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > mKeyBoardHeight)) {
            mTitleBarLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 56));
            mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 456));
            mInputLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 56));
            mTitleBarLayout.invalidate();
            mRecyclerView.invalidate();
            mInputLayout.invalidate();
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
        pageIndex = 0;
        if (list != null) {
            list.clear();
        }
//        presenter = new MomentDetailPresenterImpl(this);
        mPresenter.loadMomentComments(id, pageIndex);
        mAdapter.notifyDataSetChanged();
    }
}
