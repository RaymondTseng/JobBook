package com.example.jobbook.cv.model;

import android.text.TextUtils;

import com.example.jobbook.bean.EducationExpBean;
import com.example.jobbook.bean.JobExpBean;
import com.example.jobbook.bean.TextCVBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by 椰树 on 2016/9/4.
 */
public class TextCVModelImpl implements TextCVModel {


    @Override
    public void save(String head, String name, String sex, String qualification, String location,
                     String type, String level, boolean haveCertification, String tel, String email,
                     String expectJob, String expectSalary, String expectLocation,
                     List<EducationExpBean> educationExpBeanList, List<JobExpBean> jobExpBeanList,
                     OnBasedInformationFinishedListener onBasedInformationFinishedListener,
                     OnEducationExpFinishedListener onEducationExpFinishedListener,
                     OnJobExpFinishedListener onJobExpFinishedListener) {
        if(TextUtils.isEmpty(head)){
            onBasedInformationFinishedListener.onHeadBlankError();
        }else if(TextUtils.isEmpty(name)){
            onBasedInformationFinishedListener.onNameBlankError();
        }else if(TextUtils.isEmpty(sex)){
            onBasedInformationFinishedListener.onSexBlankError();
        }else if(TextUtils.isEmpty(qualification)){
            onBasedInformationFinishedListener.onQualificationBlankError();
        }else if(TextUtils.isEmpty(location)){
            onBasedInformationFinishedListener.onLocationBlankError();
        }else if(TextUtils.isEmpty(type)){
            onBasedInformationFinishedListener.onTypeBlankError();
        }else if(TextUtils.isEmpty(level)){
            onBasedInformationFinishedListener.onLevelBlankError();
        }else if(TextUtils.isEmpty(String.valueOf(haveCertification))){
            onBasedInformationFinishedListener.onCertificationBlankError();
        }else if(TextUtils.isEmpty(tel)){
            onBasedInformationFinishedListener.onTelBlankError();
        }else if(TextUtils.isEmpty(email)){
            onBasedInformationFinishedListener.onEmailBlankError();
        }else if(TextUtils.isEmpty(expectJob)){
            onBasedInformationFinishedListener.onExpectJobBlankError();
        }else if(TextUtils.isEmpty(expectSalary)){
            onBasedInformationFinishedListener.onExpectSalaryBlankError();
        }else if(TextUtils.isEmpty(expectLocation)){
            onBasedInformationFinishedListener.onExpectLocationBlankError();
        }else{
            for(EducationExpBean educationExpBean : educationExpBeanList){
                if(TextUtils.isEmpty(educationExpBean.getAdmissionDate())){
                    onEducationExpFinishedListener.onAdmissionTimeBlankError();
                    return;
                }else if(TextUtils.isEmpty(educationExpBean.getGraduationDate())){
                    onEducationExpFinishedListener.onGraduationTimeBlankError();
                    return;
                }else if(TextUtils.isEmpty(educationExpBean.getSchool())){
                    onEducationExpFinishedListener.onSchoolBlankError();
                    return;
                }
            }
            for(JobExpBean jobExpBean : jobExpBeanList){
                if(TextUtils.isEmpty(jobExpBean.getInaugurationDate())){
                    onJobExpFinishedListener.onInaugurationTimeBlankError();
                    return;
                }else if(TextUtils.isEmpty(jobExpBean.getDimissionDate())){
                    onJobExpFinishedListener.onDimissionTimeBlankError();
                    return;
                }else if(TextUtils.isEmpty(jobExpBean.getCompany())){
                    onJobExpFinishedListener.onCompanyBlankError();
                    return;
                }else if(TextUtils.isEmpty(jobExpBean.getPosition())){
                    onJobExpFinishedListener.onPositionBlankError();
                    return;
                }
            }
            TextCVBean textCVBean = new TextCVBean();
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
            textCVBean.setEducationExpBeanList(educationExpBeanList);
            textCVBean.setJobExpBeanList(jobExpBeanList);
            OkHttpUtils.postString().url(Urls.TEXT_CV_URL).content(new Gson().toJson(textCVBean)).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {

                }
            });
        }

    }

    public interface OnBasedInformationFinishedListener{
        void onSuccess(int id);
        void onFailure(String msg, Exception e, int id);
        void onHeadBlankError();
        void onNameBlankError();
        void onSexBlankError();
        void onQualificationBlankError();
        void onLocationBlankError();
        void onTypeBlankError();
        void onLevelBlankError();
        void onCertificationBlankError();
        void onTelBlankError();
        void onEmailBlankError();
        void onExpectJobBlankError();
        void onExpectSalaryBlankError();
        void onExpectLocationBlankError();
    }
    public interface OnEducationExpFinishedListener{
        void onSuccess(int id);
        void onFailure(String msg, Exception e, int id);
        void onAdmissionTimeBlankError();
        void onGraduationTimeBlankError();
        void onSchoolBlankError();
    }
    public interface OnJobExpFinishedListener{
        void onSuccess(int id);
        void onFailure(String msg, Exception e, int id);
        void onInaugurationTimeBlankError();
        void onDimissionTimeBlankError();
        void onCompanyBlankError();
        void onPositionBlankError();
    }
}
