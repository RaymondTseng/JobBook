package com.example.jobbook.ui.person.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.LazyLoadFragment;
import com.example.jobbook.base.contract.person.PersonContract;
import com.example.jobbook.base.contract.person.UploadContract;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.presenter.person.UploadPresenter;
import com.example.jobbook.service.MyPushIntentService;
import com.example.jobbook.ui.cv.activity.TextCVActivity;
import com.example.jobbook.ui.main.activity.MainActivity;
import com.example.jobbook.ui.message.activity.GetMessageActivity;
import com.example.jobbook.ui.person.activity.FavouriteActivity;
import com.example.jobbook.ui.account.activity.LoginActivity;
import com.example.jobbook.ui.person.activity.SettingActivity;
import com.example.jobbook.ui.person.activity.ShowFanListActivity;
import com.example.jobbook.ui.person.activity.ShowFollowerListActivity;
import com.example.jobbook.ui.person.activity.ShowMomentListActivity;
import com.example.jobbook.util.CropUtil;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;
import com.example.jobbook.util.UploadUtil;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.UploadPopupWindow;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;


/**
 * Created by 椰树 on 2016/5/20.
 */
public class PersonFragment extends LazyLoadFragment implements PersonContract.View, UploadContract.View {
    private static int REFRESH = 0;
    private static int REFRESH_NAME = 1;
    private static int REFRESH_HEAD = 2;
    private static int REFRESH_UNREAD = 3;

    @BindView(R.id.person_setting_ll)
    LinearLayout mSettingLayout;
    //    private IPersonChanged mIPersonChanged;
//    private TextView mSwitchPerson2LoginTextView;

    @BindView(R.id.person_collect_ll)
    LinearLayout mFavouriteLayout;

    @BindView(R.id.person_name_tv)
    TextView mNameTextView;

    @BindView(R.id.person_company_position_tv)
    TextView mCompanyPositionTextView;
//    private Button mSwitch2TextCVButton;

    @BindView(R.id.person_head_bg)
    ImageView mHeadBackGround;

    @BindView(R.id.person_message_rl)
    RelativeLayout mMessageLayout;
//        private LinearLayout mBlackListLayout;

    @BindView(R.id.person_logout_tv)
    TextView mLogOutTextView;

    @BindView(R.id.person_moment_num_tv)
    TextView mMomentTextView;

    @BindView(R.id.person_follow_num_tv)
    TextView mFollowTextView;

    @BindView(R.id.person_fans_num_tv)
    TextView mFansTextView;

    @BindView(R.id.person_moment_num_ll)
    LinearLayout mMomentLL;

    @BindView(R.id.person_follow_num_ll)
    LinearLayout mFollowLL;

    @BindView(R.id.person_fans_num_ll)
    LinearLayout mFanLL;

    @BindView(R.id.fragment_person_loading)
    ViewStub mLoadingLayout;

    @BindView(R.id.person_title_head_iv)
    CircleImageView mCircleHeadImageView;

    @BindView(R.id.person_edit_tv)
    TextView mEditTextView;

    @BindView(R.id.unread_address_number)
    TextView mUnReadTextView;

    private UploadPresenter presenter;
    private MyApplication mMyApplication;
    private PersonBean personBean;
    private UploadPopupWindow mPopupWindow;
    private Uri mUri;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler;

    @Override
    protected int setContentView() {
        return R.layout.fragment_person;
    }

