package com.example.jobbook.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jobbook.R;

/**
 * Created by 椰树 on 2016/6/2.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{
    private Button mLoginButton;
    private transferData mTransferData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_login, null);
        initView(view);
        return view;
    }
    private void initView(View view){
        mLoginButton = (Button) view.findViewById(R.id.login_login_bt);
        mLoginButton.setOnClickListener(this);
    }
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try {
            mTransferData = (transferData) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement OnGridViewSelectedListener");
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_login_bt:
                Log.i("TAG", "2");
                mTransferData.changeViewPager();
                Log.i("TAG", "3");
                break;
        }
    }
    public interface transferData{
        void changeViewPager();
    }
}
