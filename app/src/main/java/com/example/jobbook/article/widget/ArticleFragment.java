package com.example.jobbook.article.widget;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.article.ArticlesAdapter;
import com.example.jobbook.article.presenter.ArticlePresenter;
import com.example.jobbook.article.presenter.ArticlePresenterImpl;
import com.example.jobbook.article.view.ArticleView;
import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.app.constants.Constants;
import com.example.jobbook.app.constants.Urls;
import com.example.jobbook.ui.DividerItemDecoration;
import com.example.jobbook.util.L;
import com.example.jobbook.base.LazyLoadFragment;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Xu on 2016/7/5.
 */
public class ArticleFragment extends LazyLoadFragment implements ArticleView, View.OnClickListener,
        PopupWindow.OnDismissListener, RadioGroup.OnCheckedChangeListener, SwipeRefreshLayout.OnRefreshListener {

    private int pageIndex = 0;
    private int currentType = Constants.INDEX_ARTICLE_ALL;

    private Animation mListViewHideAnimation;
    private Animation mListViewShowAnimation;
    private Animation mBlankLayoutShowAnimation;
    private Animation mBlankLayoutHideAnimation;
    private Animation mDropImageButtonAnimation;

    @BindView(R.id.article_title_tv)
    TextView mTitleTextView;

    @BindView(R.id.article_title_ll)
    LinearLayout mArticleTitleLayout;

    @BindView(R.id.article_blank_ll)
    LinearLayout mBlankLayout;

    @BindView(R.id.article_title_drop_ib)
    ImageButton mDropImageButton;

    @BindView(R.id.article_rv)
    RecyclerView mRecyclerView;

    private PopupWindow mMenuPopupWindow;
    private View mMenuView;
    private ArticlePresenter presenter;
    private List<ArticleBean> list;
    private ArticlesAdapter adapter;
    private RadioGroup radioGroup;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected int setContentView() {
        return R.layout.fragment_article;
    }

    @Override
    protected void lazyLoad() {
        initEvents();
        initAnimation();
    }

    @Override
    protected void initViews() {
        mMenuView = inflater.inflate(R.layout.article_title_bar_rg, null);
//        mArticleTitleLayout = findViewById(R.id.article_title_ll);
//        mBlankLayout = findViewById(R.id.article_blank_ll);
//        mTitleTextView = findViewById(R.id.article_title_tv);
//        mRecyclerView = findViewById(R.id.article_rv);
        mSwipeRefreshLayout = findViewById(R.id.article_swipe_container);
//        mDropImageButton = findViewById(R.id.article_title_drop_ib);
        radioGroup = (RadioGroup) mMenuView.findViewById(R.id.article_title_rg);
        L.i("initViews");
    }

    private void initEvents() {
//        list = new ArrayList<>();
        presenter = new ArticlePresenterImpl(this);
        mTitleTextView.setText(Constants.ARTICLE_ALL);
        mMenuPopupWindow = new PopupWindow(mMenuView, ViewGroup.LayoutParams.MATCH_PARENT,
                (int) ((Util.getHeight(getActivity()) / (double) 556) * 192), true);
        mMenuPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)));
        mMenuPopupWindow.setOutsideTouchable(true);
        mMenuPopupWindow.setOnDismissListener(this);
        mArticleTitleLayout.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ArticlesAdapter(getActivity());
        adapter.setOnItemClickListener(mOnItemClickListener);
        adapter.setOnFooterItemClickListener(mOnFooterItemClickListener);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, Util.getHeight(getActivity()) / 4);
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
        if (articlesList == null || articlesList.size() < Urls.PAZE_SIZE) {
            adapter.setmShowFooter(false);
        }
        if (pageIndex == 0) {
            adapter.updateData(list);
        } else {
            //如果没有更多数据了,则隐藏footer布局
//            if (articlesList == null || articlesList.size() == 0) {
//                adapter.setmShowFooter(false);
//            }
            adapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAZE_SIZE;
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg(String msg) {
//        if (pageIndex == 0) {
//            adapter.setmShowFooter(false);
//            adapter.notifyDataSetChanged();
//        }
//        Util.showSnackBar(getActivity().findViewById(R.id.article_fragment), "网络无法连接！", "重试");

//        View parentview = getActivity().findViewById(android.R.id.content);
        Util.showSnackBar(MyApplication.mSnackBarView, msg, "重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }

    @OnClick(R.id.article_title_ll)
    public void click_top_title(View v) {
        int visible = mBlankLayout.getVisibility();
        if (mMenuPopupWindow.isShowing() && visible == View.VISIBLE) {
            mMenuPopupWindow.dismiss();
        } else {
            mDropImageButton.startAnimation(mDropImageButtonAnimation);
            mRecyclerView.startAnimation(mListViewShowAnimation);
            mMenuPopupWindow.showAsDropDown(v, 0, 0);
//                    mMenuPopupWindow.showAsDropDown(v, 0, (Util.getHeight(getActivity()) / 720) * 20);
            mBlankLayout.startAnimation(mBlankLayoutShowAnimation);
            mBlankLayout.setVisibility(View.VISIBLE);
            mDropImageButton.setImageResource(R.mipmap.down_white);
        }
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
                    mMenuPopupWindow.showAsDropDown(v, 0, 0);
//                    mMenuPopupWindow.showAsDropDown(v, 0, (Util.getHeight(getActivity()) / 720) * 20);
                    mBlankLayout.startAnimation(mBlankLayoutShowAnimation);
                    mBlankLayout.setVisibility(View.VISIBLE);
                    mDropImageButton.setImageResource(R.mipmap.down_white);
                }
                break;
        }
    }

    @OnCheckedChanged({R.id.article_title_all_rb, R.id.article_title_engagement_rb,
            R.id.article_title_politic_rb, R.id.article_title_life_rb})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.article_title_all_rb:
                    mTitleTextView.setText(Constants.ARTICLE_ALL);
//                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_ALL);
                    currentType = Constants.INDEX_ARTICLE_ALL;
                    onRefresh();
                    L.i("change");
                    break;
                case R.id.article_title_engagement_rb:
                    mTitleTextView.setText(Constants.ARTICLE_ENGAGEMENT);
//                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_ENGAGEMENT);
                    currentType = Constants.INDEX_ARTICLE_ENGAGEMENT;
                    onRefresh();
                    L.i("change");
                    break;
                case R.id.article_title_politic_rb:
                    mTitleTextView.setText(Constants.ARTICLE_POLITIC);
//                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_POLITIC);
                    currentType = Constants.INDEX_ARTICLE_POLITIC;
                    onRefresh();
                    L.i("change");
                    break;
                case R.id.article_title_life_rb:
                    mTitleTextView.setText(Constants.ARTICLE_LIFE);
//                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_LIFE);
                    currentType = Constants.INDEX_ARTICLE_LIFE;
                    onRefresh();
                    L.i("change");
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.article_title_all_rb:
                mTitleTextView.setText(Constants.ARTICLE_ALL);
//                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_ALL);
                currentType = Constants.INDEX_ARTICLE_ALL;
                onRefresh();
                L.i("change");
                break;
            case R.id.article_title_engagement_rb:
                mTitleTextView.setText(Constants.ARTICLE_ENGAGEMENT);
//                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_ENGAGEMENT);
                currentType = Constants.INDEX_ARTICLE_ENGAGEMENT;
                onRefresh();
                L.i("change");
                break;
            case R.id.article_title_politic_rb:
                mTitleTextView.setText(Constants.ARTICLE_POLITIC);
//                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_POLITIC);
                currentType = Constants.INDEX_ARTICLE_POLITIC;
                onRefresh();
                L.i("change");
                break;
            case R.id.article_title_life_rb:
                mTitleTextView.setText(Constants.ARTICLE_LIFE);
//                presenter.loadArticles(pageIndex, Constants.INDEX_ARTICLE_LIFE);
                currentType = Constants.INDEX_ARTICLE_LIFE;
                onRefresh();
                L.i("change");
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


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisiableItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisiableItem + 1 == adapter.getItemCount()
                    && adapter.ismShowFooter()) {
                //加载更多
                L.i("loading more data");
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
            bundle.putParcelable("article_detail", article);
            Util.toAnotherActivity(getActivity(), ArticleDetailActivity.class, bundle);
        }
    };

    private ArticlesAdapter.OnFooterItemClickListener mOnFooterItemClickListener = new ArticlesAdapter.OnFooterItemClickListener() {
        @Override
        public void onFooterItemClick(View view, int position) {
            presenter.loadArticles(pageIndex, currentType);
        }
    };

    @Override
    public void onRefresh() {
        L.i("onRefresh");
        pageIndex = 0;
        if (list != null) {
            list.clear();
        }
        presenter.loadArticles(pageIndex, currentType);
        adapter.notifyDataSetChanged();
    }
}