    @Override
    protected void lazyLoad() {
        initEvents();
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

    @Override
    protected void initViews() {
        personBean = MyApplication.getmPersonBean();
    }

    private void initEvents() {
        mLoadingLayout.inflate();
        mUri = null;
        handler = new PersonHandler();
        mMyApplication = (MyApplication) getActivity().getApplication();
        if (showPersonData()) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(REFRESH_UNREAD);
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean showPersonData() {
//        Bundle bundle = (Bundle) getArguments();
//        PersonBean personBean = (PersonBean) bundle.getSerializable("PersonBean");
        if (MyApplication.getmLoginStatus() != 0) {
            personBean = MyApplication.getmPersonBean();
            mNameTextView.setText(personBean.getUsername());
//            L.i("person-head", personBean.getHead());
            ImageLoadUtils.display(getActivity(), mCircleHeadImageView, personBean.getHead(), 0);
            ImageLoadUtils.display(getActivity(), mHeadBackGround, personBean.getHead(), 0);
            mMomentTextView.setText(personBean.getMoment());
            mFansTextView.setText(personBean.getFans());
            mFollowTextView.setText(personBean.getFollow());
            mCompanyPositionTextView.setText(personBean.getWorkSpace() + " " + personBean.getWorkPosition());
            return true;
        }
        return false;
    }

    @OnClick(R.id.person_logout_tv)
    public void logout() {
        createLogoutDialog();
    }

    @OnClick(R.id.person_setting_ll)
    public void setting() {
        Util.toAnotherActivity(getActivity(), SettingActivity.class);
    }

    @OnClick(R.id.person_collect_ll)
    public void favourite() {
        Util.toAnotherActivity(getActivity(), FavouriteActivity.class);
    }

    @OnClick(R.id.person_message_rl)
    public void message() {
        Util.toAnotherActivity(getActivity(), GetMessageActivity.class);
        mUnReadTextView.setVisibility(View.GONE);
        MainActivity.mBadgeView.setText("");
        MainActivity.mBadgeView.setVisibility(View.GONE);
        MyPushIntentService.num = 0;
    }

    @OnClick(R.id.person_moment_num_ll)
    public void moment() {
        Util.toAnotherActivity(getActivity(), ShowMomentListActivity.class);
    }

    @OnClick(R.id.person_follow_num_ll)
    public void follow() {
        Util.toAnotherActivity(getActivity(), ShowFollowerListActivity.class);
    }

    @OnClick(R.id.person_fans_num_ll)
    public void fan() {
        Util.toAnotherActivity(getActivity(), ShowFanListActivity.class);
    }

    @OnClick(R.id.person_title_head_iv)
    public void head() {
        mPopupWindow = new UploadPopupWindow(getActivity(), itemsOnClick);
        mPopupWindow.setBackgroundAlpha(getActivity(), 0.5f);
        mPopupWindow.showAtLocation(getActivity().findViewById(R.id.person_fragment_ll),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mPopupWindow.dismissOutSide(getActivity());
    }

    @OnClick(R.id.person_edit_tv)
    public void edit() {
        mMyApplication.setHandler(handler);
        Util.toAnotherActivity(getActivity(), TextCVActivity.class);
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 隐藏弹出窗口
            mPopupWindow.dismissPopupWindow(getActivity());
            switch (v.getId()) {
                case R.id.person_upload_takePhoto_bt:// 拍照
                    CropUtil.pickAvatarFromCamera(PersonFragment.this);
                    break;
                case R.id.person_upload_pickPhoto_bt:// 相册选择图片
                    CropUtil.pickAvatarFromGallery(PersonFragment.this);
                    break;
                case R.id.person_upload_cancel_bt:// 取消
//                    finish();
                    break;
            }
        }
    };

    private CropUtil.CropHandler cropHandler = new CropUtil.CropHandler() {
        @Override
        public void handleCropResult(Uri uri, int tag) {
            //send Image to Server
            MultipartBody.Part pic = UploadUtil.getMutilPartBodyFromUri(uri, "multipart/form-data");
            sendImage(pic);
            mUri = uri;
//            ImageLoadUtils.display(getActivity() , mCircleHeadImageView, uri);
//            ImageLoadUtils.display(getActivity(), mHeadBackGround, uri);
        }

        @Override
        public void handleCropError(Intent data) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropUtil.handleResult(getContext(), PersonFragment.this, cropHandler, requestCode, resultCode, data);
    }

    private void sendImage(MultipartBody.Part pic) {
        L.i("sendImage");
        presenter.uploadAvatar(pic);
    }

    @Override
    public void onResume() {
        super.onResume();
        L.i("on resume");
        refreshUnread();
        showPersonData();
//        onRefreshHead();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        refreshUnread();
    }

    private void onRefreshHead() {
        mCircleHeadImageView.setImageResource(R.mipmap.default_78px);
        L.i(personBean.getHead());
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
    public void showLoadFailMsg(String msg) {

    }

    @Override
    public void uploadSuccess() {
        Util.showSnackBar(MyApplication.mSnackBarView, "上传成功！");
    }

    @Override
    public void loadHead() {
        L.i("loadHead:" + mUri.toString());
//        mMyApplication.getHandler().sendEmptyMessage(2);
//        mCircleHeadImageView.setImageBitmap(bm);
//        mHeadBackGround.setImageBitmap(bm);
        Bitmap bm = UploadUtil.getBitmapFromUri(getContext(), mUri);
        mCircleHeadImageView.setImageBitmap(bm);
        mHeadBackGround.setImageBitmap(bm);
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

    public class PersonHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == REFRESH) {
                Util.showSnackBar(MyApplication.mSnackBarView, "保存成功！");
            } else if (msg.what == REFRESH_NAME) {
                showPersonData();
            } else if (msg.what == REFRESH_HEAD) {
                onRefreshHead();
            } else if (msg.what == REFRESH_UNREAD) {
                refreshUnread();
                if (personBean != null && MyApplication.getmPersonBean() != null) {
                    if (!personBean.toString().equals(MyApplication.getmPersonBean().toString())) {
                        L.i("refresh");
                        showPersonData();
                    }
                }
            }
        }
    }


}
