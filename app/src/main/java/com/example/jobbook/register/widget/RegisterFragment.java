package com.example.jobbook.register.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.register.presenter.RegisterPresenter;
import com.example.jobbook.register.presenter.RegisterPresenterImpl;
import com.example.jobbook.register.view.RegisterView;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.Util;


/**
 * Created by 椰树 on 2016/6/2.
 */
public class RegisterFragment extends Fragment implements RegisterView, View.OnClickListener {

    private IRegisterChanged mIRegisterChanged;
    private Button mRegisterButton;
    private EditText mAccountEditText;
    private EditText mUserNameEditText;
    private EditText mEmailEditText;
    private EditText mPwdEditText;
    private EditText mPwdAgainEditText;
    private EditText mCodeEditText;
    private ImageView mCodeImageView;
    private ImageButton mCloseImageButton;
    private RegisterPresenter presenter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, null);
        initViews(view);
        initEvents();
        return view;
    }

    private void initViews(View view) {
        mRegisterButton = (Button) view.findViewById(R.id.register_register_bt);
        mAccountEditText = (EditText) view.findViewById(R.id.register_account_et);
        mUserNameEditText = (EditText) view.findViewById(R.id.register_username_et);
        mEmailEditText = (EditText) view.findViewById(R.id.register_telephone_et);
        mPwdEditText = (EditText) view.findViewById(R.id.register_password_et);
        mPwdAgainEditText = (EditText) view.findViewById(R.id.register_confirm_password_et);
        mCodeEditText = (EditText) view.findViewById(R.id.register_code_et) ;
        mCloseImageButton = (ImageButton) view.findViewById(R.id.register_close_ib);
        mCodeImageView = (ImageView) view.findViewById(R.id.register_code_iv);
    }
    private void initEvents(){
        presenter = new RegisterPresenterImpl(this);
        mRegisterButton.setOnClickListener(this);
        mCloseImageButton.setOnClickListener(this);
        mCodeImageView.setOnClickListener(this);
        ImageLoadUtils.display(getActivity(), mCodeImageView, Urls.GET_CODE_URL);
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
        showSnackbar("连接成功");
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
        showSnackbar("用户名为空");
    }

    @Override
    public void pwdBlankError() {
        showSnackbar("密码为空");
    }

    @Override
    public void pwdConfirmBlankError() {
        showSnackbar("确认密码为空");
    }

    @Override
    public void pwdNotEqualError() {
        showSnackbar("密码与确认密码不一致");
    }

    @Override
    public void accountExistError() {
        showSnackbar("用户名已存在");
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
        showSnackbar("账号存在非法字符");
    }

    @Override
    public void userNameBlankError() {
        showSnackbar("昵称为空");
    }

    @Override
    public void telephoneBlankError() {
        showSnackbar("手机号码为空");
    }

    @Override
    public void codeBlankError() {
        showSnackbar("验证码为空");
    }

    @Override
    public void codeError() {
        showSnackbar("验证码错误");
    }


    private void showSnackbar(String content){
        if(view == null){
            view = getActivity().findViewById(R.id.main_layout);
        }
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG);
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
            case R.id.register_register_bt:
                presenter.registerCheck(mAccountEditText.getText().toString(),
                        mUserNameEditText.getText().toString(), mEmailEditText.getText().toString(),
                        mPwdEditText.getText().toString(), mPwdAgainEditText.getText().toString(),
                        mCodeEditText.getText().toString());
//                switch2Person();
                break;
            case R.id.register_close_ib:
                switch2Login();
                break;
            case R.id.register_code_iv:
                Log.i("register", "click");
                mCodeImageView.setImageResource(0);
                ImageLoadUtils.display(getActivity(), mCodeImageView, Urls.GET_CODE_URL);
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
