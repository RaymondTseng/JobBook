package com.example.jobbook.search.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobbook.R;
import com.example.jobbook.search.SearchRecordListViewAdapter;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import java.util.LinkedList;

/**
 * Created by Xu on 2016/8/24.
 */
public class SearchDialogFragment extends DialogFragment implements View.OnClickListener {

    private ListView mRecordListView;
    private TextView mRemoveRecordTextView;
    private ImageButton mBackImageButton;
    private EditText mEditText;
    private View view;
    private SearchRecordListViewAdapter adapter;
    private LinkedList<String> list;
    private Spinner mSpinner;
    private SharedPreferences sharedPreferences;
    private int mCurrentType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP);
        getDialog().setCancelable(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.job_search_bar_dialog, null);
        initViews(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        mBackImageButton.setOnClickListener(this);
        list = new LinkedList<>();
        ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.search_spinner_select, 0, getResources().getStringArray(R.array.search));
        mSpinnerAdapter.setDropDownViewResource(R.layout.search_spinner_drop);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setDropDownVerticalOffset(50);
        mSpinner.setSelection(0);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sharedPreferences = getActivity().getSharedPreferences("search", Context.MODE_PRIVATE);
        if (Util.getSearchList(sharedPreferences).size() != 0) {
            list = Util.getSearchList(sharedPreferences);
            adapter = new SearchRecordListViewAdapter(getActivity(), list);
            mRecordListView.setAdapter(adapter);
        }
        mRemoveRecordTextView.setOnClickListener(this);
        mRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("content", list.get(list.size() - position - 1));
                bundle.putInt("type", mCurrentType);
                intent.putExtras(bundle);
                startActivity(intent);
                dismiss();
            }
        });
        return builder.create();
    }

    private void initViews(View view) {
        mRecordListView = (ListView) view.findViewById(R.id.job_search_record_lv);
        mRemoveRecordTextView = (TextView) view.findViewById(R.id.job_search_remove_record_tv);
        mBackImageButton = (ImageButton) view.findViewById(R.id.job_search_bar_dialog_back_ib);
        mEditText = (EditText) view.findViewById(R.id.job_search_bar_dialog_et);
        mSpinner = (Spinner) view.findViewById(R.id.job_search_bar_spinner);

//        mEditText.setOnKeyListener(new View.OnKeyListener() {
//
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                    if (imm.isActive()) {
//                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
//                    }
//                    toSearchActivity();
//                    return true;
//                }
//                return false;
//            }
//        });


    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - Util.dip2px(getActivity(), 6), getDialog().getWindow().getAttributes().height);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));

        mEditText.requestFocus();
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
            L.i("search", "keyboardup");
        }
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (!TextUtils.isEmpty(mEditText.getText())) {
                        toSearchActivity();
                    } else {
                        Toast.makeText(getActivity(), "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.job_search_bar_dialog_back_ib:
                dismiss();
                break;

            case R.id.job_search_remove_record_tv:
                Util.clearSearchList(sharedPreferences);
                adapter.clearData();
                Util.setSearchList(sharedPreferences, list = new LinkedList<>());
                break;

//            case R.id.job_search_bar_dialog_search_ib:
//                toSearchActivity();
//                break;

        }
    }

    private void toSearchActivity() {
        if (!TextUtils.isEmpty(mEditText.getText().toString())) {
            list.add(mEditText.getText().toString());
            L.i("search_dialog", "size=" + list.size() + ",add data:" + mEditText.getText().toString());
            Util.setSearchList(sharedPreferences, list);
        }
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("content", mEditText.getText().toString());
        bundle.putInt("type", mCurrentType);
        intent.putExtras(bundle);
        startActivity(intent);
        dismiss();
    }
}
