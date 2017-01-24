package com.example.jobbook.person.widget;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.cv.widget.TextCVActivity;
import com.example.jobbook.login.widget.LoginActivity;
import com.example.jobbook.main.widget.MainActivity;
import com.example.jobbook.message.widget.GetMessageActivity;
import com.example.jobbook.person.presenter.UploadPresenter;
import com.example.jobbook.person.presenter.UploadPresenterImpl;
import com.example.jobbook.person.view.PersonView;
import com.example.jobbook.person.view.UploadView;
import com.example.jobbook.service.MyPushIntentService;
import com.example.jobbook.upload.CropUtils;
import com.example.jobbook.upload.UploadManager;
import com.example.jobbook.upload.UploadPopupWindow;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by 椰树 on 2016/5/20.
 */
public class PersonFragment extends Fragment implements PersonView, View.OnClickListener, UploadView {
    private static int REFRESH = 0;
    private static int REFRESH_NAME = 1;
    private static int REFRESH_HEAD = 2;
    private static int REFRESH_UNREAD = 3;
    private ListView mListView;
    private LinearLayout mSettingLayout;
    //    private IPersonChanged mIPersonChanged;
    private TextView mSwitchPerson2LoginTextView;
    private LinearLayout mFavouriteLayout;
    private TextView mNameTextView;
    private TextView mCompanyPositionTextView;
    private Button mSwitch2TextCVButton;
    private ImageView mHeadBackGround;
    private RelativeLayout mMessageLayout;
    private UploadPresenter presenter;
    //    private LinearLayout mBlackListLayout;
    private TextView mLogOutTextView;
    private TextView mMomentTextView;
    private TextView mFollowTextView;
    private TextView mFansTextView;
    private LinearLayout mMomentLL;
    private LinearLayout mFollowLL;
    private LinearLayout mFanLL;
    private LinearLayout mLoadingLayout;
    private CircleImageView mCircleHeadImageView;
    private MyApplication mMyApplication;
    private PersonBean personBean;
    private UploadPopupWindow mPopupWindow;
    //    private TextView mEditTextView;
    private TextView mUnReadTextView;
    private View view;
    private Uri mUri;
    private Timer timer;
    private TimerTask timerTask;

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == REFRESH) {
                Util.showSnackBar(MainActivity.mSnackBarView, "保存成功！");
            } else if (msg.what == REFRESH_NAME) {
                mNameTextView.setText(MyApplication.getmPersonBean().getUsername());
            } else if (msg.what == REFRESH_HEAD) {
                onRefreshHead();
            } else if (msg.what == REFRESH_UNREAD) {
                refreshUnread();
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
        mMessageLayout = (RelativeLayout) view.findViewById(R.id.person_message_rl);
        mLoadingLayout = (LinearLayout) view.findViewById(R.id.loading_circle_progress_bar_ll);
//        mBlackListLayout = (LinearLayout) view.findViewById(R.id.person_black_list_ll);
        mLogOutTextView = (TextView) view.findViewById(R.id.person_logout_tv);
        mUnReadTextView = (TextView) view.findViewById(R.id.unread_address_number);
        mCircleHeadImageView = (CircleImageView) view.findViewById(R.id.person_title_head_iv);
    }

    private void initEvents() {
        mUri = null;
        presenter = new UploadPresenterImpl(this);
        mSwitchPerson2LoginTextView.setOnClickListener(this);
        mSettingLayout.setOnClickListener(this);
        mFavouriteLayout.setOnClickListener(this);
        mMessageLayout.setOnClickListener(this);
//        mBlackListLayout.setOnClickListener(this);
        mLogOutTextView.setOnClickListener(this);
        mCircleHeadImageView.setOnClickListener(this);
//        mEditTextView.setOnClickListener(this);
//        mMomentTextView.setOnClickListener(this);
//        mFollowTextView.setOnClickListener(this);
//        mFansTextView.setOnClickListener(this);
        mMomentLL.setOnClickListener(this);
        mFollowLL.setOnClickListener(this);
        mFanLL.setOnClickListener(this);
        mMyApplication = (MyApplication) getActivity().getApplication();
        showPersonData();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(REFRESH_UNREAD);
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    @Override
    public void showPersonData() {
//        Bundle bundle = (Bundle) getArguments();
//        PersonBean personBean = (PersonBean) bundle.getSerializable("PersonBean");
        if (MyApplication.getmLoginStatus() != 0) {
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
            case R.id.person_message_rl:
                Util.toAnotherActivity(getActivity(), GetMessageActivity.class);
                mUnReadTextView.setVisibility(View.GONE);
                MainActivity.mBadgeView.setText("");
                MainActivity.mBadgeView.setVisibility(View.GONE);
                MyPushIntentService.num = 0;
                break;
            case R.id.person_moment_num_ll:
                Util.toAnotherActivity(getActivity(), ShowMomentListActivity.class);
                L.i("showmoment", "click");
                break;
            case R.id.person_follow_num_ll:
                Util.toAnotherActivity(getActivity(), ShowFollowerListActivity.class);
                break;
            case R.id.person_fans_num_ll:
                Util.toAnotherActivity(getActivity(), ShowFanListActivity.class);
                break;
            case R.id.person_title_head_iv:
                mPopupWindow = new UploadPopupWindow(getActivity(), itemsOnClick);
                mPopupWindow.showAtLocation(getActivity().findViewById(R.id.person_fragment_ll),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.person_edit_tv:
                mMyApplication.setHandler(handler);
                Util.toAnotherActivity(getActivity(), TextCVActivity.class);
                break;
//            case R.id.person_textcv_bt:
//                mMyApplication.setHandler(handler);
//                Util.toAnotherActivity(getActivity(), TextCVActivity.class);
//                break;
//            case R.id.person_userinfo_rl:
//                mMyApplication.setHandler(handler);
//                Util.toAnotherActivity(getActivity(), OldUserDetailActivity.class);
//                break;
        }
    }

//    public interface IPersonChanged {
//        void switchPerson2Login();
//    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 隐藏弹出窗口
            mPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.person_upload_takePhoto_bt:// 拍照
                    CropUtils.pickAvatarFromCamera(getActivity());
                    break;
                case R.id.person_upload_pickPhoto_bt:// 相册选择图片
                    CropUtils.pickAvatarFromGallery(getActivity());
                    break;
                case R.id.person_upload_cancel_bt:// 取消
//                    finish();
                    break;
            }
        }
    };

    private CropUtils.CropHandler cropHandler = new CropUtils.CropHandler() {
        @Override
        public void handleCropResult(Uri uri, int tag) {
            //send Image to Server
            sendImage(UploadManager.getBitmapFromUri(getActivity().getApplicationContext(), uri));
            mUri = uri;
//            ImageLoadUtils.display(OldUserDetailActivity.this , mUserHeadImageView, uri);
        }

        @Override
        public void handleCropError(Intent data) {

        }
    };

    private void sendImage(Bitmap bm) {
        L.i("photo", "sendImage");
        presenter.uploadImage(bm);
    }

    @Override
    public void onResume() {
        super.onResume();
        L.i("personfragment", "on resume");
        refreshUnread();
//        onRefreshHead();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        refreshUnread();
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

    @Override
    public void showProgress() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void uploadSuccess() {
        Util.showSnackBar(view, "上传成功！");
    }

    @Override
    public void uploadFailure() {
        Util.showSnackBar(view, "上传失败，请重试！");
    }

    @Override
    public void loadHead(Bitmap bm) {
        L.i("photo", "loadHead1:" + mUri.toString());
        mMyApplication.getHandler().sendEmptyMessage(2);
//        mUserHeadImageView.setImageBitmap(bm);
        mCircleHeadImageView.setImageURI(mUri);
        bm.recycle();
//        this.onCreate(null);
    }

    private void refreshUnread() {
        if (!TextUtils.isEmpty(MainActivity.mBadgeView.getText()) && Integer.valueOf(MainActivity.mBadgeView.getText().toString()) != 0) {
            mUnReadTextView.setVisibility(View.VISIBLE);
            mUnReadTextView.setText(MainActivity.mBadgeView.getText());
        } else {
            mUnReadTextView.setVisibility(View.GONE);
        }
    }


}
