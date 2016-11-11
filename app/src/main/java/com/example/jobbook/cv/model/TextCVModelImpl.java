package com.example.jobbook.cv.model;

import android.text.TextUtils;
import com.example.jobbook.util.L;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.EducationExpBean;
import com.example.jobbook.bean.JobExpBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.bean.TextCVBean;
import com.example.jobbook.commons.Constants;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by 椰树 on 2016/9/4.
 */
public class TextCVModelImpl implements TextCVModel {


    @Override
    public void save(TextCVBean textCVBean, final OnBasedInformationFinishedListener onBasedInformationFinishedListener,
                     final OnEducationExpFinishedListener onEducationExpFinishedListener,
                     final OnJobExpFinishedListener onJobExpFinishedListener) {
        if(TextUtils.isEmpty(textCVBean.getHead())){
            onBasedInformationFinishedListener.onHeadBlankError();
        }else if(TextUtils.isEmpty(textCVBean.getName())){
            onBasedInformationFinishedListener.onNameBlankError();
        }else if(TextUtils.isEmpty(textCVBean.getSex())){
            onBasedInformationFinishedListener.onSexBlankError();
        }else if(TextUtils.isEmpty(textCVBean.getQualification())){
            onBasedInformationFinishedListener.onQualificationBlankError();
        }else if(TextUtils.isEmpty(textCVBean.getCity())){
            onBasedInformationFinishedListener.onLocationBlankError();
        }else if(TextUtils.isEmpty(textCVBean.getDisabilityType())){
            onBasedInformationFinishedListener.onTypeBlankError();
        }else if(TextUtils.isEmpty(textCVBean.getDisabilityLevel())){
            onBasedInformationFinishedListener.onLevelBlankError();
        }else if(TextUtils.isEmpty(textCVBean.isHaveDisabilityCard())){
            onBasedInformationFinishedListener.onCertificationBlankError();
        }else if(TextUtils.isEmpty(textCVBean.getTelephone())){
            onBasedInformationFinishedListener.onTelBlankError();
        }else if(TextUtils.isEmpty(textCVBean.getEmail())){
            onBasedInformationFinishedListener.onEmailBlankError();
        }else if(TextUtils.isEmpty(textCVBean.getExpectPosition())){
            onBasedInformationFinishedListener.onExpectJobBlankError();
        }else if(TextUtils.isEmpty(textCVBean.getExpectSalary())){
            onBasedInformationFinishedListener.onExpectSalaryBlankError();
        }else if(TextUtils.isEmpty(textCVBean.getExpectLocation())){
            onBasedInformationFinishedListener.onExpectLocationBlankError();
        }else{
            for(int i = 0; i < textCVBean.getEducationExpBeanList().size(); i++){
                EducationExpBean educationExpBean = textCVBean.getEducationExpBeanList().get(i);
                if(!ifNumber(educationExpBean.getAdmissionDate())){
                    onEducationExpFinishedListener.onAdmissionTimeBlankError(i);
                    return;
                }else if(!ifNumber(educationExpBean.getGraduationDate())){
                    onEducationExpFinishedListener.onGraduationTimeBlankError(i);
                    return;
                }else if(TextUtils.isEmpty(educationExpBean.getSchool())){
                    onEducationExpFinishedListener.onSchoolBlankError(i);
                    return;
                }
            }
            for(int i = 0; i < textCVBean.getJobExpBeanList().size(); i++){
                JobExpBean jobExpBean = textCVBean.getJobExpBeanList().get(i);
                if(!ifNumber(jobExpBean.getInaugurationDate())){
                    onJobExpFinishedListener.onInaugurationTimeBlankError(i);
                    return;
                }else if(!ifNumber(jobExpBean.getDimissionDate())){
                    onJobExpFinishedListener.onDimissionTimeBlankError(i);
                    return;
                }else if(TextUtils.isEmpty(jobExpBean.getCompany())){
                    onJobExpFinishedListener.onCompanyBlankError(i);
                    return;
                }else if(TextUtils.isEmpty(jobExpBean.getPosition())){
                    onJobExpFinishedListener.onPositionBlankError(i);
                    return;
                }
            }
            L.i("loadCV", textCVBean.getName());
            OkHttpUtils.postString().url(Urls.POST_TEXT_CV_URL + MyApplication.getAccount())
                    .content(new Gson().toJson(textCVBean)).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    L.i("response", e.getMessage());
                    onBasedInformationFinishedListener.onFailure("network", e, id);
                }

                @Override
                public void onResponse(String response, int id) {
                    L.i("TextCV", response);
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if(resultBean.getStatus().equals("true")){
                        onBasedInformationFinishedListener.onSuccess();
                        onEducationExpFinishedListener.onSuccess();
                        onJobExpFinishedListener.onSuccess();
                    } else {
                        onBasedInformationFinishedListener.onFailure(resultBean.getResponse(), null, id);
                    }
                }
            });
        }

    }

    @Override
    public void load(final OnLoadTextCVListener listener) {
        OkHttpUtils.get().url(Urls.LOAD_TEXT_CV_URL + MyApplication.getAccount())
                .addParams("","").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onFailure("network", e, id);
            }

            @Override
            public void onResponse(String response, int id) {
                L.i("loadcv", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if(resultBean.getStatus().equals("true")){
                    TextCVBean textCVBean = new Gson().fromJson(resultBean.getResponse(), TextCVBean.class);
                    listener.onSuccess(textCVBean);
                }else{
                    listener.onFailure(resultBean.getResponse(), new Exception(), id);
                }
            }
        });
    }

    private boolean ifNumber(String date){
        for(String number : Constants.numbers){
            if(date.startsWith(number)){
                return true;
            }
        }
        return false;
    }

    public interface OnBasedInformationFinishedListener{
        void onSuccess();
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
        void onSuccess();
        void onFailure(String msg, Exception e, int id);
        void onAdmissionTimeBlankError(int id);
        void onGraduationTimeBlankError(int id);
        void onSchoolBlankError(int id);
    }
    public interface OnJobExpFinishedListener{
        void onSuccess();
        void onFailure(String msg, Exception e, int id);
        void onInaugurationTimeBlankError(int id);
        void onDimissionTimeBlankError(int id);
        void onCompanyBlankError(int id);
        void onPositionBlankError(int id);
    }

    public interface OnLoadTextCVListener{
        void onSuccess(TextCVBean textCVBean);
        void onFailure(String msg, Exception e, int id);
    }
}
