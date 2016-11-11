package com.example.jobbook.main.model;

import com.example.jobbook.bean.PersonBean;

/**
 * Created by root on 16-10-25.
 */

public interface MainModel {

    void loginCheck(PersonBean personBean, MainModelImpl.OnLoginCheckListener listener);
}
