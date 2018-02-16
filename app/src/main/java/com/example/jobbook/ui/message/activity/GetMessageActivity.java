package com.example.jobbook.ui.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.message.GetMessageContract;
import com.example.jobbook.model.bean.MessageBean;
import com.example.jobbook.ui.moment.activity.MomentDetailActivity;
import com.example.jobbook.presenter.message.GetMessagePresenter;
import com.example.jobbook.ui.message.adapter.GetMessageListViewAdapter;
import com.example.jobbook.ui.person.activity.UserDetailActivity;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xu on 2016/12/6.
 */

public class GetMessageActivity extends Activity implements GetMessageContract.View {

//    private SwipeRefreshLayout refreshLayout;
    @BindView(R.id.getmessage_back_ib)
    ImageButton mBackImageButton;
    //    private StickyListHeadersListView stickyList;
    @BindView(R.id.getmessage_lv)
    ListView mGetMessageListView;

    @BindView(R.id.getmessage_loading_layout)
    ViewStub mLoadingLinearLayout;
//    private GetMessageRecyclerViewAdapter mAdapter;
    private GetMessageListViewAdapter mAdapter;
    private GetMessagePresenter presenter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmessage);
        ButterKnife.bind(this);
        initViews();
        initEvents();
    }

    private void initViews() {
        view = findViewById(android.R.id.content);
//        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.getmessage_refresh_layout);
        mLoadingLinearLayout.inflate();
    }

    private void initEvents() {
        presenter = new GetMessagePresenter(this);
        presenter.getMessage(MyApplication.getAccount());
        mAdapter = new GetMessageListViewAdapter(this, new ArrayList<MessageBean>());
        mGetMessageListView.setAdapter(mAdapter);
        mAdapter.setOnMessageItemClickLitener(new GetMessageListViewAdapter.OnMessageItemClickListener() {
            @Override
            public void onItemClick(MessageBean messageBean) {
                switch (messageBean.getType()) {
                    case MessageBean.FOLLOW:
                        Intent intent = new Intent(GetMessageActivity.this, UserDetailActivity.class);
                        intent.putExtra("person_account_from_message", messageBean.getId());
                        startActivity(intent);
                        break;
                    case MessageBean.LIKE:
                        Intent intent1 = new Intent(GetMessageActivity.this, MomentDetailActivity.class);
                        intent1.putExtra("moment_id_from_message", messageBean.getId());
                        startActivity(intent1);
                        break;
                    case MessageBean.COMMENT:
                        Intent intent2 = new Intent(GetMessageActivity.this, MomentDetailActivity.class);
                        intent2.putExtra("moment_id_from_message", messageBean.getId());
                        startActivity(intent2);
                        break;
                }
            }
        });
    }

    @Override
    public void getMessage(List<MessageBean> list) {
        mAdapter.refreshData(list);
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
        Util.showSnackBar(view, "获取消息错误，请重试！");
    }

    @OnClick(R.id.getmessage_back_ib)
    public void click_back() {
        finish();
    }
}
