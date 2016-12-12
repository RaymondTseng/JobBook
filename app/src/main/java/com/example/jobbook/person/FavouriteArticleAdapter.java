package com.example.jobbook.person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.article.ArticlesAdapter;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.commons.Constants;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.Util;

import java.util.List;

/**
 * Created by root on 16-12-9.
 */

public class FavouriteArticleAdapter extends BaseAdapter {
    private Context mContext;
    private List<ArticleBean> mData;

    public FavouriteArticleAdapter(Context mContext, List<ArticleBean> mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        ArticleBean article = mData.get(position);
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.article_recyclerview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mContent = (TextView) view.findViewById(R.id.article_lv_content_tv);
            viewHolder.mLabel = (TextView) view.findViewById(R.id.article_lv_label_tv);
            viewHolder.mLogo = (ImageView) view.findViewById(R.id.article_lv_iv);
            viewHolder.mTitle = (TextView) view.findViewById(R.id.article_lv_title_tv);
            viewHolder.mTime = (TextView) view.findViewById(R.id.article_lv_time_tv);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTime.setText(article.getDate());
        viewHolder.mTitle.setText(Util.subContent(article.getTitle(), 8));
        viewHolder.mContent.setText(Util.subContent(article.getContent(), 25));
        ImageLoadUtils.display(mContext, viewHolder.mLogo, article.getImage());
        switch (article.getType()) {
            case Constants.INDEX_ARTICLE_ENGAGEMENT:
                viewHolder.mLabel.setText("就业技巧");
                break;
            case Constants.INDEX_ARTICLE_POLITIC:
                viewHolder.mLabel.setText("政策解读");
                break;
            case Constants.INDEX_ARTICLE_LIFE:
                viewHolder.mLabel.setText("生活资讯");
                break;
        }

        return view;
    }

    class ViewHolder{
        TextView mLabel;
        TextView mTitle;
        TextView mContent;
        TextView mTime;
        ImageView mLogo;
    }

    public void onRefreshData(List<ArticleBean> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }
}
