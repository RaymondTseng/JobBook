package com.example.jobbook.person.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
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
import com.example.jobbook.feedback.widget.FeedBackActivity;
import com.example.jobbook.person.view.PersonView;
import com.example.jobbook.upload.widget.UploadPopupWindow;
import com.example.jobbook.upload.widget.UploadTakePhotoActivity;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.Util;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by 椰树 on 2016/5/20.
 */
public class PersonFragment extends Fragment implements PersonView, View.OnClickListener {

    private ListView mListView;
    private ImageButton mSettingImageButton;
    private CircleImageView mPersonHeadImageView;
    private RelativeLayout mPersonUserInfoRelativeLayout;
    private IPersonChanged mIPersonChanged;
    private Button mSwitchPerson2LoginButton;
    private Button mSwitch2FeedBackButton;
    private Button mFavouriteButton;
    private UploadPopupWindow mUploadPopupWindow;
    private TextView mNameTextView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);
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
        mPersonHeadImageView = (CircleImageView) view.findViewById(R.id.person_logo_iv);
        mPersonUserInfoRelativeLayout = (RelativeLayout) view.findViewById(R.id.person_userinfo_rl);
    }

    private void initEvents() {
        mSwitchPerson2LoginButton.setOnClickListener(this);
        mSwitch2FeedBackButton.setOnClickListener(this);
        mSettingImageButton.setOnClickListener(this);
        mFavouriteButton.setOnClickListener(this);
        mPersonHeadImageView.setOnClickListener(this);
        mPersonUserInfoRelativeLayout.setOnClickListener(this);
        showPersonData();
    }

    @Override
    public void showPersonData() {
        Bundle bundle = (Bundle) getArguments();
        PersonBean personBean = (PersonBean) bundle.getSerializable("PersonBean");
        mNameTextView.setText(personBean.getUsername());
        ImageLoadUtils.display(getActivity(), mPersonHeadImageView, personBean.getHead());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_switch2login_bt:
                switchPerson2Login();
                break;
            case R.id.person_feedback_bt:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.person_setting_ib:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.person_favourite_bt:
                startActivity(new Intent(getActivity(), FavouriteActivity.class));
                break;
            case R.id.person_logo_iv:
                mUploadPopupWindow = new UploadPopupWindow(getActivity(), onItemClick);
                mUploadPopupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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

    private View.OnClickListener onItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mUploadPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.person_upload_takePhoto_bt:
                    Util.toAnotherActivity(getActivity(), UploadTakePhotoActivity.class);
                    break;
                case R.id.person_upload_pickPhoto_bt:
                    Intent pickIntent = new Intent(Intent.ACTION_PICK);
                    pickIntent.setType("image/*");
                    getActivity().startActivityForResult(pickIntent, 1);
                    break;
                case R.id.person_upload_cancel_bt:
                    mUploadPopupWindow.dismiss();
                    break;
            }
        }
    };

}
