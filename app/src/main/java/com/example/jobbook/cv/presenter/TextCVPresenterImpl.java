package com.example.jobbook.cv.presenter;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jobbook.bean.EducationExpBean;
import com.example.jobbook.bean.JobExpBean;
import com.example.jobbook.cv.model.TextCVModel;
import com.example.jobbook.cv.model.TextCVModelImpl;
import com.example.jobbook.cv.view.TextCVView;

/**
 * Created by 椰树 on 2016/9/4.
 */
public class TextCVPresenterImpl implements TextCVPresenter, TextCVModelImpl.OnBasedInformationFinishedListener,
    TextCVModelImpl.OnEducationExpFinishedListener, TextCVModelImpl.OnJobExpFinishedListener{
    private TextCVModel mTextCVModel;
    private TextCVView mTextCVView;

    public TextCVPresenterImpl(TextCVView view){
        mTextCVView = view;
        mTextCVModel = new TextCVModelImpl();
    }

    @Override
    public void basedInformationCheck(String head, String name, String sex, String qualification, String location,
                String type, String level, boolean haveCertification, String tel, String email) {

    }

    @Override
    public void educationExpCheck(EducationExpBean educationExpBean) {

    }

    @Override
    public void jobExpCheck(JobExpBean jobExpBean) {

    }

    @Override
    public void onSuccess(int id) {

    }

    @Override
    public void onFailure(String msg, Exception e, int id) {

    }

    @Override
    public void onInaugurationTimeBlankError() {

    }

    @Override
    public void onDimissionTimeBlankError() {

    }

    @Override
    public void onCompanyBlankError() {

    }

    @Override
    public void onPositionBlankError() {

    }

    @Override
    public void onAdmissionTimeBlankError() {

    }

    @Override
    public void onGraduationTimeBlankError() {

    }

    @Override
    public void onSchoolBlankError() {

    }

    @Override
    public void onHeadBlankError() {

    }

    @Override
    public void onNameBlankError() {

    }

    @Override
    public void onSexBlankError() {

    }

    @Override
    public void onQualificationBlankError() {

    }

    @Override
    public void onLocationBlankError() {

    }

    @Override
    public void onTypeBlankError() {

    }

    @Override
    public void onLevelBlankError() {

    }

    @Override
    public void onCertificationBlankError() {

    }

    @Override
    public void onTelBlankError() {

    }

    @Override
    public void onEmailBlankError() {

    }

    @Override
    public void onExpectJobBlankError() {

    }

    @Override
    public void onExpectSalaryBlankError() {

    }

    @Override
    public void onExpectLocationBlankError() {

    }

}
