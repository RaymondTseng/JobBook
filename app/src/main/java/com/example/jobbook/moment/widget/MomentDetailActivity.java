package com.example.jobbook.moment.widget;

import android.app.Activity;
import android.os.Bundle;
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
import com.example.jobbook.moment.SquareDetailListViewAdapter;
import com.example.jobbook.moment.SquareDetailListViewAdapter.OnLikeClickListener;
import com.example.jobbook.moment.SquareDetailListViewAdapter.OnUnlikeClickListener;
import com.example.jobbook.moment.presenter.MomentDetailPresenter;
import com.example.jobbook.moment.presenter.MomentDetailPresenterImpl;
import com.example.jobbook.moment.view.MomentDetailView;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class MomentDetailActivity extends Activity implements MomentDetailView, View.OnClickListener, View.OnLayoutChangeListener{
    private ListView mListView;
    private EditText mEditText;
    private ImageButton mSendImageButton;
    private ImageButton mBackImageButton;
    private MomentDetailPresenter mPresenter;
    private TextView mMomentContentTextView;
    private TextView mMomentUserNameTextView;
    private TextView mMomentTimeTextView;
    private ImageView mMomentUserLogoImageView;
    private SquareDetailListViewAdapter mAdapter;
    private MomentBean momentBean;
    private LinearLayout mHeadView;
    private View mRootView;
    private RelativeLayout mTitleBarLayout;
    private LinearLayout mInputLayout;
    private LinearLayout mLoadingLinearLayout;
    private int mScreenHeight;
    private int mKeyBoardHeight;
    private float mTitleBarHeight;
    private float mInputLayoutHeight;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_detail);
        view = getWindow().getDecorView();
        initViews();
        initEvents();

    }
    private void initViews(){
        mHeadView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_square_detail, null);
        mListView = (ListView) findViewById(R.id.square_detail_lv);
        mEditText = (EditText) findViewById(R.id.square_detail_et);
        mSendImageButton = (ImageButton) findViewById(R.id.square_detail_send_ib);
        mBackImageButton = (ImageButton) findViewById(R.id.square_detail_back_ib);
        mMomentContentTextView = (TextView) mHeadView.findViewById(R.id.square_detail_content_tv);
        mMomentUserNameTextView = (TextView) mHeadView.findViewById(R.id.square_detail_name_tv);
        mMomentTimeTextView = (TextView) mHeadView.findViewById(R.id.square_detail_time_tv);
        mMomentUserLogoImageView = (ImageView) findViewById(R.id.square_detail_head_iv);
        mRootView = findViewById(R.id.question_detail_root_ll);
        mTitleBarLayout = (RelativeLayout) findViewById(R.id.square_detail_title_bar);
        mInputLayout = (LinearLayout) findViewById(R.id.square_detail_input_ll);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }

    private void initEvents(){
        momentBean = (MomentBean) getIntent().getExtras().getSerializable("square_detail");
        L.i("squaredetail_activity", "123:" + momentBean.getId());
        mScreenHeight = Util.getHeight(this);
        mKeyBoardHeight = mScreenHeight / 3;
        mTitleBarHeight = ((float)mScreenHeight / 568) * 56;
        mInputLayoutHeight = mTitleBarHeight;
        mAdapter = new SquareDetailListViewAdapter(this);
        mPresenter = new MomentDetailPresenterImpl(this);
        mPresenter.loadMoment(momentBean);
        mPresenter.loadMomentComments(momentBean.getId());
        mBackImageButton.setOnClickListener(this);
        mSendImageButton.setOnClickListener(this);
        mRootView.addOnLayoutChangeListener(this);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnLikeClickListener(new OnLikeClickListener() {
            @Override
            public void onLikeClickListener(int com_id) {
                if (MyApplication.getmLoginStatus() == 0) {
                    Toast.makeText(MomentDetailActivity.this, "请先登录！", Toast.LENGTH_LONG).show();
                }else {
                    mPresenter.commentLike(com_id, MyApplication.getAccount());
                }
            }
        });
        mAdapter.setOnUnlikeClickListener(new OnUnlikeClickListener() {
            @Override
            public void onUnlikeClickListener(int com_id) {
                if (MyApplication.getmLoginStatus() == 0) {
                    Toast.makeText(MomentDetailActivity.this, "请先登录！", Toast.LENGTH_LONG).show();
                }else {
//                    L.i("squaredetail", "comment unlike" + com_id);
                    mPresenter.commentUnlike(com_id, MyApplication.getAccount());
                }
            }
        });
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
    }

    @Override
    public void addComments(List<MomentCommentBean> mComments) {
        mListView.removeHeaderView(mHeadView);
        mAdapter.updateData(mComments);
        mListView.addHeaderView(mHeadView);
//        Util.setListViewHeightBasedOnChildren(mListView);
    }


    @Override
    public void sendSuccess() {
        mPresenter.loadMomentComments(momentBean.getId());
        mEditText.setText("");
        Util.showSnackBar(view,"评论成功!");
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(int error) {
        switch (error){
            case 0:
                Util.showSnackBar(view,"问问加载错误,请重试!");
                break;
            case 1:
                Util.showSnackBar(view,"评论加载错误,请重试！");
                break;
            case 2:
                Util.showSnackBar(view,"发表评论失败！");
                break;
        }
    }

    @Override
    public String getComment() {
        return mEditText.getText().toString();
    }

    @Override
    public void editTextBlankError() {
        Util.showSnackBar(view,"评论不能为空！");
    }

    @Override
    public void noLoginError() {
        Util.showSnackBar(view,"请先登录!");
    }

    @Override
    public void sendComment(String comment) {
        if(MyApplication.getmLoginStatus() == 0){
            noLoginError();
        }else{
            MomentCommentBean momentCommentBean = new MomentCommentBean();
            momentCommentBean.setApplier(MyApplication.getmPersonBean());
            momentCommentBean.setContent(comment);
            L.i("square_detail", momentBean.getId() + "");
            momentCommentBean.setQ_id(momentBean.getId());
            mPresenter.sendComment(momentCommentBean);
        }
    }

    @Override
    public void commentLikeSuccess(int num_like, int num_unlike) {
        Toast.makeText(MomentDetailActivity.this, "评论点赞成功！", Toast.LENGTH_LONG).show();
        mPresenter.loadMomentComments(momentBean.getId());
        L.i("comment_like_success", "good:" + num_like + "bad:" + num_unlike);
    }

    @Override
    public void commentLikeFailure(String msg) {
        Toast.makeText(MomentDetailActivity.this, "您已经点过啦！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void commentUnlikeSuccess(int num_like, int num_unlike) {
        Toast.makeText(MomentDetailActivity.this, "评论踩成功！", Toast.LENGTH_LONG).show();
        mPresenter.loadMomentComments(momentBean.getId());
        L.i("comment_like_success", "good:" + num_like + "bad:" + num_unlike);
    }

    @Override
    public void commentUnlikeFailure(String msg) {
        Toast.makeText(MomentDetailActivity.this, "您已经点过啦！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.square_detail_back_ib:
                finish();
                break;
            case R.id.square_detail_send_ib:
                if(TextUtils.isEmpty(getComment())){
                    editTextBlankError();
                }else{
                    sendComment(getComment());
                }
                break;
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > mKeyBoardHeight)){
            int dValue = oldBottom - bottom;
            int newHeight = mScreenHeight - dValue;
            L.i("square_detail", "newHeight:" + newHeight + "Height:" + mScreenHeight + "mTitleBarHeight:" + mTitleBarHeight);
            mTitleBarLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, (mTitleBarHeight / newHeight) * 568));
            mInputLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, (mInputLayoutHeight / newHeight) * 568));
            mListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, ((float)(newHeight - 2 * mTitleBarHeight) / newHeight) * 568));
            L.i("square_detail", "软键盘弹起");

        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > mKeyBoardHeight)){
            mTitleBarLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 56));
            mInputLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 56));
            mListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 456));
            L.i("square_detail", "软键盘关闭");

        }
    }
}
