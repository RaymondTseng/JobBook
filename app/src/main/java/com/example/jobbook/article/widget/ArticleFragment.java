package com.example.jobbook.article.widget;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.article.ArticleListViewAdapter;
import com.example.jobbook.article.presenter.ArticlePresenter;
import com.example.jobbook.article.presenter.ArticlePresenterImpl;
import com.example.jobbook.article.view.ArticleView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.commons.Constants;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class ArticleFragment extends Fragment implements ArticleView, View.OnClickListener, PopupWindow.OnDismissListener {

    public static final int ARTICLE_ALL = 0;
    public static final int ARTICLE_ENGAGEMENT = 1;
    public static final int ARTICLE_POLITIC = 2;
    public static final int ARTICLE_LIFE = 3;

    private ListView mListView;
    private TextView mTitleTextView;
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
    private ArticlePresenter presenter;
    private View view;
    private List<ArticleBean> list;
    private ArticleListViewAdapter adapter;
    private RadioGroup radioGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article, container, false);
        initViews(view);
        initAnimation();
        list = new ArrayList<>();
        presenter = new ArticlePresenterImpl(this);
        // 默认加载全部文章
        presenter.loadArticles(ARTICLE_ALL);
        mTitleTextView.setText(Constants.ARTICLE_ALL);
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
        mTitleTextView = (TextView) view.findViewById(R.id.article_title_tv);
        mListView = (ListView) view.findViewById(R.id.article_lv);
        mDropImageButton = (ImageButton) view.findViewById(R.id.article_title_drop_ib);
        mMenuPopupWindow = new PopupWindow(mMenuView, ViewGroup.LayoutParams.MATCH_PARENT,
                (getmHeight() / 556) * 192, true);
        mMenuPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)));
        mMenuPopupWindow.setOutsideTouchable(true);
        mMenuPopupWindow.setOnDismissListener(this);
        mArticleTitleLayout.setOnClickListener(this);
//        mListView.setOnItemClickListener(this);
        radioGroup = (RadioGroup) mMenuView.findViewById(R.id.article_title_rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.article_title_all_rb:
                        mTitleTextView.setText(Constants.ARTICLE_ALL);
                        presenter.loadArticles(ARTICLE_LIFE);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.article_title_engagement_rb:
                        mTitleTextView.setText(Constants.ARTICLE_ENGAGEMENT);
                        presenter.loadArticles(ARTICLE_ENGAGEMENT);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.article_title_politic_rb:
                        mTitleTextView.setText(Constants.ARTICLE_POLITIC);
                        presenter.loadArticles(ARTICLE_POLITIC);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.article_title_life_rb:
                        mTitleTextView.setText(Constants.ARTICLE_LIFE);
                        presenter.loadArticles(ARTICLE_LIFE);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }

    private void initAnimation() {
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
    public void addArticles(List<ArticleBean> articlesList) {
        // 加载数据的地方
        if (articlesList != null) {
            list = articlesList;
            adapter = new ArticleListViewAdapter(getActivity(), articlesList);
            adapter.setOnItemClickListener(new ArticleListViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Bundle bundle = new Bundle();
//                    Log.i("article_detail", list.get(position).getContent());
                    bundle.putSerializable("article_detail", list.get(position));
                    Util.toAnotherActivity(getActivity(), ArticleDetailActivity.class, bundle);
                }
            });
            mListView.setAdapter(adapter);
        }
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {
        final Snackbar snackbar = Snackbar.make(view, "干货读取错误，请重试！", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    private int getmHeight() {
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.article_title_ll:
                int visible = mBlankLayout.getVisibility();
                if (mMenuPopupWindow.isShowing() && visible == View.VISIBLE) {
                    mMenuPopupWindow.dismiss();
                } else {
                    mDropImageButton.startAnimation(mDropImageButtonAnimation);
                    mListView.startAnimation(mListViewShowAnimation);
                    mMenuPopupWindow.showAsDropDown(v, 0, (getmHeight() / 720) * 20);
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

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("article_detail", list.get(position));
//        Util.toAnotherActivity(getActivity(), ArticleDetailActivity.class, bundle);
//    }
}
