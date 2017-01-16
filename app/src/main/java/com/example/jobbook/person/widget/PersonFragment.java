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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
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
    private LinearLayout mSettingLayout;
    //    private IPersonChanged mIPersonChanged;
    private TextView mSwitchPerson2LoginTextView;
    private LinearLayout mFavouriteLayout;
    private TextView mNameTextView;
    private TextView mCompanyPositionTextView;
    private Button mSwitch2TextCVButton;
    private ImageView mHeadBackGround;
    private LinearLayout mMessageLayout;
//    private LinearLayout mBlackListLayout;
    private TextView mLogOutTextView;
    private TextView mMomentTextView;
    private TextView mFollowTextView;
    private TextView mFansTextView;
    private LinearLayout mMomentLL;
    private LinearLayout mFollowLL;
    private LinearLayout mFanLL;
    private CircleImageView mCircleHeadImageView;
    private MyApplication mMyApplication;
    private PersonBean personBean;
//    private TextView mEditTextView;
    private View view;

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == REFRESH) {
                Util.showSnackBar(view, "保存成功！");
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
        view = inflater.inflate(R.layout.fragment_person, container, false);
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
        mSwitchPerson2LoginTextView = (TextView) view.findViewById(R.id.person_logout_tv);
        mSettingLayout = (LinearLayout) view.findViewById(R.id.person_setting_ll);
        mFavouriteLayout = (LinearLayout) view.findViewById(R.id.person_collect_ll);
        mNameTextView = (TextView) view.findViewById(R.id.person_name_tv);
        mCompanyPositionTextView = (TextView) view.findViewById(R.id.person_company_position_tv);
        mHeadBackGround = (ImageView) view.findViewById(R.id.person_head_bg);
        mMomentTextView = (TextView) view.findViewById(R.id.person_moment_num_tv);
        mFollowTextView = (TextView) view.findViewById(R.id.person_follow_num_tv);
        mFansTextView = (TextView) view.findViewById(R.id.person_fans_num_tv);
        mMomentLL = (LinearLayout) view.findViewById(R.id.person_moment_num_ll);
        mFollowLL = (LinearLayout) view.findViewById(R.id.person_follow_num_ll);
        mFanLL = (LinearLayout) view.findViewById(R.id.person_fans_num_ll);
        mMessageLayout = (LinearLayout) view.findViewById(R.id.person_message_ll);
//        mBlackListLayout = (LinearLayout) view.findViewById(R.id.person_black_list_ll);
        mLogOutTextView = (TextView) view.findViewById(R.id.person_logout_tv);
//        mEditTextView = (TextView) view.findViewById(R.id.person_edit_tv);
        mCircleHeadImageView = (CircleImageView) view.findViewById(R.id.person_title_head_iv);
    }

    private void initEvents() {
        mSwitchPerson2LoginTextView.setOnClickListener(this);
        mSettingLayout.setOnClickListener(this);
        mFavouriteLayout.setOnClickListener(this);
        mMessageLayout.setOnClickListener(this);
//        mBlackListLayout.setOnClickListener(this);
        mLogOutTextView.setOnClickListener(this);
//        mEditTextView.setOnClickListener(this);
//        mMomentTextView.setOnClickListener(this);
//        mFollowTextView.setOnClickListener(this);
//        mFansTextView.setOnClickListener(this);
        mMomentLL.setOnClickListener(this);
        mFollowLL.setOnClickListener(this);
        mFanLL.setOnClickListener(this);
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
            ImageLoadUtils.display(getActivity(), mCircleHeadImageView, personBean.getHead(), 0);
            ImageLoadUtils.display(getActivity(), mHeadBackGround, personBean.getHead(), 0);
            mMomentTextView.setText(personBean.getMoment());
            mFansTextView.setText(personBean.getFans());
            mFollowTextView.setText(personBean.getFollow());
            mCompanyPositionTextView.setText(personBean.getWorkSpace() + " " + personBean.getWorkPosition());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_logout_tv:
                createLogoutDialog();
                break;
//            case R.id.person_feedback_bt:
//                Util.toAnotherActivity(getActivity(), FeedBackActivity.class);
//                break;
            case R.id.person_setting_ll:
                Util.toAnotherActivity(getActivity(), SettingActivity.class);
                break;
            case R.id.person_collect_ll:
                Util.toAnotherActivity(getActivity(), FavouriteActivity.class);
                break;
//            case R.id.person_black_list_ll:
//                break;
            case R.id.person_message_ll:
                break;
            case R.id.person_moment_num_ll:
                break;
            case R.id.person_follow_num_ll:
                break;
            case R.id.person_fans_num_ll:
                Util.toAnotherActivity(getActivity(), ShowFanListActivity.class);
                break;
//            case R.id.person_edit_tv:
//                break;
//            case R.id.person_textcv_bt:
//                mMyApplication.setHandler(handler);
//                Util.toAnotherActivity(getActivity(), TextCVActivity.class);
//                break;
//            case R.id.person_userinfo_rl:
//                mMyApplication.setHandler(handler);
//                Util.toAnotherActivity(getActivity(), UserDetailActivity.class);
//                break;
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
        mCircleHeadImageView.setImageResource(R.mipmap.default_78px);
        L.i("onRefreshHead", personBean.getHead());
        ImageLoadUtils.display(getActivity(), mCircleHeadImageView, personBean.getHead(), 0);
        ImageLoadUtils.display(getActivity(), mHeadBackGround, personBean.getHead(), 0);
    }

    private void createLogoutDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
//        p.width = Util.dip2px(this, 280);
//        p.height = Util.dip2px(this, 109);
        p.width = Util.dip2px(getActivity(), 300);
        p.height = Util.dip2px(getActivity(), 140);
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
