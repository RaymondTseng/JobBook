package com.example.jobbook.cv.model;

import com.example.jobbook.bean.TextCVBean;

/**
 * Created by 椰树 on 2016/9/4.
 */
public interface TextCVModel {

    void save(TextCVBean textCVBean, TextCVModelImpl.OnBasedInformationFinishedListener onBasedInformationFinishedListener,
              TextCVModelImpl.OnEducationExpFinishedListener onEducationExpFinishedListener,
              TextCVModelImpl.OnJobExpFinishedListener onJobExpFinishedListener);

    void load(TextCVModelImpl.OnLoadTextCVListener listener);
}
