package com.example.jobbook.upload.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.jobbook.R;

/**
 * Created by Xu on 2016/9/5.
 */
public class UploadPopupWindow extends PopupWindow {

    private Button mTakePhotoButton;
    private Button mPickPhotoButton;
    private Button mCancelButton;
    private View menuView;

    public UploadPopupWindow(Context context, OnClickListener itemOnClick) {
        super(context);
        menuView = LayoutInflater.from(context).inflate(R.layout.person_upload_layout, null);
        initViews(menuView);
        initEvents(itemOnClick);
        // 设置SelectPicPopupWindow的View
        this.setContentView(menuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        menuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = menuView.findViewById(R.id.person_upload_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    private void initViews(View menuView) {
        mTakePhotoButton = (Button) menuView.findViewById(R.id.person_upload_takePhoto_bt);
        mPickPhotoButton = (Button) menuView.findViewById(R.id.person_upload_pickPhoto_bt);
        mCancelButton = (Button) menuView.findViewById(R.id.person_upload_cancel_bt);
    }

    private void initEvents(OnClickListener itemOnClick) {
        mTakePhotoButton.setOnClickListener(itemOnClick);
        mPickPhotoButton.setOnClickListener(itemOnClick);
        mCancelButton.setOnClickListener(itemOnClick);
    }
}
