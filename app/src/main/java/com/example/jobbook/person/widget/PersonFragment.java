package com.example.jobbook.person.widget;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.cv.widget.TextCVActivity;
import com.example.jobbook.feedback.widget.FeedBackActivity;
import com.example.jobbook.login.widget.LoginActivity;
import com.example.jobbook.person.view.PersonView;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by 椰树 on 2016/5/20.
 */
public class PersonFragment extends Fragment implements PersonView, View.OnClickListener {
    private static int REFRESH = 0;
    private static int REFRESH_NAME = 1;
    private static int REFRESH_HEAD = 2;
    private ListView mListView;
    private ImageButton mSettingImageButton;
    //    private IPersonChanged mIPersonChanged;
    private Button mSwitchPerson2LoginButton;
    private Button mSwitch2FeedBackButton;
    private Button mFavouriteButton;
    private TextView mNameTextView;
    private Button mSwitch2TextCVButton;
    private RelativeLayout mUserDetailRelativeLayout;
    private CircleImageView mCircleImageView;
    private MyApplication mMyApplication;
    private PersonBean personBean;

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == REFRESH) {
                Util.showSnackBar(getActivity(), "保存成功！");
            } else if (msg.what == REFRESH_NAME) {
                mNameTextView.setText(MyApplication.getmPersonBean().getUsername());
            } else if (msg.what == REFRESH_HEAD) {
                onRefreshHead();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        initViews(view);
        initEvents();
        L.i("person", "createview");
        return view;
    }

//    @Override
//    public void onAttach(Activity activity) {
//        // TODO Auto-generated method stub
//        super.onAttach(activity);
//        try {
////            mIPersonChanged = (IPersonChanged) activity;
//            mIPersonChanged = (IPersonChanged) getParentFragment();
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + "must implement OnGridViewSelectedListener");
//        }
//    }

    private void initViews(View view) {
        mSwitchPerson2LoginButton = (Button) view.findViewById(R.id.person_switch2login_bt);
        mSwitch2FeedBackButton = (Button) view.findViewById(R.id.person_feedback_bt);
        mSettingImageButton = (ImageButton) view.findViewById(R.id.person_setting_ib);
        mFavouriteButton = (Button) view.findViewById(R.id.person_favourite_bt);
        mNameTextView = (TextView) view.findViewById(R.id.person_name_tv);
        mSwitch2TextCVButton = (Button) view.findViewById(R.id.person_textcv_bt);
        mUserDetailRelativeLayout = (RelativeLayout) view.findViewById(R.id.person_userinfo_rl);
        mCircleImageView = (CircleImageView) view.findViewById(R.id.person_logo_iv);
    }

    private void initEvents() {
        mSwitchPerson2LoginButton.setOnClickListener(this);
        mSwitch2FeedBackButton.setOnClickListener(this);
        mSettingImageButton.setOnClickListener(this);
        mFavouriteButton.setOnClickListener(this);
        mSwitch2TextCVButton.setOnClickListener(this);
        mUserDetailRelativeLayout.setOnClickListener(this);
        mMyApplication = (MyApplication) getActivity().getApplication();
        showPersonData();
    }

    @Override
    public void showPersonData() {
//        Bundle bundle = (Bundle) getArguments();
//        PersonBean personBean = (PersonBean) bundle.getSerializable("PersonBean");
        if(MyApplication.getmLoginStatus() != 0){
            personBean = MyApplication.getmPersonBean();
            mNameTextView.setText(personBean.getUsername());
            ImageLoadUtils.display(getActivity(), mCircleImageView, personBean.getHead(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_switch2login_bt:
                createLogoutDialog();
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
                mMyApplication.setHandler(handler);
                Util.toAnotherActivity(getActivity(), TextCVActivity.class);
                break;
            case R.id.person_userinfo_rl:
                mMyApplication.setHandler(handler);
                Util.toAnotherActivity(getActivity(), UserDetailActivity.class);
                break;
        }
    }

//    public interface IPersonChanged {
//        void switchPerson2Login();
//    }

    @Override
    public void onResume() {
        super.onResume();
        L.i("personfragment", "on resume");
//        onRefreshHead();
    }

    private void onRefreshHead() {
        mCircleImageView.setImageResource(R.mipmap.default_78px);
        L.i("onRefreshHead", personBean.getHead());
        ImageLoadUtils.display(getActivity(), mCircleImageView, personBean.getHead(), 0);
    }

    private void createLogoutDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = Util.dip2px(getActivity(), 280);
        p.height = Util.dip2px(getActivity(), 109);
//        window.setLayout(Util.dip2px(getActivity(), Util.getWidth(getActivity()) * 1 / 4), Util.dip2px(getActivity(), Util.getHeight(getActivity()) * 1 / 13));
        window.setAttributes(p);
        window.setContentView(R.layout.logout_sure_layout);
        TextView mSureTextView = (TextView) window.findViewById(R.id.logout_sure_tv);
        TextView mCancelTextView = (TextView) window.findViewById(R.id.logout_cancel_tv);
        mSureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.toAnotherActivity(getActivity(), LoginActivity.class);
                MyApplication.clearmPersonBean(getActivity());
                getActivity().finish();
                alertDialog.dismiss();
            }
        });
        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

}
