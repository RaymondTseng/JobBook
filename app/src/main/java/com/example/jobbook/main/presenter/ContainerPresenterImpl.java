package com.example.jobbook.main.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.main.view.ContainerView;
import com.example.jobbook.util.Util;

/**
 * Created by 椰树 on 2016/9/8.
 */
public class ContainerPresenterImpl implements ContainerPresenter {
    private ContainerView mView;

    public ContainerPresenterImpl(ContainerView view){
        mView = view;
    }
    @Override
    public PersonBean loadPersonBean(Context context) {
        SharedPreferences share = context.getSharedPreferences("user", context.MODE_PRIVATE);
        PersonBean personBean = null;
        if(share != null){
            Util.loadPersonBean(share, personBean);
            MyApplication.setmPersonBean(personBean);
        }
        return personBean;
    }

    @Override
    public void savePersonBean(Context context, PersonBean personBean) {
        SharedPreferences share = context.getSharedPreferences("user", context.MODE_PRIVATE);
        Util.savePersonBean(share, personBean);
        MyApplication.setmPersonBean(personBean);
    }
}
