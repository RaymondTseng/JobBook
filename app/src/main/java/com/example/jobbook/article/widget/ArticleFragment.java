package com.example.jobbook.article.widget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jobbook.R;
import com.example.jobbook.article.ArticleListViewAdapter;
import com.example.jobbook.article.view.ArticleView;
import com.example.jobbook.bean.ArticleBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class ArticleFragment extends Fragment implements ArticleView,View.OnClickListener, PopupWindow.OnDismissListener,
        AdapterView.OnItemClickListener{

    private ListView mListView;
    private LinearLayout mArticleTitleLayout;
    private LinearLayout mBlankLayout;
    private Animation mListViewHideAnimation;
    private Animation mListViewShowAnimation;
    private Animation mBlankLayoutShowAnimation;
    private Animation mBlankLayoutHideAnimation;
    private Animation mDropImageButtonAnimation;
    private PopupWindow mMenuPopupWindow;
    private View mMenuView;
    private ImageButton mDropImageButton;
    private RadioButton mEngagementRadioButton;
    private RadioButton mAllRadioButton;
    private RadioButton mPoliticRadioButton;
    private RadioButton mLifeRadioButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        initViews(view);
        initAnimation();
        return view;
    }

    private void initViews(View view) {
        mArticleTitleLayout = (LinearLayout) view.findViewById(R.id.article_title_ll);
        mMenuView = getActivity().getLayoutInflater().inflate(R.layout.article_title_bar_rg, null);
        mEngagementRadioButton = (RadioButton) mMenuView.findViewById(R.id.article_title_engagement_rb);
        mAllRadioButton = (RadioButton) mMenuView.findViewById(R.id.article_title_all_rb);
        mPoliticRadioButton = (RadioButton) mMenuView.findViewById(R.id.article_title_politic_rb);
        mLifeRadioButton = (RadioButton) mMenuView.findViewById(R.id.article_title_life_rb);
        mBlankLayout = (LinearLayout) view.findViewById(R.id.article_blank_ll);
        mListView = (ListView) view.findViewById(R.id.article_lv);
        mDropImageButton = (ImageButton) view.findViewById(R.id.article_title_drop_ib);
        mMenuPopupWindow = new PopupWindow(mMenuView, ViewGroup.LayoutParams.MATCH_PARENT,
                (getmHeight()/556)*192, true);
        mMenuPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)));
        mMenuPopupWindow.setOutsideTouchable(true);
        mMenuPopupWindow.setOnDismissListener(this);
        mArticleTitleLayout.setOnClickListener(this);
//        mListView.setAdapter(new ArticleListViewAdapter(getActivity()));
        mListView.setOnItemClickListener(this);
    }
    private void initAnimation(){
        mMenuPopupWindow.setAnimationStyle(R.style.article_menu_animation_style);
        mBlankLayoutShowAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.article_menu_show);
        mBlankLayoutHideAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.article_menu_hide);
        mListViewShowAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.article_listview_show);
        mListViewHideAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.article_listview_hide);
        mDropImageButtonAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.article_drop_anim);
        mDropImageButtonAnimation.setFillAfter(true);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void addArticles(List<ArticleBean> newsList) {
        // 加载数据的地方
//        mListView.setAdapter(new ArticleListViewAdapter(getActivity()));
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {

    }

    private int getmHeight(){
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.article_title_ll:
                int visible = mBlankLayout.getVisibility();
                if(mMenuPopupWindow.isShowing() && visible == View.VISIBLE){
                    mMenuPopupWindow.dismiss();
                }else{
                    mDropImageButton.startAnimation(mDropImageButtonAnimation);
                    mListView.startAnimation(mListViewShowAnimation);
                    mMenuPopupWindow.showAsDropDown(v ,0 , (getmHeight()/720)*20);
                    mBlankLayout.startAnimation(mBlankLayoutShowAnimation);
                    mBlankLayout.setVisibility(View.VISIBLE);
                    mDropImageButton.setImageResource(R.mipmap.down_white);
                }
                break;
            case R.id.article_title_all_rb:
                break;
            case R.id.article_title_engagement_rb:
                break;
            case R.id.article_title_politic_rb:
                break;
            case R.id.article_title_life_rb:
                break;
        }
    }

    @Override
    public void onDismiss() {
        mDropImageButton.startAnimation(mDropImageButtonAnimation);
        mBlankLayout.startAnimation(mBlankLayoutHideAnimation);
        mListView.startAnimation(mListViewHideAnimation);
        mDropImageButton.setImageResource(R.mipmap.up_white);
        mBlankLayout.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
        startActivity(intent);
    }
}
