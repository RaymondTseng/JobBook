package com.example.jobbook.cv.model;

import com.example.jobbook.bean.EducationExpBean;
import com.example.jobbook.bean.JobExpBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/9/4.
 */
public interface TextCVModel {

    void save(String head, String name, String sex, String qualification, String location,
              String type, String level, boolean haveCertification, String tel, String email,
              String expectJob, String expectSalary, String expectLocation,
              List<EducationExpBean> educationExpBeanList, List<JobExpBean> jobExpBeanList,
              TextCVModelImpl.OnBasedInformationFinishedListener onBasedInformationFinishedListener,
              TextCVModelImpl.OnEducationExpFinishedListener onEducationExpFinishedListener,
              TextCVModelImpl.OnJobExpFinishedListener onJobExpFinishedListener);
}
