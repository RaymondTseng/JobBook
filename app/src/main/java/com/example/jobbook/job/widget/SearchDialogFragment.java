package com.example.jobbook.job.widget;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.jobbook.R;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2016/8/24.
 */
public class SearchDialogFragment extends DialogFragment {

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
        View view = inflater.inflate(R.layout.job_search_bar_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        ImageButton mBackImageButton = (ImageButton) view.findViewById(R.id.job_search_bar_dialog_back_ib);
        final EditText mEditText = (EditText) view.findViewById(R.id.job_search_bar_dialog_et);
        ImageButton mSearchImageButton = (ImageButton) view.findViewById(R.id.job_search_bar_dialog_search_ib);
        mBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSearchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("content", mEditText.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - Util.dip2px(getActivity(), 6), getDialog().getWindow().getAttributes().height);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }

}
