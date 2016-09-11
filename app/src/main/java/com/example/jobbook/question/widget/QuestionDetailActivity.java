package com.example.jobbook.question.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.bean.QuestionCommentBean;
import com.example.jobbook.bean.QuestionDetailBean;
import com.example.jobbook.question.ExpandListView;
import com.example.jobbook.question.QuestionDetailListViewAdapter;
import com.example.jobbook.question.presenter.QuestionDetailPresenter;
import com.example.jobbook.question.presenter.QuestionDetailPresenterImpl;
import com.example.jobbook.question.view.QuestionDetailView;
import com.example.jobbook.util.Util;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class QuestionDetailActivity extends Activity implements QuestionDetailView, View.OnClickListener, View.OnLayoutChangeListener{
    private ListView mListView;
    private EditText mEditText;
    private ImageButton mSendImageButton;
    private ImageButton mBackImageButton;
    private QuestionDetailPresenter mPresenter;
    private TextView mQuestionTitleTextView;
    private TextView mQuestionContentTextView;
    private TextView mQuestionUserNameTextView;
    private TextView mQuestionTimeTextView;
    private ImageView mQuestionUserLogoImageView;
    private QuestionDetailListViewAdapter mAdapter;
    private QuestionBean questionBean;
    private LinearLayout mHeadView;
    private View mRootView;
    private RelativeLayout mTitleBarLayout;
    private LinearLayout mInputLayout;
    private LinearLayout mLoadingLinearLayout;
    private int mScreenHeight;
    private int mKeyBoardHeight;
    private int mTitleBarHeight;
    private int mInputLayoutHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        initViews();
        initEvents();

    }
    private void initViews(){
        mHeadView = (LinearLayout) getLayoutInflater().inflate(R.layout.question_detail_description, null);
        mListView = (ListView) findViewById(R.id.question_detail_lv);
        mEditText = (EditText) findViewById(R.id.question_detail_et);
        mSendImageButton = (ImageButton) findViewById(R.id.question_detail_send_ib);
        mBackImageButton = (ImageButton) findViewById(R.id.question_detail_back_ib);
        mQuestionTitleTextView = (TextView) mHeadView.findViewById(R.id.question_detail_title_tv);
        mQuestionContentTextView = (TextView) mHeadView.findViewById(R.id.question_detail_content_tv);
        mQuestionUserNameTextView = (TextView) mHeadView.findViewById(R.id.question_detail_user_name_tv);
        mQuestionTimeTextView = (TextView) mHeadView.findViewById(R.id.question_detail_time_tv);
        mQuestionUserLogoImageView = (ImageView) findViewById(R.id.question_detail_user_logo_iv);
        mRootView = (View) findViewById(R.id.question_detail_root_ll);
        mTitleBarLayout = (RelativeLayout) findViewById(R.id.question_detail_title_bar);
        mInputLayout = (LinearLayout) findViewById(R.id.question_detail_input_ll);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }

    private void initEvents(){
        questionBean = (QuestionBean) getIntent().getExtras().getSerializable("question_detail");
        Log.i("questiondetail_activity", "123:" + questionBean.getId());
        mScreenHeight = Util.getHeight(this);
        mKeyBoardHeight = mScreenHeight / 3;
        mTitleBarHeight = (mScreenHeight / 568) * 56;
        mInputLayoutHeight = mTitleBarHeight;
        mAdapter = new QuestionDetailListViewAdapter(this);
        mPresenter = new QuestionDetailPresenterImpl(this);
        mPresenter.loadQuestion(questionBean);
        mPresenter.loadQuestionComments(questionBean.getId());
        mBackImageButton.setOnClickListener(this);
        mSendImageButton.setOnClickListener(this);
        mRootView.addOnLayoutChangeListener(this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void addQuestion(QuestionBean mQuestion) {
        mQuestionTitleTextView.setText(mQuestion.getTitle());
        mQuestionContentTextView.setText(mQuestion.getContent());
        mQuestionUserNameTextView.setText(mQuestion.getAuthor().getUsername());
        mQuestionTimeTextView.setText(mQuestion.getDate());
//        mQuestionUserLogoImageView
    }

    @Override
    public void addComments(List<QuestionCommentBean> mComments) {
        mAdapter.updateData(mComments);
        mListView.addHeaderView(mHeadView);
//        Util.setListViewHeightBasedOnChildren(mListView);
    }

    @Override
    public void sendSuccess() {
        mPresenter.loadQuestionComments(questionBean.getId());
        mEditText.setText("");
        showSnackbar("评论成功!");
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(int error) {
        switch (error){
            case 0:
                showSnackbar("问问加载错误,请重试!");
                break;
            case 1:
                showSnackbar("评论加载错误,请重试！");
                break;
            case 2:
                showSnackbar("发表评论失败！");
                break;
        }
    }

    private void showSnackbar(String content){
        View view = getWindow().getDecorView();
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }


    @Override
    public String getComment() {
        return mEditText.getText().toString();
    }

    @Override
    public void editTextBlankError() {
        showSnackbar("评论不能为空！");
    }

    @Override
    public void noLoginError() {
        showSnackbar("请先登录!");
    }

    @Override
    public void sendComment(String comment) {
        if(MyApplication.getmLoginStatus() == 0){
            noLoginError();
        }else{
            QuestionCommentBean questionCommentBean = new QuestionCommentBean();
            questionCommentBean.setApplier(MyApplication.getmPersonBean());
            questionCommentBean.setContent(comment);
            Log.i("question_detail", questionBean.getId() + "");
            questionCommentBean.setQ_id(questionBean.getId());
            mPresenter.sendComment(questionCommentBean);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.question_detail_back_ib:
                finish();
                break;
            case R.id.question_detail_send_ib:
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
            Log.i("question_detail", "newHeight:" + newHeight + "Height:" + mScreenHeight);
            mTitleBarLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, ((float)mTitleBarHeight / newHeight) * 568));
            mInputLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, ((float)mInputLayoutHeight / newHeight) * 568));
            mListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    MATCH_PARENT, 0, ((float)(newHeight - 2 * mTitleBarHeight) / newHeight) * 568));
            Log.i("question_detail", "软键盘弹起");

        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > mKeyBoardHeight)){
            mTitleBarLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 56));
            mInputLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 56));
            mListView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 456));
            Log.i("question_detail", "软键盘关闭");

        }
    }
}
