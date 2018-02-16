package com.example.jobbook.ui.moment.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.moment.NewMomentContract;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.presenter.moment.NewMomentPresenter;
import com.example.jobbook.util.AffectUtil;
import com.example.jobbook.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewMomentActivity extends Activity implements NewMomentContract.View, TextWatcher {

    private static int NO_CHANGE_COLOR = 0;
    private static int CHANGE_COLOR = 1;

    @BindView(R.id.activity_publish_moment_close_ib)
    ImageButton mCloseImageButton;

    @BindView(R.id.activity_publish_moment_content_et)
    EditText mNewMomentContentEditText;

    @BindView(R.id.activity_publish_moment_release_tv)
    TextView mNewMomentReleaseTextView;

    @BindView(R.id.activity_publish_moment_loading)
    ViewStub mLoadingLinearLayout;

    private NewMomentPresenter mNewMomentPresenter;
    private MyApplication myApplication;
    private View view;
    private NewMomentHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_moment);
        ButterKnife.bind(this);
        view = getWindow().getDecorView();
        initEvents();
    }

    private void initEvents() {
        mLoadingLinearLayout.inflate();
        mNewMomentPresenter = new NewMomentPresenter(this);
//        mNewMomentTitleEditText.addTextChangedListener(this);
        mNewMomentContentEditText.addTextChangedListener(this);
        mLoadingLinearLayout.setVisibility(View.GONE);
        mNewMomentReleaseTextView.setTextColor(Color.WHITE);
//        mNewQuestionTitleEditText.setOnFocusChangeListener(AffectUtil.changeHintColorListener(mNewMomentTitleEditText));
        mNewMomentContentEditText.setOnFocusChangeListener(AffectUtil.changeHintColorListener(mNewMomentContentEditText));

        handler = new NewMomentHandler();

        mNewMomentContentEditText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void showSuccess() {
        MyApplication.getmPersonBean().setMoment(Integer.valueOf(MyApplication.getmPersonBean().
                getMoment()) + 1 + "");
        Util.showSnackBar(view, "发表成功!");
    }

    @Override
    public void publishNoLoginError() {
        Util.showSnackBar(view, "请先登录！");
    }

    @Override
    public void close() {
        myApplication = (MyApplication) getApplication();
        if (myApplication.getHandler() != null) {
            myApplication.getHandler().sendEmptyMessage(1);
            myApplication.setHandler(null);
        }
        this.finish();
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        Util.showSnackBar(view, "发表失败,请重试！");
    }

    @OnClick(R.id.activity_publish_moment_close_ib)
    public void back() {
        close();
    }

    @OnClick(R.id.activity_publish_moment_release_tv)
    public void release() {
        MomentBean momentBean = new MomentBean();
        if (MyApplication.getmLoginStatus() == 0) {
            publishNoLoginError();
        } else {
            mNewMomentReleaseTextView.setTextColor(Color.parseColor("#61ffffff"));
            momentBean.setAuthor(new TypePersonBean(MyApplication.getmPersonBean(), 1));
//                    momentBean.setTitle(mNewMomentTitleEditText.getText().toString());
            momentBean.setContent(mNewMomentContentEditText.getText().toString());
            mNewMomentPresenter.newmoment(momentBean);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        changeColor();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void changeColor() {
        if (!TextUtils.isEmpty(mNewMomentContentEditText.getText())) {
            Message message = new Message();
            message.what = CHANGE_COLOR;
            handler.sendMessage(message);
        } else {
            Message message = new Message();
            message.what = NO_CHANGE_COLOR;
            handler.sendMessage(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public class NewMomentHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CHANGE_COLOR) {
                mNewMomentReleaseTextView.setClickable(true);
                mNewMomentReleaseTextView.setTextColor(Color.WHITE);
            } else if (msg.what == NO_CHANGE_COLOR) {
                mNewMomentReleaseTextView.setTextColor(Color.parseColor("#61ffffff"));
            }
        }
    }
}
