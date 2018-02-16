package com.example.jobbook.ui.cv.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.cv.TextCVContract;
import com.example.jobbook.base.contract.person.UploadContract;
import com.example.jobbook.model.bean.EducationExpBean;
import com.example.jobbook.model.bean.JobExpBean;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.bean.TextCVBean;
import com.example.jobbook.presenter.cv.TextCVPresenter;
import com.example.jobbook.presenter.person.UploadPresenter;
import com.example.jobbook.util.CropUtil;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;
import com.example.jobbook.util.UploadUtil;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.UploadPopupWindow;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;

/**
 * Created by 椰树 on 2016/8/25.
 */
public class TextCVActivity extends AppCompatActivity implements OnDateSetListener,
        TextCVContract.View, UploadContract.View, View.OnClickListener {
    private static final String EDU_ADMISSION = "1";
    private static final String EDU_GRADUATION = "2";
    private static final String JOB_INAUGURATION = "3";
    private static final String JOB_DIMISSION = "4";

    @BindView(R.id.text_cv_activity_ll)
    LinearLayout mParentLayout;

    private TimePickerDialog mJobExpInaugurationDialog;
    private TimePickerDialog mJobExpDimissionDialog;
    private TimePickerDialog mEduExpAdmissionDialog;
    private TimePickerDialog mEduExpGraduationDialog;

    @BindView(R.id.job_exp_inauguration_tv)
    TextView mJobExpInaugurationTextView;

    @BindView(R.id.job_exp_dimission_tv)
    TextView mJobExpDimissionTextView;

    @BindView(R.id.education_exp_admission_tv)
    TextView mEduExpAdmissionTextView;

    @BindView(R.id.education_exp_graduation_tv)
    TextView mEduExpGraduationTextView;

    @BindView(R.id.text_cv_edu_container)
    LinearLayout mEduContainerLayout;

    @BindView(R.id.text_cv_job_container)
    LinearLayout mJobContainerLayout;

    @BindView(R.id.text_cv_add_edu_exp_ib)
    ImageButton mAddEduExpImageButton;

    @BindView(R.id.text_cv_add_job_exp_ib)
    ImageButton mAddJobExpImageButton;

    @BindView(R.id.text_cv_close_ib)
    ImageButton mCloseImageButton;

    @BindView(R.id.text_cv_save_tv)
    TextView mSaveTextView;

    @BindView(R.id.text_cv_name_et)
    EditText mNameEditText;

    @BindView(R.id.text_cv_sex_spinner)
    Spinner mSexSpinner;

    @BindView(R.id.text_cv_status_et)
    EditText mStatusEditText;

    @BindView(R.id.text_cv_company_et)
    EditText mCompanyEditText;

    @BindView(R.id.text_cv_position_et)
    EditText mPositionEditText;

    @BindView(R.id.text_cv_location_et)
    EditText mCityEditText;

    @BindView(R.id.text_cv_type_spinner)
    Spinner mTypeSpinner;

    @BindView(R.id.text_cv_level_spinner)
    Spinner mLevelSpinner;

    @BindView(R.id.text_cv_certificate_spinner)
    Spinner mCertificationSpinner;

    @BindView(R.id.text_cv_tel_et)
    EditText mTelEditText;

    @BindView(R.id.text_cv_email_et)
    EditText mEmailEditText;

    @BindView(R.id.text_cv_expect_job_positon_et)
    EditText mExpectJobEditText;

    @BindView(R.id.text_cv_expect_job_salary_et)
    EditText mExpectSalaryEditText;

    @BindView(R.id.text_cv_expect_job_location_et)
    EditText mExpectLocationEditText;

    @BindView(R.id.edu_exp_divider)
    TextView mEduDivider;

    @BindView(R.id.job_exp_divider)
    TextView mJobDivider;

    @BindView(R.id.text_cv_head_iv)
    CircleImageView mUserHeadImageView;

    @BindView(R.id.activity_text_cv_loading)
    ViewStub mLoadingLayout;

    private UploadPopupWindow mPopupWindow;
    private TextCVPresenter mPresenter;
    private UploadPresenter uploadPresenter;
    private Uri mUri;
    private MyApplication myApplication;
    private TextCVBean mTextCVBean;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_cv);
        ButterKnife.bind(this);
        initView();
        initEvents();
    }

    private void initView() {
        mLoadingLayout.inflate();
    }

    @SuppressWarnings("ResourceType")
    private void initEvents() {
        mUri = null;
        myApplication = (MyApplication) getApplication();
        mPresenter = new TextCVPresenter(this);
        uploadPresenter = new UploadPresenter(this);
        mPresenter.load();
        mJobExpInaugurationTextView.setTag(0);
        mJobExpDimissionTextView.setTag(0);
        mEduExpAdmissionTextView.setTag(0);
        mEduExpGraduationTextView.setTag(0);
        mJobExpInaugurationDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setTitleStringId("选择时间")
                .setThemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCallBack(this)
                .build();
        mJobExpDimissionDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setTitleStringId("选择时间")
                .setThemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCallBack(this)
                .build();
        mEduExpAdmissionDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setTitleStringId("选择时间")
                .setThemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCallBack(this)
                .build();
        mEduExpGraduationDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setTitleStringId("选择时间")
                .setThemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCallBack(this)
                .build();

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date d = new Date(millseconds);
        String text = sf.format(d);
        String tag = timePickerView.getTag();
        if (tag.startsWith(JOB_DIMISSION)) {
            View view = mJobContainerLayout.getChildAt(Integer.valueOf(tag.split(";")[1]));
            TextView mJobExpDimissionTextView = (TextView) view.findViewById(R.id.job_exp_dimission_tv);
            mJobExpDimissionTextView.setText(text);
        } else if (tag.startsWith(JOB_INAUGURATION)) {
            View view = mJobContainerLayout.getChildAt(Integer.valueOf(tag.split(";")[1]));
            TextView mJobExpInaugurationTextView = (TextView) view.findViewById(R.id.job_exp_inauguration_tv);
            mJobExpInaugurationTextView.setText(text);
        } else if (tag.startsWith(EDU_ADMISSION)) {
            View view = mEduContainerLayout.getChildAt(Integer.valueOf(tag.split(";")[1]));
            TextView mEduExpAdmissionTextView = (TextView) view.findViewById(R.id.education_exp_admission_tv);
            mEduExpAdmissionTextView.setText(text);
        } else {
            View view = mEduContainerLayout.getChildAt(Integer.valueOf(tag.split(";")[1]));
            TextView mEduExpGraduationTextView = (TextView) view.findViewById(R.id.education_exp_graduation_tv);
            mEduExpGraduationTextView.setText(text);
        }
    }

    private View addJobView(LinearLayout mJobContainerLayout) {
        LinearLayout jobView = (LinearLayout) View.inflate(this, R.layout.job_experiment_layout, null);
        View jobDeleteView = View.inflate(this, R.layout.delete_exp_layout, null);
        LinearLayout.LayoutParams jobParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        jobView.addView(jobDeleteView, jobParams);
        mJobContainerLayout.addView(jobView, jobParams);
        return jobView;
    }

    private void initJobView(View jobView) {
        TextView mInaugurationTextView = (TextView) jobView.findViewById(R.id.job_exp_inauguration_tv);
        TextView mDimissionTextView = (TextView) jobView.findViewById(R.id.job_exp_dimission_tv);
//        EditText mCompantEditText = (EditText) jobView.findViewById(R.id.job_exp_company_et);
//        EditText mPositionEditText = (EditText) jobView.findViewById(R.id.job_exp_position_et);
        mInaugurationTextView.setTag(mJobContainerLayout.getChildCount() - 1);
        mDimissionTextView.setTag(mJobContainerLayout.getChildCount() - 1);
    }

    private void initJobView(View jobView, JobExpBean jobExpBean) {
        initJobView(jobView);
        ((TextView) jobView.findViewById(R.id.job_exp_inauguration_tv)).setText(jobExpBean.getInaugurationDate());
        ((TextView) jobView.findViewById(R.id.job_exp_dimission_tv)).setText(jobExpBean.getDimissionDate());
        ((EditText) jobView.findViewById(R.id.job_exp_company_et)).setText(jobExpBean.getCompany());
        ((EditText) jobView.findViewById(R.id.job_exp_position_et)).setText(jobExpBean.getPosition());
    }

    private View addEduView(LinearLayout mEduContainerLayout) {
        LinearLayout eduView = (LinearLayout) View.inflate(this, R.layout.education_experiment_layout, null);
        View eduDeleteView = View.inflate(this, R.layout.delete_exp_layout, null);
        LinearLayout.LayoutParams eduParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        eduView.addView(eduDeleteView, eduParams);
        mEduContainerLayout.addView(eduView, eduParams);
        return eduView;
    }

    private void initEduView(View eduView) {
        TextView mAdmissionTextView = (TextView) eduView.findViewById(R.id.education_exp_admission_tv);
        TextView mGraduationTextView = (TextView) eduView.findViewById(R.id.education_exp_graduation_tv);
//        EditText mSchoolEditText = (EditText) eduView.findViewById(R.id.education_school_et);
//        EditText mMajorEditText = (EditText) eduView.findViewById(R.id.education_major_et);
        mAdmissionTextView.setTag(mEduContainerLayout.getChildCount() - 1);
        mGraduationTextView.setTag(mEduContainerLayout.getChildCount() - 1);
    }

    private void initEduView(View eduView, EducationExpBean educationExpBean) {
        initEduView(eduView);
        ((TextView) eduView.findViewById(R.id.education_exp_admission_tv)).setText(educationExpBean.getAdmissionDate());
        ((TextView) eduView.findViewById(R.id.education_exp_graduation_tv)).setText(educationExpBean.getGraduationDate());
        ((EditText) eduView.findViewById(R.id.education_school_et)).setText(educationExpBean.getSchool());
        ((EditText) eduView.findViewById(R.id.education_major_et)).setText(educationExpBean.getMajor());
    }

    @OnClick(R.id.text_cv_activity_ll)
    public void click_text_cv_activity_ll(View v) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @OnClick(R.id.job_exp_inauguration_tv)
    public void click_job_exp_inauguration_tv(View v) {
        mJobExpInaugurationDialog.show(getSupportFragmentManager(), JOB_INAUGURATION + ";" + v.getTag());
        mJobDivider.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorBackgroundGray));
    }

    @OnClick(R.id.job_exp_dimission_tv)
    public void click_job_exp_dimission_tv(View v) {
        mJobExpDimissionDialog.show(getSupportFragmentManager(), JOB_DIMISSION + ";" + v.getTag());
        mJobDivider.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorBackgroundGray));
    }

    @OnClick(R.id.education_exp_admission_tv)
    public void click_education_exp_admission_tv(View v) {
        mEduExpAdmissionDialog.show(getSupportFragmentManager(), EDU_ADMISSION + ";" + v.getTag());
        mEduDivider.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorBackgroundGray));
    }

    @OnClick(R.id.education_exp_graduation_tv)
    public void click_education_exp_graduation_tv(View v) {
        mEduExpGraduationDialog.show(getSupportFragmentManager(), EDU_GRADUATION + ";" + v.getTag());
        mEduDivider.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorBackgroundGray));
    }

    @OnClick(R.id.text_cv_close_ib)
    public void click_close_ib() {
        close();
    }

    @OnClick(R.id.text_cv_save_tv)
    public void click_save_tv() {
        save();
    }

    @OnClick(R.id.text_cv_add_edu_exp_ib)
    public void click_text_cv_add_edu_exp_ib() {
        View eduView = addEduView(mEduContainerLayout);
        initEduView(eduView);
        ImageButton mEduDeleteImageButton = (ImageButton) eduView.findViewById(R.id.delete_exp_ib);
        mEduDeleteImageButton.setTag(EDU_ADMISSION + ";" + (mEduContainerLayout.getChildCount() - 1));
        mEduDeleteImageButton.setOnClickListener(this);
    }

    @OnClick(R.id.text_cv_add_job_exp_ib)
    public void click_text_cv_add_job_exp_ib() {
        View jobView = addJobView(mJobContainerLayout);
        initJobView(jobView);
        ImageButton mJobDeleteImageButton = (ImageButton) jobView.findViewById(R.id.delete_exp_ib);
        mJobDeleteImageButton.setTag(JOB_INAUGURATION + ";" + (mJobContainerLayout.getChildCount() - 1));
        mJobDeleteImageButton.setOnClickListener(this);
    }

    @OnClick(R.id.text_cv_head_iv)
    public void click_text_cv_head_iv() {
        mPopupWindow = new UploadPopupWindow(this, itemsOnClick);
        mPopupWindow.showAtLocation(this.findViewById(R.id.text_cv_activity_ll),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 隐藏弹出窗口
            mPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.person_upload_takePhoto_bt:// 拍照
                    CropUtil.pickAvatarFromCamera(TextCVActivity.this);
                    break;
                case R.id.person_upload_pickPhoto_bt:// 相册选择图片
                    CropUtil.pickAvatarFromGallery(TextCVActivity.this);
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
            ImageLoadUtils.display(TextCVActivity.this, mUserHeadImageView, mUri);
        }

        @Override
        public void handleCropError(Intent data) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropUtil.handleResult(this, cropHandler, requestCode, resultCode, data);
    }

    private void sendImage(MultipartBody.Part pic) {
        L.i("sendImage");
        uploadPresenter.uploadAvatar(pic);
    }

    @Override
    public void success(PersonBean personBean) {
        MyApplication.setmPersonBean(this, personBean);
        MyApplication myApplication = (MyApplication) getApplication();
        Handler handler = myApplication.getHandler();
        if (handler != null) {
            handler.sendEmptyMessage(1);
            myApplication.setHandler(null);
        }
        close();
    }

    @Override
    public void networkError() {
        Util.showSnackBar(getWindow().getDecorView(), "网络错误！");
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
        Util.showSnackBar(getWindow().getDecorView(), "上传成功！");
    }

    @Override
    public void loadHead() {
        L.i("loadHead1:" + mUri.toString());
        myApplication.getHandler().sendEmptyMessage(2);
//        mUserHeadImageView.setImageBitmap(bm);
        Bitmap bm = UploadUtil.getBitmapFromUri(getApplication(), mUri);
        mUserHeadImageView.setImageBitmap(bm);
//        bm.recycle();
    }

    @Override
    public void headBlankError() {

    }

    @Override
    public void nameBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_name_tv);
        setBackgroundRed(mNameEditText, textView);
    }


    @Override
    public void companyBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_company_tv);
        setBackgroundRed(mCompanyEditText, textView);
    }

    @Override
    public void positionBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_position_tv);
        setBackgroundRed(mPositionEditText, textView);
    }

    @Override
    public void statusBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_status_tv);
        setBackgroundRed(mStatusEditText, textView);
    }

    @Override
    public void locationBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_location_tv);
        setBackgroundRed(mCityEditText, textView);
    }

    @Override
    public void levelBlankError() {
//        TextView textView = (TextView) findViewById(R.id.text_cv_level_tv);
//        setBackgroundRed(mLevelEditText, textView);
    }

    @Override
    public void typeBlankError() {
//        TextView textView = (TextView) findViewById(R.id.text_cv_type_tv);
//        setBackgroundRed(mTypeEditText, textView);
    }

    @Override
    public void telBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_tel_tv);
        setBackgroundRed(mTelEditText, textView);
    }

    @Override
    public void emailBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_email_tv);
        setBackgroundRed(mEmailEditText, textView);
    }

    @Override
    public void eduExpAdmissionError(int id) {
        L.i("eduExpAdmissionError/" + id);
        View view = mEduContainerLayout.getChildAt(id);
        mEduDivider = (TextView) view.findViewById(R.id.edu_exp_divider);
        mEduDivider.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPink));
    }

    @Override
    public void eduExpGraduationError(int id) {
        View view = mEduContainerLayout.getChildAt(id);
        mEduDivider = (TextView) view.findViewById(R.id.edu_exp_divider);
        mEduDivider.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPink));
    }

    @Override
    public void eduExpSchoolBlankError(int id) {
        View view = mEduContainerLayout.getChildAt(id);
        EditText editText = (EditText) view.findViewById(R.id.education_school_et);
        TextView textView = (TextView) view.findViewById(R.id.education_school_tv);
        setBackgroundRed(editText, textView);
    }

    @Override
    public void eduExpMajorBlankError(int id) {

    }

    @Override
    public void jobExpInaugurationBlankError(int id) {
        View view = mJobContainerLayout.getChildAt(id);
        mJobDivider = (TextView) view.findViewById(R.id.job_exp_divider);
        mJobDivider.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPink));
    }

    @Override
    public void jobExpDimissionBlankError(int id) {
        View view = mJobContainerLayout.getChildAt(id);
        mJobDivider = (TextView) view.findViewById(R.id.job_exp_divider);
        mJobDivider.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPink));
    }

    @Override
    public void jobExpCompanyBlankError(int id) {
        View view = mJobContainerLayout.getChildAt(id);
        EditText editText = (EditText) view.findViewById(R.id.job_exp_company_et);
        TextView textView = (TextView) view.findViewById(R.id.job_exp_company_tv);
        setBackgroundRed(editText, textView);
    }

    @Override
    public void jobExpPositionBlankError(int id) {
        View view = mJobContainerLayout.getChildAt(id);
        EditText editText = (EditText) view.findViewById(R.id.job_exp_position_et);
        TextView textView = (TextView) view.findViewById(R.id.job_exp_position_tv);
        setBackgroundRed(editText, textView);
    }

    @Override
    public void expectJobPositionBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_expect_job_positon_tv);
        setBackgroundRed(mExpectJobEditText, textView);
    }

    @Override
    public void expectJobSalaryBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_expect_job_salary_tv);
        setBackgroundRed(mExpectSalaryEditText, textView);
    }

    @Override
    public void expectJobLocationBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_expect_job_location_tv);
        setBackgroundRed(mExpectLocationEditText, textView);
    }

    @Override
    public void close() {
        this.finish();
    }

    @Override
    public void save() {
        mPresenter.basedInformationCheck(MyApplication.getmPersonBean().getHead(),
                mNameEditText.getText().toString(), mSexSpinner.getSelectedItem().toString(),
                mStatusEditText.getText().toString(), mCompanyEditText.getText().toString(),
                mPositionEditText.getText().toString(), mCityEditText.getText().toString(), mTypeSpinner.getSelectedItem().toString(),
                mLevelSpinner.getSelectedItem().toString(), mCertificationSpinner.getSelectedItem().toString(),
                mTelEditText.getText().toString(), mEmailEditText.getText().toString(), mExpectJobEditText.getText().toString(),
                mExpectSalaryEditText.getText().toString(), mExpectLocationEditText.getText().toString());
        List<EducationExpBean> educationExpBeanList = new ArrayList<>();
        List<JobExpBean> jobExpBeanList = new ArrayList<>();
        for (int i = 0; i < mEduContainerLayout.getChildCount(); i++) {
            View view = mEduContainerLayout.getChildAt(i);
            TextView mAdmissionTextView = (TextView) view.findViewById(R.id.education_exp_admission_tv);
            TextView mGraduationTextView = (TextView) view.findViewById(R.id.education_exp_graduation_tv);
            EditText mSchoolEditText = (EditText) view.findViewById(R.id.education_school_et);
            EditText mMajorEditText = (EditText) view.findViewById(R.id.education_major_et);
            EducationExpBean educationExpBean = new EducationExpBean();
            educationExpBean.setAdmissionDate(mAdmissionTextView.getText().toString());
            educationExpBean.setGraduationDate(mGraduationTextView.getText().toString());
            educationExpBean.setSchool(mSchoolEditText.getText().toString());
            educationExpBean.setMajor(mMajorEditText.getText().toString());
            educationExpBeanList.add(educationExpBean);
        }
        for (int i = 0; i < mJobContainerLayout.getChildCount(); i++) {
            View view = mJobContainerLayout.getChildAt(i);
            TextView mInaugurationTextView = (TextView) view.findViewById(R.id.job_exp_inauguration_tv);
            TextView mDimissionTextView = (TextView) view.findViewById(R.id.job_exp_dimission_tv);
            EditText mCompanyEditText = (EditText) view.findViewById(R.id.job_exp_company_et);
            EditText mPositionEditText = (EditText) view.findViewById(R.id.job_exp_position_et);
            JobExpBean jobExpBean = new JobExpBean();
            jobExpBean.setInaugurationDate(mInaugurationTextView.getText().toString());
            jobExpBean.setDimissionDate(mDimissionTextView.getText().toString());
            jobExpBean.setCompany(mCompanyEditText.getText().toString());
            jobExpBean.setPosition(mPositionEditText.getText().toString());
            jobExpBeanList.add(jobExpBean);
        }
        mPresenter.educationExpCheck(educationExpBeanList);
        mPresenter.jobExpCheck(jobExpBeanList);
    }

    private void setSelection(Spinner mSpinner, String[] array, String content) {
        for (int i = 0; i < array.length; i++) {
            String str = array[i];
            if (content.equals(str)) {
                mSpinner.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void load(TextCVBean textCVBean) {
        ImageLoadUtils.display(this, mUserHeadImageView, textCVBean.getHead(), 0);
        mNameEditText.setText(textCVBean.getName());
        String[] sex = getResources().getStringArray(R.array.sex);
        setSelection(mSexSpinner, sex, textCVBean.getSex());
//        mQualificationEditText.setText(textCVBean.getQualification());
        mCityEditText.setText(textCVBean.getCity());
        String[] type = getResources().getStringArray(R.array.type);
        setSelection(mTypeSpinner, type, textCVBean.getDisabilityType());
        String[] level = getResources().getStringArray(R.array.level);
        setSelection(mLevelSpinner, level, textCVBean.getDisabilityLevel());
        String[] ifHave = getResources().getStringArray(R.array.ifhave);
        setSelection(mCertificationSpinner, ifHave, textCVBean.isHaveDisabilityCard());
        mTelEditText.setText(textCVBean.getTelephone());
        mStatusEditText.setText(textCVBean.getStatus());
        mEmailEditText.setText(textCVBean.getEmail());
        mExpectJobEditText.setText(textCVBean.getExpectPosition());
        mCompanyEditText.setText(textCVBean.getCompany());
        mPositionEditText.setText(textCVBean.getPosition());
        mExpectSalaryEditText.setText(textCVBean.getExpectSalary());
        mExpectLocationEditText.setText(textCVBean.getExpectLocation());
        for (int i = 0; i < textCVBean.getEducationExpBeanList().size(); i++) {
            if (i != 0) {
                View view = addEduView(mEduContainerLayout);
                ImageButton mEduDeleteImageButton = (ImageButton) view.findViewById(R.id.delete_exp_ib);
                mEduDeleteImageButton.setTag(EDU_ADMISSION + ";" + (mEduContainerLayout.getChildCount() - 1));
                mEduDeleteImageButton.setOnClickListener(this);
            }
            View view = mEduContainerLayout.getChildAt(i);
            EducationExpBean educationExpBean = textCVBean.getEducationExpBeanList().get(i);
            initEduView(view, educationExpBean);
        }
        for (int i = 0; i < textCVBean.getJobExpBeanList().size(); i++) {
            if (i != 0) {
                View view = addJobView(mJobContainerLayout);
                ImageButton mJobDeleteImageButton = (ImageButton) view.findViewById(R.id.delete_exp_ib);
                mJobDeleteImageButton.setTag(JOB_INAUGURATION + ";" + (mJobContainerLayout.getChildCount() - 1));
                mJobDeleteImageButton.setOnClickListener(this);
            }
            View view = mJobContainerLayout.getChildAt(i);
            JobExpBean jobExpBean = textCVBean.getJobExpBeanList().get(i);
            initJobView(view, jobExpBean);
        }
    }

    private void setBackgroundRed(final EditText editText, final TextView textView) {
        if (editText == null) {
            L.i("arguments error!");
        } else {
//            editText.setBackground(ContextCompat.getDrawable(this, R.drawable.text_cv_et_red_bg));
            editText.setBackgroundResource(R.drawable.text_cv_et_red_bg);
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorPink));
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.
                    getDrawable(this, R.drawable.text_cv_vertical_red_line), null);
            if (textView.getText().toString().length() > 2) {
                editText.setPadding(Util.dip2px(editText.getContext(), 72), 0, 0, Util.dip2px(editText.getContext(), 8));
            } else {
                L.i("<=2");
                editText.setPadding(Util.dip2px(editText.getContext(), 40), 0, 0, Util.dip2px(editText.getContext(), 8));
            }
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    editText.setBackgroundResource(R.drawable.text_cv_et_bg);
                    textView.setTextColor(ContextCompat.getColor(v.getContext(), R.color.colorBlack));
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.
                            getDrawable(v.getContext(), R.drawable.text_cv_vertical_gray_line), null);
                    if (textView.getText().toString().length() > 2) {
                        editText.setPadding(Util.dip2px(v.getContext(), 72), 0, 0, Util.dip2px(v.getContext(), 8));
                    } else {
                        editText.setPadding(Util.dip2px(v.getContext(), 40), 0, 0, Util.dip2px(v.getContext(), 8));
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_exp_ib:
                String position = (String) v.getTag();
                if (position.startsWith(JOB_INAUGURATION)) {
                    mJobContainerLayout.removeViewAt(Integer.valueOf(position.split(";")[1]));
                } else {
                    mEduContainerLayout.removeViewAt(Integer.valueOf(position.split(";")[1]));
                }
                break;
        }
    }
}
