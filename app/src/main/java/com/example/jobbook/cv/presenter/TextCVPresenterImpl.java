package com.example.jobbook.cv.presenter;

import com.example.jobbook.bean.EducationExpBean;
import com.example.jobbook.bean.JobExpBean;
import com.example.jobbook.bean.PersonBean;
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

    private void refresh(){
        textCVBean = null;
        TAG = 0;
        textCVBean = new TextCVBean();
    }

    @Override
    public void basedInformationCheck(String head, String name, String sex, String status, String company, String position, String location, String type, String level, String haveCertification, String tel, String email, String expectJob, String expectSalary, String expectLocation) {
        textCVBean.setHead(head);
        textCVBean.setName(name);
        textCVBean.setSex(sex);
        textCVBean.setStatus(status);
        textCVBean.setCompany(company);
        textCVBean.setPosition(position);
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
        mTextCVView.showProgress();
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
//        mTextCVView.hideProgress();
//        mTextCVView.success();
//        refresh();
    }

    @Override
    public void onSuccess(TextCVBean textCVBean) {
        mTextCVView.hideProgress();
        mTextCVView.load(textCVBean);
    }

    @Override
    public void onSuccess(PersonBean personBean) {
        mTextCVView.hideProgress();
        mTextCVView.success(personBean);
        refresh();
    }

    @Override
    public void onFailure(String msg, Exception e, int id) {
        mTextCVView.hideProgress();
        if(msg.equals("network")){
            mTextCVView.networkError();
        }else if(msg.equals("null")){

        }

        refresh();
    }

    @Override
    public void onInaugurationTimeBlankError(int id) {
        mTextCVView.hideProgress();
        mTextCVView.jobExpInaugurationBlankError(id);
        refresh();
    }

    @Override
    public void onDimissionTimeBlankError(int id) {
        mTextCVView.hideProgress();
        mTextCVView.jobExpDimissionBlankError(id);
        refresh();
    }

    @Override
    public void onJobCompanyBlankError(int id) {
        mTextCVView.hideProgress();
        mTextCVView.jobExpCompanyBlankError(id);
        refresh();
    }

    @Override
    public void onJobPositionBlankError(int id) {
        mTextCVView.hideProgress();
        mTextCVView.jobExpPositionBlankError(id);
        refresh();
    }

    @Override
    public void onAdmissionTimeBlankError(int id) {
        mTextCVView.hideProgress();
        mTextCVView.eduExpAdmissionError(id);
        refresh();
    }

    @Override
    public void onGraduationTimeBlankError(int id) {
        mTextCVView.hideProgress();
        mTextCVView.eduExpGraduationError(id);
        refresh();
    }

    @Override
    public void onSchoolBlankError(int id) {
        mTextCVView.hideProgress();
        mTextCVView.eduExpSchoolBlankError(id);
        refresh();
    }


    @Override
    public void onHeadBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.headBlankError();
        refresh();
    }

    @Override
    public void onNameBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.nameBlankError();
        refresh();
    }

    @Override
    public void onSexBlankError() {

    }

    @Override
    public void onStatusBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.statusBlankError();
        refresh();
    }

    @Override
    public void onCompanyBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.companyBlankError();
        refresh();
    }

    @Override
    public void onPositionBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.positionBlankError();
        refresh();
    }


    @Override
    public void onLocationBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.locationBlankError();
        refresh();
    }

    @Override
    public void onTypeBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.typeBlankError();
        refresh();
    }

    @Override
    public void onLevelBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.levelBlankError();
        refresh();
    }

    @Override
    public void onCertificationBlankError() {

    }

    @Override
    public void onTelBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.telBlankError();
        refresh();
    }

    @Override
    public void onEmailBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.emailBlankError();
        refresh();
    }

    @Override
    public void onExpectJobBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.expectJobPositionBlankError();
        refresh();
    }

    @Override
    public void onExpectSalaryBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.expectJobSalaryBlankError();
        refresh();
    }

    @Override
    public void onExpectLocationBlankError() {
        mTextCVView.hideProgress();
        mTextCVView.expectJobLocationBlankError();
        refresh();
    }

}
