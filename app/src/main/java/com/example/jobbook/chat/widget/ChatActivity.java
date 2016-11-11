package com.example.jobbook.chat.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.ChatBean;
import com.example.jobbook.chat.presenter.ChatPresenter;
import com.example.jobbook.chat.presenter.ChatPresenterImpl;
import com.example.jobbook.chat.view.ChatView;

import java.util.List;

/**
 * Created by Xu on 2016/7/19.
 */
public class ChatActivity extends Activity implements ChatView {

    private TextView mUserNameTextView;
    private ImageButton mUserLogoImageButton;
    private ListView mListView;
    private EditText mContentEditText;
    private FrameLayout mSendFrameLayout;
    private ChatPresenter mChatPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChatPresenter = new ChatPresenterImpl(this);
        setContentView(R.layout.chat_layout);
        initViews();
    }

    @Override
    protected void onResume() {
        mChatPresenter.receive();
        super.onResume();
    }

    private void initViews() {
        mUserNameTextView = (TextView) findViewById(R.id.chat_layout_name_tv);
        mUserLogoImageButton = (ImageButton) findViewById(R.id.chat_layout_logo_ib);
        mListView = (ListView) findViewById(R.id.chat_layout_lv);
        mContentEditText = (EditText) findViewById(R.id.chat_layout_send_content_et);
        mSendFrameLayout = (FrameLayout) findViewById(R.id.chat_layout_send_fl);
        mSendFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mChatPresenter.send
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setChatItems(List<ChatBean> chatItems) {
//        mListView.setAdapter();
    }
}
