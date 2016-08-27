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
import com.example.jobbook.feedback.widget.FeedBackActivity;
import com.example.jobbook.person.view.PersonView;


/**
 * Created by 椰树 on 2016/5/20.
 */
public class PersonFragment extends Fragment implements PersonView, View.OnClickListener{

    private ListView mListView;
    private ImageButton mSettingImageButton;
    private IPersonChanged mIPersonChanged;
    private Button mSwitch2PersonButton;
    private TextView mSwitch2FeedBackTextView;
    private RelativeLayout mFavouriteLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        initViews(view);
        Bundle bundle = getArguments().getBundle("PersonBean");
        if(bundle != null){
            Log.i("TAG", "bundle is not null");
        }
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

    public void initViews(View view) {
        mSwitch2PersonButton = (Button) view.findViewById(R.id.person_switch2login_bt);
        mSwitch2FeedBackTextView = (TextView) view.findViewById(R.id.person_feedback_tv);
        mSwitch2PersonButton.setOnClickListener(this);
        mSwitch2FeedBackTextView.setOnClickListener(this);
        mSettingImageButton = (ImageButton) view.findViewById(R.id.person_setting_ib);
        mFavouriteLayout = (RelativeLayout) view.findViewById(R.id.person_favourite_ll);
        mSwitch2PersonButton.setOnClickListener(this);
        mSettingImageButton.setOnClickListener(this);
        mFavouriteLayout.setOnClickListener(this);
    }

    @Override
    public void showPersonData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_switch2login_bt:
                switch2Login();
                break;
            case R.id.person_feedback_tv:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.person_setting_ib:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.person_favourite_ll:
                startActivity(new Intent(getActivity(), FavouriteActivity.class));
                break;
        }
    }

    @Override
    public void switch2Login() {
        mIPersonChanged.switchPerson2Login();
    }

    public interface IPersonChanged {
        void switchPerson2Login();
    }
}
