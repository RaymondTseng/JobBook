package com.example.jobbook.moment.widget;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;
import com.example.jobbook.commons.Constants;
import com.example.jobbook.commons.Urls;
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
    private MomentBean momentBean;
    private LinearLayout mHeadView;
    private MomentDetailPresenter presenter;
    private View mRootView;
    private List<MomentCommentBean> list;
    private RelativeLayout mTitleBarLayout;
    private LinearLayout mInputLayout;
    private LinearLayout mLoadingLinearLayout;
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
        mHeadView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_moment_detail, null);
        mRecyclerView = (RecyclerView) findViewById(R.id.moment_detail_rv);
        mEditText = (EditText) findViewById(R.id.moment_detail_comment_et);
        mSendImageButton = (ImageButton) findViewById(R.id.moment_detail_send_ib);
        mBackImageButton = (ImageButton) findViewById(R.id.moment_detail_back_ib);
        mFavouriteImageButton = (ImageButton) findViewById(R.id.moment_detail_favourite_ib);
        mMomentContentTextView = (TextView) findViewById(R.id.moment_detail_content_tv);
        mMomentUserNameTextView = (TextView) findViewById(R.id.moment_detail_name_tv);
        mMomentTimeTextView = (TextView) findViewById(R.id.moment_detail_time_tv);
        mMomentUserLogoImageView = (ImageView) findViewById(R.id.moment_detail_head_iv);
        mMomentFavouriteTextView = (TextView) findViewById(R.id.moment_detail_favourite_tv);
        mMomentCommentTextView = (TextView) findViewById(R.id.moment_detail_comment_tv);
        mRootView = findViewById(R.id.question_detail_root_ll);
        mTitleBarLayout = (RelativeLayout) findViewById(R.id.moment_detail_title_bar);
        mInputLayout = (LinearLayout) findViewById(R.id.moment_detail_input_ll);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }

    private void initEvents() {
        momentBean = (MomentBean) getIntent().getExtras().getSerializable("square_detail");
        id = momentBean.getId();
        L.i("squaredetail_activity", "123:" + momentBean.getId());
        mScreenHeight = Util.getHeight(this);
        mKeyBoardHeight = mScreenHeight / 3;
        mTitleBarHeight = ((float) mScreenHeight / 568) * 56;
        mInputLayoutHeight = mTitleBarHeight;
        mAdapter = new MomentDetailCommentListViewAdapter(this);
        mPresenter = new MomentDetailPresenterImpl(this);
        mPresenter.loadMoment(momentBean);
        mPresenter.loadMomentComments(momentBean.getId());
        mBackImageButton.setOnClickListener(this);
        mSendImageButton.setOnClickListener(this);
        mFavouriteImageButton.setOnClickListener(this);
        mRootView.addOnLayoutChangeListener(this);
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
        mMomentContentTextView.setText(mMoment.getContent());
        mMomentUserNameTextView.setText(mMoment.getAuthor().getUsername());
        mMomentTimeTextView.setText(mMoment.getDate());
        ImageLoadUtils.display(this, mMomentUserLogoImageView, mMoment.getAuthor().getHead());
        mMomentFavouriteTextView.setText(mMoment.getLikesNum() + "");
        mMomentCommentTextView.setText(mMoment.getCommentNum() + "");
        if(mMoment.getIfLike() == 0){
            mFavouriteImageButton.setImageResource(R.mipmap.favourite);
        }else{
            mFavouriteImageButton.setImageResource(R.mipmap.favourite_tapped);
        }
    }

    @Override
    public void addComments(List<MomentCommentBean> mComments) {
//        mListView.removeHeaderView(mHeadView);
        list = mComments;
//        mListView.addHeaderView(mHeadView);
//        Util.setListViewHeightBasedOnChildren(mListView);
        // 加载数据的地方
        mAdapter.setmShowFooter(true);
        if (list == null) {
            list = new ArrayList<>();
        }
        list = mComments;
        if (pageIndex == 0) {
            mAdapter.updateData(list);
        } else {
            //如果没有更多数据了,则隐藏footer布局
            if (list == null || list.size() == 0) {
                mAdapter.setmShowFooter(false);
            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }


    @Override
    public void sendSuccess() {
        mPresenter.loadMomentComments(id);
        mEditText.setText("");
        mMomentCommentTextView.setText(Integer.valueOf(mMomentCommentTextView.getText().toString()) + 1 + "");
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
        Util.showSnackBar(view, "请先登录!");
    }

    @Override
    public void sendComment(String comment) {
        if (MyApplication.getmLoginStatus() == 0) {
            noLoginError();
        } else {
            MomentCommentBean momentCommentBean = new MomentCommentBean();
            momentCommentBean.setApplier(MyApplication.getmPersonBean());
            momentCommentBean.setContent(comment);
//            L.i("square_detail", momentBean.getId() + "");
//            momentCommentBean.setQ_id(momentBean.getId());
            mPresenter.sendComment(id, momentCommentBean);
        }
    }

    @Override
    public void commentLikeSuccess(int num_like, int num_unlike) {
        mFavouriteImageButton.setImageResource(R.mipmap.favourite_tapped);
        mMomentFavouriteTextView.setText(Integer.valueOf(mMomentFavouriteTextView.getText().toString()) + 1 + "");
        Toast.makeText(MomentDetailActivity.this, "评论点赞成功！", Toast.LENGTH_LONG).show();
//        mPresenter.loadMomentComments(momentBean.getId());
        L.i("comment_like_success", "good:" + num_like + "bad:" + num_unlike);
    }

    @Override
    public void commentLikeFailure(String msg) {
        Toast.makeText(MomentDetailActivity.this, "您已经点过啦！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void commentUnlikeSuccess(int num_like, int num_unlike) {
        mFavouriteImageButton.setImageResource(R.mipmap.favourite);
        mMomentFavouriteTextView.setText(Integer.valueOf(mMomentFavouriteTextView.getText().toString()) - 1 + "");
        Toast.makeText(MomentDetailActivity.this, "评论踩成功！", Toast.LENGTH_LONG).show();
//        mPresenter.loadMomentComments(momentBean.getId());
        L.i("comment_like_success", "good:" + num_like + "bad:" + num_unlike);
    }

    @Override
    public void commentUnlikeFailure(String msg) {
        Toast.makeText(MomentDetailActivity.this, "您已经点过啦！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moment_detail_back_ib:
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
                if(momentBean.getIfLike() == 0){
                    presenter.commentLike(momentBean.getId() , myApplication.getAccount());

                }else{
                    presenter.commentUnlike(momentBean.getId(), myApplication.getAccount());
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
            mTitleBarLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, (mTitleBarHeight / newHeight) * 568));
            mInputLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, (mInputLayoutHeight / newHeight) * 568));
            mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, ((float) (newHeight - 2 * mTitleBarHeight) / newHeight) * 568));
            L.i("square_detail", "软键盘弹起");

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > mKeyBoardHeight)) {
            mTitleBarLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 56));
            mInputLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 56));
            mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 456));
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
                presenter.loadMomentComments(pageIndex);
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
        presenter.loadMomentComments(pageIndex);
        mAdapter.notifyDataSetChanged();
    }
}
