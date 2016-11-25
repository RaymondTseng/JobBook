package com.example.jobbook.square.widget;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.MomentCommentBean;
import com.example.jobbook.square.SquareDetailListViewAdapter;
import com.example.jobbook.square.presenter.SquareDetailPresenterImpl;
import com.example.jobbook.square.view.SquareDetailView;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by root on 16-11-23.
 */

public class SquareDetailActivity extends Activity implements View.OnClickListener, SquareDetailView{
    private ImageButton mBackImageButton;
    private ImageView mHeadImageView;
    private TextView mNameTextView;
    private TextView mPositionTextView;
    private TextView mContentTextView;
    private TextView mFavouriteTextView;
    private TextView mCommentTextView;
    private TextView mTimeTextView;
    private ListView mListView;
    private ImageButton mFavouriteImageButton;
    private EditText mCommentEditText;
    private ImageButton mSendImageButton;
    private SquareDetailPresenterImpl mPresenter;
    private SquareDetailListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_detail);
        initViews();
        initEvents();
    }

    private void initViews(){
        mBackImageButton = (ImageButton) findViewById(R.id.square_detail_back_ib);
        mHeadImageView = (ImageView) findViewById(R.id.square_detail_head_iv);
        mNameTextView = (TextView) findViewById(R.id.square_detail_name_tv);
        mPositionTextView = (TextView) findViewById(R.id.square_detail_position_tv);
        mContentTextView = (TextView) findViewById(R.id.square_detail_content_tv);
        mFavouriteTextView = (TextView) findViewById(R.id.square_detail_favourite_tv);
        mCommentTextView = (TextView) findViewById(R.id.square_detail_comment_tv);
        mTimeTextView = (TextView) findViewById(R.id.square_detail_time_tv);
        mListView = (ListView) findViewById(R.id.square_detail_lv);
        mFavouriteImageButton = (ImageButton) findViewById(R.id.square_detail_favourite_ib);
        mCommentEditText = (EditText) findViewById(R.id.square_detail_comment_et);
        mSendImageButton = (ImageButton) findViewById(R.id.square_detail_send_ib);
        mPresenter = new SquareDetailPresenterImpl(this);
    }

    private void initEvents(){
        MomentBean moment = (MomentBean) getIntent().getSerializableExtra("square_detail");
        ImageLoadUtils.display(this, mHeadImageView, moment.getAuthor().getHead());
        mPresenter.loadSquareComments(Integer.parseInt(moment.getId()));
        mNameTextView.setText(moment.getAuthor().getUsername());
        mContentTextView.setText(moment.getContent());
        mFavouriteTextView.setText(moment.getLikesNum() + "");
        mCommentTextView.setText(moment.getCommentNum() + "");
        mTimeTextView.setText(moment.getDate());
        mAdapter = new SquareDetailListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mFavouriteImageButton.setOnClickListener(this);
        mSendImageButton.setOnClickListener(this);
        mBackImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.square_detail_favourite_ib:
                break;
            case R.id.square_detail_send_ib:
                break;
            case R.id.square_detail_back_ib:
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void addSquareComments(List<MomentCommentBean> squareCommentList) {
        mAdapter.updateData(squareCommentList);
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {

    }
}
