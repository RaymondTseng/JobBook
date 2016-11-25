package com.example.jobbook.userdetail.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jobbook.R;
import com.example.jobbook.util.L;

/**
 * Created by root on 16-11-25.
 */

public class UserDetailMomentFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_square, container, false);

        return view;
    }
}
