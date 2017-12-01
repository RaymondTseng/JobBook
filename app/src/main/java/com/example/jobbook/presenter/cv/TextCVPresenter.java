package com.example.jobbook.presenter.cv;

import android.text.TextUtils;

import com.example.jobbook.app.Constants;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.cv.TextCVContract;
import com.example.jobbook.model.bean.EducationExpBean;
import com.example.jobbook.model.bean.JobExpBean;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.bean.TextCVBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2017/12/1.
 */

public class TextCVPresenter extends RxPresenter<TextCVContract.View> implements TextCVContract.Presenter{
    private TextCVBean textCVBean = new TextCVBean();
    private int TAG = 0;
    
    public TextCVPresenter(TextCVContract.View view) {
        attach(view);
    }
    
    private void refresh() {
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
        String account = MyApplication.getAccount();
        if (TextUtils.isEmpty(account)) {
            return;
        }
        RetrofitService.loadCV(account)
                .subscribe(new BaseSubscriber<TextCVBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        refresh();
                    }

                    @Override
                    public void onNext(TextCVBean textCVBean) {
                        mView.load(textCVBean);
                    }
                });
    }

    private void toModel() {
        if (TAG == 3) {
            String account = MyApplication.getAccount();
            if (TextUtils.isEmpty(account)) {
                return;
            }
            if (!postCheckFirst(textCVBean) || !postCheckSecond(textCVBean)) {
                return;
            }
            RetrofitService.postCV(account, textCVBean)
                    .subscribe(new BaseSubscriber<PersonBean>() {
                        @Override
                        public IBaseView getBaseView() {
                            return mView;
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            refresh();
                        }

                        @Override
                        public void onNext(PersonBean personBean) {
                            mView.success(personBean);
                            refresh();
                        }
                    });
        }
    }

    private boolean postCheckFirst(TextCVBean textCVBean) {
        if (TextUtils.isEmpty(textCVBean.getHead())) {
            mView.hideProgress();
            mView.headBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getName())) {
            mView.hideProgress();
            mView.nameBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getSex())) {
//            onBasedInformationFinishedListener.onSexBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getStatus())) {
            mView.hideProgress();
            mView.statusBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getCompany())) {
            mView.hideProgress();
            mView.companyBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getPosition())) {
            mView.hideProgress();
            mView.positionBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getCity())) {
            mView.hideProgress();
            mView.locationBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getDisabilityType())) {
            mView.hideProgress();
            mView.typeBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getDisabilityLevel())) {
            mView.hideProgress();
            mView.levelBlankError();
        } else if (TextUtils.isEmpty(textCVBean.isHaveDisabilityCard())) {
//            onBasedInformationFinishedListener.onCertificationBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getTelephone())) {
            mView.hideProgress();
            mView.telBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getEmail())) {
            mView.hideProgress();
            mView.emailBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getExpectPosition())) {
            mView.hideProgress();
            mView.expectJobPositionBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getExpectSalary())) {
            mView.hideProgress();
            mView.expectJobSalaryBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getExpectLocation())) {
            mView.hideProgress();
            mView.expectJobLocationBlankError();
        } else {
            return true;
        }
        refresh();
        return false;
    }

    private boolean postCheckSecond(TextCVBean textCVBean) {
        for(int i = 0; i < textCVBean.getEducationExpBeanList().size(); i++){
            EducationExpBean educationExpBean = textCVBean.getEducationExpBeanList().get(i);
            if(!ifNumber(educationExpBean.getAdmissionDate())){
                mView.hideProgress();
                mView.eduExpAdmissionError(i);
                refresh();
                return false;
            }else if(!ifNumber(educationExpBean.getGraduationDate())){
                mView.hideProgress();
                mView.eduExpGraduationError(i);
                refresh();
                return false;
            }else if(TextUtils.isEmpty(educationExpBean.getSchool())){
                mView.hideProgress();
                mView.eduExpSchoolBlankError(i);
                refresh();
                return false;
            }
        }
        for(int i = 0; i < textCVBean.getJobExpBeanList().size(); i++){
            JobExpBean jobExpBean = textCVBean.getJobExpBeanList().get(i);
            if(!ifNumber(jobExpBean.getInaugurationDate())){
                mView.hideProgress();
                mView.jobExpInaugurationBlankError(i);
                refresh();
                return false;
            }else if(!ifNumber(jobExpBean.getDimissionDate())){
                mView.hideProgress();
                mView.jobExpDimissionBlankError(i);
                refresh();
                return false;
            }else if(TextUtils.isEmpty(jobExpBean.getCompany())){
                mView.hideProgress();
                mView.jobExpCompanyBlankError(i);
                refresh();
                return false;
            }else if(TextUtils.isEmpty(jobExpBean.getPosition())){
                mView.hideProgress();
                mView.jobExpPositionBlankError(i);
                refresh();
                return false;
            }
        }
        return true;
    }

    private boolean ifNumber(String date){
        for(String number : Constants.numbers){
            if(date.startsWith(number)){
                return true;
            }
        }
        return false;
    }
}
