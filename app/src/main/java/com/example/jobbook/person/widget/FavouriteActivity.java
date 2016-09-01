package com.example.jobbook.person.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.job.JobsAdapter;
import com.example.jobbook.person.presenter.FavouritePresenter;
import com.example.jobbook.person.presenter.FavouritePresenterImpl;
import com.example.jobbook.person.view.FavouriteView;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class FavouriteActivity extends Activity implements View.OnClickListener, FavouriteView {
    private ImageButton mBackImageButton;
    private RecyclerView mRecyclerView;
    private JobsAdapter mAdapter;
    private FavouritePresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_favourite);
        mPresenter = new FavouritePresenterImpl(this);
        initViews();
        initEvents();
    }
    private void initViews(){
        mBackImageButton = (ImageButton) findViewById(R.id.favourite_back_ib);
        mRecyclerView = (RecyclerView) findViewById(R.id.favourite_rv);
    }

    private void initEvents(){
        mPresenter = new FavouritePresenterImpl(this);
        mBackImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.favourite_back_ib:
                finish();
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void addJobs(List<JobBean> mJobs) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {

    }
}
