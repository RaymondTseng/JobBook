package com.example.jobbook.message.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.model.bean.MessageBean;
import com.example.jobbook.message.GetMessageListViewAdapter;
import com.example.jobbook.message.presenter.GetMessagePresenter;
import com.example.jobbook.message.presenter.GetMessagePresenterImpl;
import com.example.jobbook.message.view.GetMessageView;
import com.example.jobbook.moment.widget.MomentDetailActivity;
import com.example.jobbook.userdetail.widget.UserDetailActivity;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/12/6.
 */

public class GetMessageActivity extends Activity implements View.OnClickListener, GetMessageView {

//    private SwipeRefreshLayout refreshLayout;
    private ImageButton mBackImageButton;
    //    private StickyListHeadersListView stickyList;
    private ListView mGetMessageListView;
    private ViewStub mLoadingLinearLayout;
//    private GetMessageRecyclerViewAdapter mAdapter;
    private GetMessageListViewAdapter mAdapter;
    private GetMessagePresenter presenter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmessage);
        initViews();
        initEvents();
    }

    private void initViews() {
        view = findViewById(android.R.id.content);
//        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.getmessage_refresh_layout);
        mBackImageButton = (ImageButton) findViewById(R.id.getmessage_back_ib);
//        stickyList = (StickyListHeadersListView) findViewById(R.id.getmessage_lv);
        mGetMessageListView = (ListView) findViewById(R.id.getmessage_lv);
        mLoadingLinearLayout = (ViewStub) findViewById(R.id.getmessage_loading_layout);
        mLoadingLinearLayout.inflate();
    }

    private void initEvents() {
        presenter = new GetMessagePresenterImpl(this);
        presenter.getMessage(MyApplication.getAccount());
        mAdapter = new GetMessageListViewAdapter(this, new ArrayList<MessageBean>());
        mGetMessageListView.setAdapter(mAdapter);
        mBackImageButton.setOnClickListener(this);
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
    public void showLoadFailMsg() {
        Util.showSnackBar(view, "获取消息错误，请重试！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getmessage_back_ib:
                finish();
                break;
        }
    }
}
