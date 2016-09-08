package com.example.jobbook.login.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.login.presenter.LoginPresenter;
import com.example.jobbook.login.presenter.LoginPresenterImpl;
import com.example.jobbook.login.view.LoginView;


/**
 * Created by 椰树 on 2016/6/2.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, LoginView {

    private Button mLoginButton;
    private TextView mRegisterTextView;
    private ILoginChanged mILoginChanged;
    private LoginPresenter presenter;
    private EditText mAccountEditText;
    private EditText mPasswordEditText;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, null);
        initView(view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        presenter = new LoginPresenterImpl(this);
        Log.i("login", "createview");
        return view;
    }

    private void initView(View view) {
        mLoginButton = (Button) view.findViewById(R.id.login_login_bt);
        mLoginButton.setOnClickListener(this);
        mRegisterTextView = (TextView) view.findViewById(R.id.login_register_tv);
        mAccountEditText = (EditText) view.findViewById(R.id.login_account_et);
        mPasswordEditText = (EditText) view.findViewById(R.id.login_password_et);
        mRegisterTextView.setOnClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try {
//            mILoginChanged = (ILoginChanged) activity;
            mILoginChanged = (ILoginChanged) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement OnGridViewSelectedListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_bt:
                presenter.loginCheck(mAccountEditText.getText().toString(), mPasswordEditText.getText().toString());
                break;

            case R.id.login_register_tv:
                switch2Register();
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setNetworkError() {
        Log.i("loginfragment", "error");
    }

    @Override
    public void setUserError() {
        final Snackbar snackbar = Snackbar.make(view, "账号或密码错误", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void setAccountError() {
        final Snackbar snackbar = Snackbar.make(view, "账号不能为空", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void setPasswordError() {
        final Snackbar snackbar = Snackbar.make(view, "密码不能为空", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }


    @Override
    public void switch2Person(PersonBean personBean) {
        mILoginChanged.switchLogin2Person(personBean);
    }

    @Override
    public void switch2Register() {
        mILoginChanged.switchLogin2Register();
    }


    public interface ILoginChanged {
        void switchLogin2Person(PersonBean personBean);

        void switchLogin2Register();
    }
}
