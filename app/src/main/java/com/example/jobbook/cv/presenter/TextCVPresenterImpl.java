package com.example.jobbook.cv.presenter;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jobbook.bean.EducationExpBean;
import com.example.jobbook.bean.JobExpBean;
import com.example.jobbook.bean.TextCVBean;
import com.example.jobbook.cv.model.TextCVModel;
import com.example.jobbook.cv.model.TextCVModelImpl;
import com.example.jobbook.cv.view.TextCVView;

import java.util.List;

/**
 * Created by 椰树 on 2016/9/4.
 */
public class TextCVPresenterImpl implements TextCVPresenter, TextCVModelImpl.OnBasedInformationFinishedListener,
    TextCVModelImpl.OnEducationExpFinishedListener, TextCVModelImpl.OnJobExpFinishedListener,
    TextCVModelImpl.OnLoadTextCVListener{
    private TextCVModel mTextCVModel;
    private TextCVView mTextCVView;
    private TextCVBean textCVBean = new TextCVBean();
    private int TAG = 0;

    public TextCVPresenterImpl(TextCVView view){
        mTextCVView = view;
        mTextCVModel = new TextCVModelImpl();
    }

    @Override
    public void basedInformationCheck(String head, String name, String sex, String qualification, String location,
                String type, String level, String haveCertification, String tel, String email, String expectJob,
                                      String expectSalary, String expectLocation) {
        textCVBean.setHead(head);
        textCVBean.setName(name);
        textCVBean.setSex(sex);
        textCVBean.setQualification(qualification);
        textCVBean.setCity(location);
        textCVBean.setDisabilityType(type);
        textCVBean.setDisabilityLevel(level);
        textCVBean.setHaveDisabilityCard(haveCertification);
        textCVBean.setTelephone(tel);
        textCVBean.setEmail(email);
        textCVBean.setExpectPosition(expectJob);
        textCVBean.setExpectSalary(expectSalary);
        textCVBean.setExpectLocation(expectLocation);
        TAG++;
        toModel();
    }

    private void refresh(){
        textCVBean = null;
        TAG = 0;
        textCVBean = new TextCVBean();
    }
    @Override
    public void educationExpCheck(List<EducationExpBean> educationExpBeanList) {
        textCVBean.setEducationExpBeanList(educationExpBeanList);
        TAG++;
        toModel();
    }

    @Override
    public void jobExpCheck(List<JobExpBean> jobExpBeanList) {
        textCVBean.setJobExpBeanList(jobExpBeanList);
        TAG++;
        toModel();
    }

    @Override
    public void load() {
        mTextCVModel.load(this);
    }

    private void toModel(){
        if(TAG == 3){
            mTextCVView.showProgress();
            mTextCVModel.save(textCVBean, this, this, this);
        }
    }


    @Override
    public void onSuccess() {
        mTextCVView.hideProgress();
        mTextCVView.success();
        refresh();
    }

    @Override
    public void onSuccess(TextCVBean textCVBean) {
        mTextCVView.hideProgress();
        mTextCVView.load(textCVBean);
    }

    @Override
    public void onFailure(String msg, Exception e, int id) {
        if(msg.equals("network")){
            mTextCVView.networkError();
        }else if(msg.equals("null")){

        }

        refresh();
    }

    @Override
    public void onInaugurationTimeBlankError(int id) {
        mTextCVView.jobExpInaugurationBlankError(id);
        refresh();
    }

    @Override
    public void onDimissionTimeBlankError(int id) {
        mTextCVView.jobExpDimissionBlankError(id);
        refresh();
    }

    @Override
    public void onCompanyBlankError(int id) {
        mTextCVView.jobExpCompanyBlankError(id);
        refresh();
    }

    @Override
    public void onPositionBlankError(int id) {
        mTextCVView.jobExpPositionBlankError(id);
        refresh();
    }

    @Override
    public void onAdmissionTimeBlankError(int id) {
        mTextCVView.eduExpAdmissionError(id);
        refresh();
    }

    @Override
    public void onGraduationTimeBlankError(int id) {
        mTextCVView.eduExpGraduationError(id);
        refresh();
    }

    @Override
    public void onSchoolBlankError(int id) {
        mTextCVView.eduExpSchoolBlankError(id);
        refresh();
    }


    @Override
    public void onHeadBlankError() {
        mTextCVView.headBlankError();
        refresh();
    }

    @Override
    public void onNameBlankError() {
        mTextCVView.nameBlankError();
        refresh();
    }

    @Override
    public void onSexBlankError() {

    }

    @Override
    public void onQualificationBlankError() {
        mTextCVView.qualificationBlankError();
        refresh();
    }

    @Override
    public void onLocationBlankError() {
        mTextCVView.locationBlankError();
        refresh();
    }

    @Override
    public void onTypeBlankError() {
        mTextCVView.typeBlankError();
        refresh();
    }

    @Override
    public void onLevelBlankError() {
        mTextCVView.levelBlankError();
        refresh();
    }

    @Override
    public void onCertificationBlankError() {

    }

    @Override
    public void onTelBlankError() {
        mTextCVView.telBlankError();
        refresh();
    }

    @Override
    public void onEmailBlankError() {
        mTextCVView.emailBlankError();
        refresh();
    }

    @Override
    public void onExpectJobBlankError() {
        mTextCVView.expectJobPositionBlankError();
        refresh();
    }

    @Override
    public void onExpectSalaryBlankError() {
        mTextCVView.expectJobSalaryBlankError();
        refresh();
    }

    @Override
    public void onExpectLocationBlankError() {
        mTextCVView.expectJobLocationBlankError();
        refresh();
    }

}
