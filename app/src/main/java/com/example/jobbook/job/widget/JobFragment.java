package com.example.jobbook.job.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.job.JobListViewAdapter;
import com.example.jobbook.job.presenter.JobPresenter;
import com.example.jobbook.job.presenter.JobPresenterImpl;
import com.example.jobbook.job.view.JobView;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class JobFragment extends Fragment implements JobView, View.OnFocusChangeListener,
        JobListViewAdapter.OnItemClickListener, View.OnClickListener {

    private ListView mListView;
    private EditText mEditText;
    private JobPresenter mJobPresenter;
    private View view;
    private List<JobBean> list;
    private JobListViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_job, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initViews(View view) {
        mListView = (ListView) view.findViewById(R.id.job_lv);
        mEditText = (EditText) view.findViewById(R.id.job_et);
    }

    private void initEvents(){
        list = new ArrayList<>();
        mAdapter = new JobListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mAdapter.updateData(list);
        mAdapter.setOnItemClickListener(this);
        mEditText.setOnFocusChangeListener(this);
        mListView.setOnFocusChangeListener(this);
        mJobPresenter = new JobPresenterImpl(this);
        mJobPresenter.loadJobs();
//        mListView.setAdapter(new JobListViewAdapter(getActivity()));
//        mListView.setOnItemClickListener(this);
        mEditText.setFocusable(false);
        mEditText.setOnClickListener(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void addJobs(List<JobBean> jobList) {
        if(jobList != null){
            list = jobList;
            mAdapter.updateData(list);
        }
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadingFailMsg() {
        final Snackbar snackbar = Snackbar.make(view, "岗位读取错误，请重试！", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void search() {
        SearchDialogFragment fragment = new SearchDialogFragment();
        fragment.show(getActivity().getFragmentManager(), "search_dialog");
        Log.i("jobfragment", "createSearchDialog");
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.job_et:
                Log.i("jobfragment", "123");
                break;

        }
    }

    @Override
    public void onClick(View v) {
        search();
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("job_detail", list.get(position));
        Util.toAnotherActivity(getActivity(), JobDetailActivity.class, bundle);
    }
}
