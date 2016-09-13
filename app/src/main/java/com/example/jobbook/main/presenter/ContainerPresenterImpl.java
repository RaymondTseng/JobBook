package com.example.jobbook.main.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.commons.Constants;
import com.example.jobbook.login.widget.LoginFragment;
import com.example.jobbook.main.view.ContainerView;
import com.example.jobbook.person.widget.PersonFragment;
import com.example.jobbook.register.widget.RegisterFragment;
import com.example.jobbook.util.Util;

/**
 * Created by 椰树 on 2016/9/8.
 */
public class ContainerPresenterImpl implements ContainerPresenter {
    private ContainerView mView;
    private int mShowFragment;

    public ContainerPresenterImpl(ContainerView view){
        mShowFragment = 0;
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

    @Override
    public void update(FragmentManager fm, int mShowFragment) {
        Log.i("container", "update:" + mShowFragment);
        FragmentTransaction ft = fm.beginTransaction();
        int fragmentContainerId = R.id.container_fragment_container;
        if(this.mShowFragment != mShowFragment){
            this.mShowFragment = mShowFragment;
            if(mShowFragment == Constants.LOGIN){
                ft.replace(fragmentContainerId, new LoginFragment());
            }else if(mShowFragment == Constants.REGISTER){
                ft.replace(fragmentContainerId, new RegisterFragment());
            }else{
                ft.replace(fragmentContainerId, new PersonFragment());
            }
            ft.commit();

        }
    }

    public void setmShowFragment(int mShowFragment){
        this.mShowFragment = mShowFragment;
    }
}
