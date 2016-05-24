package com.example.jobbook.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.adapter.ArticleListViewAdapter;

/**
 * Created by 椰树 on 2016/5/20.
 */
public class ArticleFragment extends Fragment implements View.OnClickListener, PopupWindow.OnDismissListener{
    private ListView mListView;
    private LinearLayout mArticleTitleLayout;
    private LinearLayout mBlankLayout;
    private Animation mListViewHideAnimation;
    private Animation mListViewShowAnimation;
    private Animation mBlankLayoutShowAnimation;
    private Animation mBlankLayoutHideAnimation;
    private PopupWindow mMenuPopupWindow;
    private View mMenuView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        initViews(view);
        return view;
    }
    private void initViews(View view){
        mArticleTitleLayout = (LinearLayout) view.findViewById(R.id.article_title_ll);
        mMenuView = getActivity().getLayoutInflater().inflate(R.layout.article_title_bar_rg, null);
        mBlankLayout = (LinearLayout) view.findViewById(R.id.article_blank_ll);
        mListView = (ListView) view.findViewById(R.id.article_lv);
        mMenuPopupWindow = new PopupWindow(mMenuView, ViewGroup.LayoutParams.MATCH_PARENT, 350, true);
        mMenuPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)));
        mMenuPopupWindow.setOutsideTouchable(true);
        mMenuPopupWindow.setAnimationStyle(R.style.article_menu_animation_style);
        mMenuPopupWindow.setOnDismissListener(this);
        mArticleTitleLayout.setOnClickListener(this);
        mListView.setAdapter(new ArticleListViewAdapter(getActivity()));
        mBlankLayoutShowAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.article_menu_show);
        mBlankLayoutHideAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.article_menu_hide);
        mListViewShowAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.article_listview_show);
        mListViewHideAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.article_listview_hide);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.article_title_ll:
                int visible = mBlankLayout.getVisibility();
                if(mMenuPopupWindow.isShowing() && visible == View.VISIBLE){
                    mMenuPopupWindow.dismiss();
                }else{
                    mMenuPopupWindow.showAsDropDown(v ,0 , 20);
                    mBlankLayout.startAnimation(mBlankLayoutShowAnimation);
                    mBlankLayout.setVisibility(View.VISIBLE);
                    mListView.startAnimation(mListViewShowAnimation);
                }
                break;
        }
    }

    @Override
    public void onDismiss() {
        mBlankLayout.startAnimation(mBlankLayoutHideAnimation);
        mListView.startAnimation(mListViewHideAnimation);
        mBlankLayout.setVisibility(View.GONE);
    }
}
