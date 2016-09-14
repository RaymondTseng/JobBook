package com.example.jobbook.main.presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.example.jobbook.bean.PersonBean;

/**
 * Created by 椰树 on 2016/9/8.
 */
public interface ContainerPresenter {
    PersonBean loadPersonBean(Context context);

    void savePersonBean(Context context, PersonBean personBean);

    void update(FragmentManager fm, int mShowFragment);
}