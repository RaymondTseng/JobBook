package com.example.jobbook.main.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.commons.Constants;
import com.example.jobbook.login.widget.LoginFragment;
import com.example.jobbook.main.presenter.ContainerPresenterImpl;
import com.example.jobbook.main.view.ContainerView;
import com.example.jobbook.person.widget.PersonFragment;
import com.example.jobbook.register.widget.RegisterFragment;

/**
 * Created by 椰树 on 2016/9/8.
 */
public class ContainerFragment extends Fragment implements ContainerView, LoginFragment.ILoginChanged,
        RegisterFragment.IRegisterChanged, PersonFragment.IPersonChanged{
    private FrameLayout mFragmentContainer;
    private ContainerPresenterImpl mPresenter;
    private int fragmentContainerId;


    @Override
    public void update(int mShowFragment) {
        mPresenter.update(getChildFragmentManager(), mShowFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, null);
        initViews(view);
        initEvents();
        Log.i("container", "createview");
        return view;
    }

    private void initViews(View view){
        mFragmentContainer = (FrameLayout) view.findViewById(R.id.container_fragment_container);
    }

    private void initEvents(){
        mPresenter = new ContainerPresenterImpl(this);
        fragmentContainerId = R.id.container_fragment_container;
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment oldFragment = fm.findFragmentById(fragmentContainerId);
        if(oldFragment != null){
            Log.i("old", oldFragment.toString());
            ft.remove(oldFragment);
        }
        mPresenter.loadPersonBean(getActivity());
        if(MyApplication.getmLoginStatus() == 0){
            mPresenter.setmShowFragment(Constants.LOGIN);
            ft.replace(fragmentContainerId, new LoginFragment());
        }else{
            mPresenter.setmShowFragment(Constants.PERSON);
            ft.replace(fragmentContainerId, new PersonFragment());
        }
        ft.commit();
    }

    @Override
    public void switchLogin2Person(PersonBean personBean) {
        mPresenter.savePersonBean(getActivity(), personBean);
        update(Constants.PERSON);
    }

    @Override
    public void switchLogin2Register() {
        update(Constants.REGISTER);
    }

    @Override
    public void switchRegister2Person(PersonBean personBean) {
        mPresenter.savePersonBean(getActivity(), personBean);
        update(Constants.PERSON);
    }

    @Override
    public void switchRegister2Login() {
        update(Constants.LOGIN);
    }

    @Override
    public void switchPerson2Login() {
        MyApplication.setmNoLoginStatus();
        update(Constants.LOGIN);
    }
}
