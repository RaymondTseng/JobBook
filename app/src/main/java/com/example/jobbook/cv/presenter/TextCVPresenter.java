package com.example.jobbook.cv.presenter;

import com.example.jobbook.model.bean.EducationExpBean;
import com.example.jobbook.model.bean.JobExpBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/9/4.
 */
public interface TextCVPresenter {
    void basedInformationCheck(String head, String name, String sex, String status, String company, String position,
                               String location, String type, String level, String haveCertification,
                               String tel, String email, String expectJob,
                               String expectSalary, String expectLocation);

    void educationExpCheck(List<EducationExpBean> educationExpBeanList);

    void jobExpCheck(List<JobExpBean> jobExpBeanList);

    void load();

}
