package com.example.jobbook.register.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.register.presenter.RegisterPresenter;
import com.example.jobbook.register.presenter.RegisterPresenterImpl;
import com.example.jobbook.register.view.RegisterView;


/**
 * Created by 椰树 on 2016/6/2.
 */
public class RegisterFragment extends Fragment implements RegisterView, View.OnClickListener{

    private IRegisterChanged mIRegisterChanged;
    private TextView mRegisterTextView;
    private EditText mUserNameEditText;
    private EditText mPwdEditText;
    private EditText mPwdAgainEditText;
    private RegisterPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register, null);
        initViews(view);
        presenter = new RegisterPresenterImpl(this);
        return view;
    }

    private void initViews(View view) {
        mRegisterTextView = (TextView) view.findViewById(R.id.register_register_tv);
        mUserNameEditText = (EditText) view.findViewById(R.id.register_account_et);
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
    public void hideProgress() {

    }

    @Override
    public void setNetworkError() {
        Log.i("registerfragment", "error");
    }

    @Override
    public void setUserNameError() {

    }

    @Override
    public void setPwdError() {

    }

    @Override
    public void switch2Person() {
        mIRegisterChanged.switchRegister2Person();
    }

    @Override
    public void switch2Login() {
        mIRegisterChanged.switchRegister2Login();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_register_tv:
                presenter.registerCheck(mUserNameEditText.getText().toString(), mPwdEditText.getText().toString());
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
        void switchRegister2Person();
        void switchRegister2Login();
    }

}
