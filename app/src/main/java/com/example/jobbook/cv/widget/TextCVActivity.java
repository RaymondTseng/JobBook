package com.example.jobbook.cv.widget;

import com.example.jobbook.cv.view.TextCVView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 椰树 on 2016/8/25.
 */
public class TextCVActivity extends AppCompatActivity implements OnDateSetListener, View.OnClickListener, TextCVView{
    private static final String EDU_ADMISSION = "1";
    private static final String EDU_GRADUATION = "2";
    private static final String JOB_INAUGURATION = "3";
    private static final String JOB_DIMISSION = "4";
    private TimePickerDialog mJobExpInaugurationDialog;
    private TimePickerDialog mJobExpDimissionDialog;
    private TimePickerDialog mEduExpAdmissionDialog;
    private TimePickerDialog mEduExpGraduationDialog;
    private TextView mJobExpInaugurationTextView;
    private TextView mJobExpDimissionTextView;
    private TextView mEduExpAdmissionTextView;
    private TextView mEduExpGraduationTextView;
    private LinearLayout mEduContainerLayout;
    private ImageButton mAddEduExpImageButton;
    private ImageButton mAddJobExpImageButton;
    private ImageButton mCloseImageButton;
    private TextView mSaveTextView;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_cv);
        initView();
        initEvents();
    }
    private void initView(){
        mJobExpInaugurationTextView = (TextView) findViewById(R.id.job_exp_inauguration_tv);
        mJobExpDimissionTextView = (TextView) findViewById(R.id.job_exp_dimission_tv);
        mEduExpAdmissionTextView = (TextView) findViewById(R.id.education_exp_admission_tv);
        mEduExpGraduationTextView = (TextView) findViewById(R.id.education_exp_graduation_tv);
        mEduContainerLayout = (LinearLayout) findViewById(R.id.text_cv_edu_container);
        mAddEduExpImageButton = (ImageButton) findViewById(R.id.text_cv_add_edu_exp_ib);
        mAddJobExpImageButton = (ImageButton) findViewById(R.id.text_cv_add_job_exp_ib);
        mCloseImageButton = (ImageButton) findViewById(R.id.text_cv_close_ib);
        mSaveTextView = (TextView) findViewById(R.id.text_cv_save_tv);
    }
    private void initEvents(){
        mJobExpInaugurationTextView.setOnClickListener(this);
        mJobExpDimissionTextView.setOnClickListener(this);
        mEduExpAdmissionTextView.setOnClickListener(this);
        mEduExpGraduationTextView.setOnClickListener(this);
        mAddEduExpImageButton.setOnClickListener(this);
        mAddJobExpImageButton.setOnClickListener(this);
        mCloseImageButton.setOnClickListener(this);
        mSaveTextView.setOnClickListener(this);
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
        if(timePickerView.getTag().equals(JOB_DIMISSION)){
            mJobExpDimissionTextView.setText(text);
        }else if(timePickerView.getTag().equals(JOB_INAUGURATION)){
            mJobExpInaugurationTextView.setText(text);
        }else if(timePickerView.getTag().equals(EDU_ADMISSION)){
            mEduExpAdmissionTextView.setText(text);
        }else{
            mEduExpGraduationTextView.setText(text);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.job_exp_inauguration_tv:
                mJobExpInaugurationDialog.show(getSupportFragmentManager(), JOB_INAUGURATION);
                break;
            case R.id.job_exp_dimission_tv:
                mJobExpDimissionDialog.show(getSupportFragmentManager(), JOB_DIMISSION);
                break;
            case R.id.education_exp_admission_tv:
                mEduExpAdmissionDialog.show(getSupportFragmentManager(), EDU_ADMISSION);
                break;
            case R.id.education_exp_graduation_tv:
                mEduExpGraduationDialog.show(getSupportFragmentManager(), EDU_GRADUATION);
                break;
            case R.id.text_cv_close_ib:
                close();
                break;
            case R.id.text_cv_save_tv:
                break;
            case R.id.text_cv_add_edu_exp_ib:
                View eduView = View.inflate(this, R.layout.education_experiment_layout, null);
                LinearLayout.LayoutParams eduParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                mEduContainerLayout.addView(eduView, eduParams);
                break;
            case R.id.text_cv_add_job_exp_ib:
                View jobView = View.inflate(this, R.layout.job_experiment_layout, null);
                LinearLayout.LayoutParams jobParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                mEduContainerLayout.addView(jobView, jobParams);
                break;

        }
    }

    @Override
    public void success() {

    }

    @Override
    public void networkError() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void headBlankError() {

    }

    @Override
    public void nameBlankError() {

    }

    @Override
    public void qualificationBlankError() {

    }

    @Override
    public void locationBlankError() {

    }

    @Override
    public void levelBlankError() {

    }

    @Override
    public void typeBlankError() {

    }

    @Override
    public void telBlankError() {

    }

    @Override
    public void emailBlankError() {

    }

    @Override
    public void eduExpAdmissionError() {

    }

    @Override
    public void eduExpGraduationError() {

    }

    @Override
    public void eduExpSchoolBlankError() {

    }

    @Override
    public void eduExpMajorBlankError() {

    }

    @Override
    public void jobExpInaugurationBlankError() {

    }

    @Override
    public void jobExpDimissionBlankError() {

    }

    @Override
    public void jobExpCompanyBlankError() {

    }

    @Override
    public void jobExpPositionBlankError() {

    }

    @Override
    public void expectJobPositionBlankError() {

    }

    @Override
    public void expectJobSalaryBlankError() {

    }

    @Override
    public void expectJobLocationBlankError() {

    }

    @Override
    public void close() {
        this.finish();
    }

    @Override
    public void save() {

    }

    private void setBackgroundRed(EditText editText){
        if(editText == null){
            Log.i("TextCV", "arguments error!");
        }else{
            editText.setBackground(ContextCompat.getDrawable(this, R.drawable.text_cv_et_red_bg));
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.text_cv_et_bg));
                }
            });
        }
    }
}
