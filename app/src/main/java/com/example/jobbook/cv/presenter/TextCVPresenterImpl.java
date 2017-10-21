package com.example.jobbook.cv.presenter;

import android.text.TextUtils;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.EducationExpBean;
import com.example.jobbook.bean.JobExpBean;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.TextCVBean;
import com.example.jobbook.commons.Constants;
import com.example.jobbook.cv.view.TextCVView;
import com.example.jobbook.network.RetrofitService;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by 椰树 on 2016/9/4.
 */

public class TextCVPresenterImpl implements TextCVPresenter {
    private TextCVView mTextCVView;
    private TextCVBean textCVBean = new TextCVBean();
    private int TAG = 0;

    public TextCVPresenterImpl(TextCVView view) {
        mTextCVView = view;
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
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mTextCVView.showProgress();
                    }
                })
                .subscribe(new Subscriber<TextCVBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mTextCVView.hideProgress();
//                        if(throwable.getMessage().equals("network")){
                        mTextCVView.networkError();
//                        }
                        refresh();
                    }

                    @Override
                    public void onNext(TextCVBean textCVBean) {
                        mTextCVView.hideProgress();
                        mTextCVView.load(textCVBean);
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
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mTextCVView.showProgress();
                        }
                    })
                    .subscribe(new Subscriber<PersonBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable throwable) {
                            mTextCVView.hideProgress();
//                        if(throwable.getMessage().equals("network")){
                            mTextCVView.networkError();
//                        }
                            refresh();
                        }

                        @Override
                        public void onNext(PersonBean personBean) {
                            mTextCVView.hideProgress();
                            mTextCVView.success(personBean);
                            refresh();
                        }
                    });
        }
    }

    private boolean postCheckFirst(TextCVBean textCVBean) {
        if (TextUtils.isEmpty(textCVBean.getHead())) {
            mTextCVView.hideProgress();
            mTextCVView.headBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getName())) {
            mTextCVView.hideProgress();
            mTextCVView.nameBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getSex())) {
//            onBasedInformationFinishedListener.onSexBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getStatus())) {
            mTextCVView.hideProgress();
            mTextCVView.statusBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getCompany())) {
            mTextCVView.hideProgress();
            mTextCVView.companyBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getPosition())) {
            mTextCVView.hideProgress();
            mTextCVView.positionBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getCity())) {
            mTextCVView.hideProgress();
            mTextCVView.locationBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getDisabilityType())) {
            mTextCVView.hideProgress();
            mTextCVView.typeBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getDisabilityLevel())) {
            mTextCVView.hideProgress();
            mTextCVView.levelBlankError();
        } else if (TextUtils.isEmpty(textCVBean.isHaveDisabilityCard())) {
//            onBasedInformationFinishedListener.onCertificationBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getTelephone())) {
            mTextCVView.hideProgress();
            mTextCVView.telBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getEmail())) {
            mTextCVView.hideProgress();
            mTextCVView.emailBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getExpectPosition())) {
            mTextCVView.hideProgress();
            mTextCVView.expectJobPositionBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getExpectSalary())) {
            mTextCVView.hideProgress();
            mTextCVView.expectJobSalaryBlankError();
        } else if (TextUtils.isEmpty(textCVBean.getExpectLocation())) {
            mTextCVView.hideProgress();
            mTextCVView.expectJobLocationBlankError();
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
                mTextCVView.hideProgress();
                mTextCVView.eduExpAdmissionError(i);
                refresh();
                return false;
            }else if(!ifNumber(educationExpBean.getGraduationDate())){
                mTextCVView.hideProgress();
                mTextCVView.eduExpGraduationError(i);
                refresh();
                return false;
            }else if(TextUtils.isEmpty(educationExpBean.getSchool())){
                mTextCVView.hideProgress();
                mTextCVView.eduExpSchoolBlankError(i);
                refresh();
                return false;
            }
        }
        for(int i = 0; i < textCVBean.getJobExpBeanList().size(); i++){
            JobExpBean jobExpBean = textCVBean.getJobExpBeanList().get(i);
            if(!ifNumber(jobExpBean.getInaugurationDate())){
                mTextCVView.hideProgress();
                mTextCVView.jobExpInaugurationBlankError(i);
                refresh();
                return false;
            }else if(!ifNumber(jobExpBean.getDimissionDate())){
                mTextCVView.hideProgress();
                mTextCVView.jobExpDimissionBlankError(i);
                refresh();
                return false;
            }else if(TextUtils.isEmpty(jobExpBean.getCompany())){
                mTextCVView.hideProgress();
                mTextCVView.jobExpCompanyBlankError(i);
                refresh();
                return false;
            }else if(TextUtils.isEmpty(jobExpBean.getPosition())){
                mTextCVView.hideProgress();
                mTextCVView.jobExpPositionBlankError(i);
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
