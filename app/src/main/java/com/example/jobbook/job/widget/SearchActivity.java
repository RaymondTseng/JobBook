package com.example.jobbook.job.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jobbook.R;
import com.example.jobbook.main.widget.MainActivity;

/**
 * Created by Xu on 2016/8/24.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    private ImageButton mBackImageButton;
    private EditText mSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_search_activity);
        init();
//        Bundle bundle = getIntent().getExtras();
//        Toast.makeText(SearchActivity.this, bundle.getString("content"), Toast.LENGTH_LONG).show();
    }

    private void init() {
        mBackImageButton = (ImageButton) findViewById(R.id.job_search_activity_back_ib);
        mBackImageButton.setOnClickListener(this);
        mSearchEditText = (EditText) findViewById(R.id.job_search_activity_et);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSearchEditText.setText(bundle.getString("content"));
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
