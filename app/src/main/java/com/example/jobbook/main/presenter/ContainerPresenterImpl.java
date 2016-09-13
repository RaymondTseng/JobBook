package com.example.jobbook.main.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.main.view.ContainerView;
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
//        SharedPreferences share = context.getSharedPreferences("user", context.MODE_PRIVATE);
//        PersonBean personBean = null;
//        if(share != null){
//            Util.loadPersonBean(share, personBean);
//            MyApplication.setmPersonBean(personBean);
//        }
//        return personBean;
        return null;
    }

    @Override
    public void savePersonBean(Context context, PersonBean personBean) {
//        SharedPreferences share = context.getSharedPreferences("user", context.MODE_PRIVATE);
//        Util.savePersonBean(share, personBean);
//        MyApplication.setmPersonBean(personBean);
    }

    @Override
    public void update(FragmentManager fm, int mShowFragment) {
//        Log.i("container", "update:" + mShowFragment);
//        FragmentTransaction ft = fm.beginTransaction();
//        int fragmentContainerId = R.id.container_fragment_container;
//        if(this.mShowFragment != mShowFragment){
//            this.mShowFragment = mShowFragment;
//            if(mShowFragment == Constants.LOGIN){
//                ft.replace(fragmentContainerId, new LoginActivity());
//            }else if(mShowFragment == Constants.REGISTER){
//                ft.replace(fragmentContainerId, new RegisterActivity());
//            }else{
//                ft.replace(fragmentContainerId, new PersonFragment());
//            }
//            ft.commit();
//
//        }
    }

    public void setmShowFragment(int mShowFragment){
        this.mShowFragment = mShowFragment;
    }
}
