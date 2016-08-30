package com.example.jobbook.article;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.commons.Constants;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.Util;

import java.util.List;

/**
 * Created by 椰树 on 2016/8/30.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<ArticleBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public ArticlesAdapter(Context mContext){
        this.mContext = mContext;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_recyclerview_item, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.loadingfooter_layout, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ArticleBean article = mData.get(position);
            if(article == null){
                return;
            }
            ((ItemViewHolder) holder).mContent.setText(Util.subContent(article.getContent()));
            switch (article.getType()) {
                case Constants.INDEX_ARTICLE_ENGAGEMENT:
                    ((ItemViewHolder) holder).mLabel.setBackgroundResource(R.mipmap.engagement_24px_blue);
                    break;
                case Constants.INDEX_ARTICLE_POLITIC:
                    ((ItemViewHolder) holder).mLabel.setBackgroundResource(R.mipmap.politic_blue);
                    break;
                case Constants.INDEX_ARTICLE_LIFE:
                    ((ItemViewHolder) holder).mLabel.setBackgroundResource(R.mipmap.life_blue);
                    break;
            }
            ImageLoadUtils.display(mContext, ((ItemViewHolder)holder).mLogo, article.getImage());
            ((ItemViewHolder) holder).mTime.setText(article.getDate());
            ((ItemViewHolder) holder).mTitle.setText(article.getTitle());
        }
    }

    public void updateData(List<ArticleBean> mData){
        this.mData = mData;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter?1:0;
        if(mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if(!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mLabel;
        TextView mTitle;
        TextView mContent;
        TextView mTime;
        ImageView mLogo;
        public ItemViewHolder(View view) {
            super(view);
            mContent = (TextView) view.findViewById(R.id.article_lv_content_tv);
            mLabel = (ImageView) view.findViewById(R.id.article_lv_label_iv);
            mLogo = (ImageView) view.findViewById(R.id.article_lv_iv);
            mTime = (TextView) view.findViewById(R.id.article_lv_time_tv);
            mTitle = (TextView) view.findViewById(R.id.article_lv_title_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, this.getLayoutPosition());
            }
        }
    }

    public ArticleBean getItem(int position){
        return mData == null ? null : mData.get(position);
    }

    public boolean ismShowFooter() {
        return mShowFooter;
    }

    public void setmShowFooter(boolean mShowFooter) {
        this.mShowFooter = mShowFooter;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }
}
