package com.example.jobbook.question.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jobbook.R;
import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.question.QuestionListViewAdapter;
import com.example.jobbook.question.presenter.QuestionPresenter;
import com.example.jobbook.question.presenter.QuestionPresenterImpl;
import com.example.jobbook.question.view.QuestionView;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class QuestionFragment extends Fragment implements QuestionView{

    private ListView mListView;
    private QuestionPresenter mQuestionPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initViews(view);
        return view;
    }

    public void initViews(View view) {
        mListView = (ListView) view.findViewById(R.id.question_lv);
        mQuestionPresenter = new QuestionPresenterImpl(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void addQuestions(List<QuestionBean> questionList) {
        mListView.setAdapter(new QuestionListViewAdapter(getActivity()));
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {

    }
}
