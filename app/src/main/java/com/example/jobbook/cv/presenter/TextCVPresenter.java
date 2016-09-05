package com.example.jobbook.cv.presenter;

import com.example.jobbook.bean.EducationExpBean;
import com.example.jobbook.bean.JobExpBean;

/**
 * Created by 椰树 on 2016/9/4.
 */
public interface TextCVPresenter {
    void basedInformationCheck(String head, String name, String sex, String qualification, String location,
                  String type, String level, boolean haveCertification, String tel, String email);

    void educationExpCheck(EducationExpBean educationExpBean);

    void jobExpCheck(JobExpBean jobExpBean);

}
