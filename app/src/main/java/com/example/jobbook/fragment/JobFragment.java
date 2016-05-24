package com.example.jobbook.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jobbook.R;
import com.example.jobbook.adapter.JobListViewAdapter;

/**
 * Created by 椰树 on 2016/5/20.
 */
public class JobFragment extends Fragment {
    private ListView mListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        initViews(view);
        return view;
    }
    private void initViews(View view){
        mListView = (ListView) view.findViewById(R.id.job_lv);
        mListView.setAdapter(new JobListViewAdapter(getActivity()));
    }
}
