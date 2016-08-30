package com.example.jobbook.article.widget;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.jobbook.article.ArticlesAdapter;
import com.example.jobbook.article.presenter.ArticlePresenter;
import com.example.jobbook.article.presenter.ArticlePresenterImpl;
import com.example.jobbook.article.view.ArticleView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.commons.Constants;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.job.JobsAdapter;
import com.example.jobbook.job.widget.JobDetailActivity;
import com.example.jobbook.util.DividerItemDecoration;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class ArticleFragment extends Fragment implements ArticleView, View.OnClickListener,
        PopupWindow.OnDismissListener, RadioGroup.OnCheckedChangeListener, SwipeRefreshLayout.OnRefreshListener {

    private int pageIndex = 0;
    private int currentType = Constants.INDEX_ARTICLE_ALL;

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
    private ArticlePresenter presenter;
    private View view;
    private List<ArticleBean> list;
    private ArticlesAdapter adapter;
    private RadioGroup radioGroup;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article, container, false);
        initViews(view);
        initEvents();
        initAnimation();
        return view;
    }

    private void initViews(View view) {
        mArticleTitleLayout = (LinearLayout) view.findViewById(R.id.article_title_ll);
        mMenuView = getActivity().getLayoutInflater().inflate(R.layout.article_title_bar_rg, null);
        mBlankLayout = (LinearLayout) view.findViewById(R.id.article_blank_ll);
        mTitleTextView = (TextView) view.findViewById(R.id.article_title_tv);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.article_rv);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.article_swipe_container);
        mDropImageButton = (ImageButton) view.findViewById(R.id.article_title_drop_ib);
        radioGroup = (RadioGroup) mMenuView.findViewById(R.id.article_title_rg);
    }

    private void initEvents() {
        list = new ArrayList<>();
        presenter = new ArticlePresenterImpl(this);
        mTitleTextView.setText(Constants.ARTICLE_ALL);
        mMenuPopupWindow = new PopupWindow(mMenuView, ViewGroup.LayoutParams.MATCH_PARENT,
                (Util.getHeight(getActivity()) / 556) * 192, true);
        mMenuPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)));
        mMenuPopupWindow.setOutsideTouchable(true);
        mMenuPopupWindow.setOnDismissListener(this);
        mArticleTitleLayout.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ArticlesAdapter(getActivity().getApplicationContext());
        adapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        onRefresh();
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
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void addArticles(List<ArticleBean> articlesList) {
        // 加载数据的地方
        adapter.setmShowFooter(true);
        if (list == null) {
            list = new ArrayList<>();
        }
        list = articlesList;
        if (pageIndex == 0) {
            adapter.updateData(list);
        } else {
            //如果没有更多数据了,则隐藏footer布局
            if (articlesList == null || articlesList.size() == 0) {
                adapter.setmShowFooter(false);
            }
            adapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if(pageIndex == 0) {
            adapter.setmShowFooter(false);
            adapter.notifyDataSetChanged();
        }
        view = view == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.main_layout);
        final Snackbar snackbar = Snackbar.make(view, "干货读取错误，请重试！", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
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
                    mRecyclerView.startAnimation(mListViewShowAnimation);
                    mMenuPopupWindow.showAsDropDown(v, 0, (Util.getHeight(getActivity()) / 720) * 20);
                    mBlankLayout.startAnimation(mBlankLayoutShowAnimation);
                    mBlankLayout.setVisibility(View.VISIBLE);
                    mDropImageButton.setImageResource(R.mipmap.down_white);
                }
                break;
        }
    }

    @Override
    public void onDismiss() {
        mDropImageButton.startAnimation(mDropImageButtonAnimation);
        mBlankLayout.startAnimation(mBlankLayoutHideAnimation);
        mRecyclerView.startAnimation(mListViewHideAnimation);
        mDropImageButton.setImageResource(R.mipmap.up_white);
        mBlankLayout.setVisibility(View.GONE);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.article_title_all_rb:
                mTitleTextView.setText(Constants.ARTICLE_ALL);
                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_ALL);
                currentType = Constants.INDEX_ARTICLE_ALL;
                adapter.notifyDataSetChanged();
                break;
            case R.id.article_title_engagement_rb:
                mTitleTextView.setText(Constants.ARTICLE_ENGAGEMENT);
                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_ENGAGEMENT);
                currentType = Constants.INDEX_ARTICLE_ENGAGEMENT;
                Log.i("engagement", "change");
                adapter.notifyDataSetChanged();
                break;
            case R.id.article_title_politic_rb:
                mTitleTextView.setText(Constants.ARTICLE_POLITIC);
                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_POLITIC);
                currentType = Constants.INDEX_ARTICLE_POLITIC;
                adapter.notifyDataSetChanged();
                break;
            case R.id.article_title_life_rb:
                mTitleTextView.setText(Constants.ARTICLE_LIFE);
                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_LIFE);
                currentType = Constants.INDEX_ARTICLE_LIFE;
                adapter.notifyDataSetChanged();
                break;
        }
    }


//    @Override
//    public void onItemClick(View view, int position) {
//        Bundle bundle = new Bundle();
//                    Log.i("article_detail", list.get(position).getContent());
//        bundle.putSerializable("article_detail", list.get(position));
//        Util.toAnotherActivity(getActivity(), ArticleDetailActivity.class, bundle);
//    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("article_detail", list.get(position));
//        Util.toAnotherActivity(getActivity(), ArticleDetailActivity.class, bundle);
//    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisiableItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisiableItem + 1 == adapter.getItemCount()
                    && adapter.ismShowFooter()) {
                //加载更多
                Log.i("article_fragment", "loading more data");
                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_ALL);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisiableItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };

    private ArticlesAdapter.OnItemClickListener mOnItemClickListener = new ArticlesAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            ArticleBean article = adapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("article_detail", article);
            Util.toAnotherActivity(getActivity(), ArticleDetailActivity.class, bundle);
        }
    };

    @Override
    public void onRefresh() {
        Log.i("TAG", "onRefresh");
        pageIndex = 0;
        if (list != null) {
            list.clear();
        }
        presenter.loadArticles(pageIndex, currentType);
    }
}
