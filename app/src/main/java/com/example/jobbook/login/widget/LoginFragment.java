package com.example.jobbook.login.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.login.view.LoginView;
import com.example.jobbook.main.widget.MainActivity;


/**
 * Created by 椰树 on 2016/6/2.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, LoginView{

    private Button mLoginButton;
    private TextView mRegisterTextView;
    private ILoginChanged mILoginChanged;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLoginButton = (Button) view.findViewById(R.id.login_login_bt);
        mLoginButton.setOnClickListener(this);
        mRegisterTextView = (TextView) view.findViewById(R.id.login_register_tv);
        mRegisterTextView.setOnClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try {
            mILoginChanged = (ILoginChanged) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement OnGridViewSelectedListener");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_bt:
                switch2Person();
                break;

            case R.id.login_register_tv:
                switch2Register();
                break;
        }
    }

    public void onRegisterClick(View view) {
        switch2Register();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setUserError() {

    }

    @Override
    public void setPwdError() {

    }

    @Override
    public void switch2Person() {
        mILoginChanged.switchLogin2Person();
    }

    @Override
    public void switch2Register() {
        mILoginChanged.switchLogin2Register();
    }

    public interface ILoginChanged {
        void switchLogin2Person();
        void switchLogin2Register();
    }
}
