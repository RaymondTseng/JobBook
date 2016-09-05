package com.example.jobbook.update.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdatePhoneActivity extends Activity implements View.OnClickListener {

    private ImageButton mBackImageButton;
    private TextView mOriginalPhoneTextView;
    private EditText mCodeEditText;
    private Button mGetCodeButton;
    private EditText mNewPhoneEditText;
    private PersonBean personBean = MyApplication.getmPersonBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_change_phone);
        initViews();
        initEvents();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.person_change_phone_back_ib);
        mOriginalPhoneTextView = (TextView) findViewById(R.id.person_change_phone_original_phone_tv);
        mCodeEditText = (EditText) findViewById(R.id.person_change_phone_code_et);
        mGetCodeButton = (Button) findViewById(R.id.person_change_phone_code_bt);
        mNewPhoneEditText = (EditText) findViewById(R.id.person_change_phone_new_phone_et);
    }

    private void initEvents() {
        mBackImageButton.setOnClickListener(this);
        mGetCodeButton.setOnClickListener(this);

        mOriginalPhoneTextView.setText(personBean.getTelephone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_change_phone_back_ib:
                finish();
                break;

            case R.id.person_change_phone_code_bt:
                break;
        }
    }
}
