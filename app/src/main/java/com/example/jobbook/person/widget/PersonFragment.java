package com.example.jobbook.person.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.cv.widget.TextCVActivity;
import com.example.jobbook.feedback.widget.FeedBackActivity;
import com.example.jobbook.person.view.PersonView;
import com.example.jobbook.util.Util;


/**
 * Created by 椰树 on 2016/5/20.
 */
public class PersonFragment extends Fragment implements PersonView, View.OnClickListener {

    private ListView mListView;
    private ImageButton mSettingImageButton;
    private IPersonChanged mIPersonChanged;
    private Button mSwitchPerson2LoginButton;
    private Button mSwitch2FeedBackButton;
    private Button mFavouriteButton;
    private TextView mNameTextView;
    private Button mSwitch2TextCVButton;
    private RelativeLayout mUserDetailRelativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try {
            mIPersonChanged = (IPersonChanged) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement OnGridViewSelectedListener");
        }
    }

    private void initViews(View view) {
        mSwitchPerson2LoginButton = (Button) view.findViewById(R.id.person_switch2login_bt);
        mSwitch2FeedBackButton = (Button) view.findViewById(R.id.person_feedback_bt);
        mSettingImageButton = (ImageButton) view.findViewById(R.id.person_setting_ib);
        mFavouriteButton = (Button) view.findViewById(R.id.person_favourite_bt);
        mNameTextView = (TextView) view.findViewById(R.id.person_name_tv);
        mSwitch2TextCVButton = (Button) view.findViewById(R.id.person_textcv_bt);
        mUserDetailRelativeLayout = (RelativeLayout) view.findViewById(R.id.person_userinfo_rl);
    }

    private void initEvents() {
        mSwitchPerson2LoginButton.setOnClickListener(this);
        mSwitch2FeedBackButton.setOnClickListener(this);
        mSettingImageButton.setOnClickListener(this);
        mFavouriteButton.setOnClickListener(this);
        mSwitch2TextCVButton.setOnClickListener(this);
        mUserDetailRelativeLayout.setOnClickListener(this);
        showPersonData();
    }

    @Override
    public void showPersonData() {
        Bundle bundle = (Bundle) getArguments();
        PersonBean personBean = (PersonBean) bundle.getSerializable("PersonBean");
        mNameTextView.setText(personBean.getUsername());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_switch2login_bt:
                switchPerson2Login();
                break;
            case R.id.person_feedback_bt:
                Util.toAnotherActivity(getActivity(), FeedBackActivity.class);
                break;
            case R.id.person_setting_ib:
                Util.toAnotherActivity(getActivity(), SettingActivity.class);
                break;
            case R.id.person_favourite_bt:
                Util.toAnotherActivity(getActivity(), FavouriteActivity.class);
                break;
            case R.id.person_textcv_bt:
                Util.toAnotherActivity(getActivity(), TextCVActivity.class);
                break;
            case R.id.person_userinfo_rl:
                Util.toAnotherActivity(getActivity(), UserDetailActivity.class);
                break;
        }
    }

    @Override
    public void switchPerson2Login() {
        mIPersonChanged.switchPerson2Login();
    }

    public interface IPersonChanged {
        void switchPerson2Login();
    }
}
