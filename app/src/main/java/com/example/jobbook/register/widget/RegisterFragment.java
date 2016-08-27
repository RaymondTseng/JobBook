package com.example.jobbook.register.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.register.presenter.RegisterPresenter;
import com.example.jobbook.register.presenter.RegisterPresenterImpl;
import com.example.jobbook.register.view.RegisterView;


/**
 * Created by 椰树 on 2016/6/2.
 */
public class RegisterFragment extends Fragment implements RegisterView, View.OnClickListener {

    private IRegisterChanged mIRegisterChanged;
    private TextView mRegisterTextView;
    private EditText mAccountEditText;
    private EditText mPwdEditText;
    private EditText mPwdAgainEditText;
    private RegisterPresenter presenter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, null);
        initViews(view);
        presenter = new RegisterPresenterImpl(this);
        return view;
    }

    private void initViews(View view) {
        mRegisterTextView = (TextView) view.findViewById(R.id.register_register_tv);
        mAccountEditText = (EditText) view.findViewById(R.id.register_account_et);
        mPwdEditText = (EditText) view.findViewById(R.id.register_password_et);
        mPwdAgainEditText = (EditText) view.findViewById(R.id.register_password_again_et);
        mRegisterTextView.setOnClickListener(this);

    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try {
            mIRegisterChanged = (IRegisterChanged) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement OnGridViewSelectedListener");
        }
    }

    @Override
    public void success() {
        final Snackbar snackbar = Snackbar.make(view, "连接成功", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void networkError() {
        Log.i("registerfragment", "error");
    }

    @Override
    public void accountBlankError() {
        final Snackbar snackbar = Snackbar.make(view, "用户名为空", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void pwdBlankError() {
        final Snackbar snackbar = Snackbar.make(view, "密码为空", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void pwdConfirmBlankError() {
        final Snackbar snackbar = Snackbar.make(view, "确认密码为空", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void pwdNotEqualError() {
        final Snackbar snackbar = Snackbar.make(view, "密码与确认密码不一致", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void accountExistError() {
        final Snackbar snackbar = Snackbar.make(view, "用户名已存在", Snackbar.LENGTH_LONG);
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
        mIRegisterChanged.switchRegister2Person(personBean);
    }

    @Override
    public void switch2Login() {
        mIRegisterChanged.switchRegister2Login();
    }

    @Override
    public void accountIllegalError() {
        final Snackbar snackbar = Snackbar.make(view, "账号存在非法字符", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_register_tv:
                presenter.registerCheck(mAccountEditText.getText().toString(),
                        mPwdEditText.getText().toString(), mPwdAgainEditText.getText().toString());
//                switch2Person();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    public interface IRegisterChanged {
        void switchRegister2Person(PersonBean personBean);

        void switchRegister2Login();
    }

}
