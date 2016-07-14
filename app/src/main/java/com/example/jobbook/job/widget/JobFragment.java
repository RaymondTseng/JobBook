package com.example.jobbook.job.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.job.JobListViewAdapter;
import com.example.jobbook.job.presenter.JobPresenter;
import com.example.jobbook.job.presenter.JobPresenterImpl;
import com.example.jobbook.job.view.JobView;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class JobFragment extends Fragment implements JobView, View.OnFocusChangeListener,
        AdapterView.OnItemClickListener {

    private ListView mListView;
    private EditText mEditText;
    private JobPresenter mJobPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mListView = (ListView) view.findViewById(R.id.job_lv);
        mEditText = (EditText) view.findViewById(R.id.job_et);
        mEditText.setOnFocusChangeListener(this);
        mListView.setOnFocusChangeListener(this);
        mJobPresenter = new JobPresenterImpl(this);
        mListView.setAdapter(new JobListViewAdapter(getActivity()));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void addJobs(List<JobBean> jobList) {
        mListView.setAdapter(new JobListViewAdapter(getActivity()));
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadingFailMsg() {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch(v.getId()){
            case R.id.job_et:

                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), JobDetailActivity.class);
        startActivity(intent);
    }
}
