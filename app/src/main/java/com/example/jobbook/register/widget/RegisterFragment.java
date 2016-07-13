package com.example.jobbook.register.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.register.view.RegisterView;


/**
 * Created by 椰树 on 2016/6/2.
 */
public class RegisterFragment extends Fragment implements RegisterView, View.OnClickListener{

    private IRegisterChanged mIRegisterChanged;
    private TextView mRegisterTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mRegisterTextView = (TextView) view.findViewById(R.id.register_register_tv);
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
                switch2Person();
                break;
        }
    }

    public interface IRegisterChanged {
        void switchRegister2Person();
        void switchRegister2Login();
    }

}
