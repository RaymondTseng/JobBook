package com.example.jobbook.ui.person.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.app.Constants;
import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.Util;

import java.util.List;

/**
 * Created by root on 16-12-9.
 */

public class FavouriteArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ArticleBean> mData;

    private OnArticleItemClickListener onArticleItemClickListener;

    public FavouriteArticleAdapter(Context mContext, List<ArticleBean> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_recyclerview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            ArticleBean article = mData.get(position);
            if(article == null){
                return;
            }
            ((ViewHolder) holder).mTime.setText(article.getDate());
            ((ViewHolder) holder).mTitle.setText(Util.subContent(article.getTitle(), 8));
            ((ViewHolder) holder).mContent.setText(Util.subContent(article.getContent(), 25));
            ImageLoadUtils.display(mContext, ((ViewHolder) holder).mLogo, article.getImage());
            switch (article.getType()) {
                case Constants.INDEX_ARTICLE_ENGAGEMENT:
                    ((ViewHolder) holder).mLabel.setText("就业技巧");
                    break;
                case Constants.INDEX_ARTICLE_POLITIC:
                    ((ViewHolder) holder).mLabel.setText("政策解读");
                    break;
                case Constants.INDEX_ARTICLE_LIFE:
                    ((ViewHolder) holder).mLabel.setText("生活资讯");
                    break;
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(mData == null){
            return 0;
        }
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mLabel;
        TextView mTitle;
        TextView mContent;
        TextView mTime;
        ImageView mLogo;
        RelativeLayout mParentView;

        public ViewHolder(View view) {
            super(view);
            mContent = (TextView) view.findViewById(R.id.article_lv_content_tv);
            mLabel = (TextView) view.findViewById(R.id.article_lv_label_tv);
            mLogo = (ImageView) view.findViewById(R.id.article_lv_iv);
            mTitle = (TextView) view.findViewById(R.id.article_lv_title_tv);
            mTime = (TextView) view.findViewById(R.id.article_lv_time_tv);
            mParentView = (RelativeLayout) view.findViewById(R.id.article_recyclerview_item_rl);
            mParentView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onArticleItemClickListener != null){
                onArticleItemClickListener.onArticleItemClick(mData.get(getLayoutPosition()));
            }
        }
    }

    public interface OnArticleItemClickListener{
        void onArticleItemClick(ArticleBean articleBean);
    }

    public void setOnArticleItemClickListener(OnArticleItemClickListener onArticleItemClickListener) {
        this.onArticleItemClickListener = onArticleItemClickListener;
    }

    public void onRefreshData(List<ArticleBean> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }
}
