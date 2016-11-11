package com.example.jobbook.article;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class ArticleDetailListViewAdapter extends BaseAdapter {
    private Context mContext;
    public ArticleDetailListViewAdapter(Context mContext){
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder mViewHolder;
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.article_detail_lv_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mComment = (TextView)view.findViewById(R.id.article_detail_lv_comment_tv);
            mViewHolder.mLike = (TextView)view.findViewById(R.id.article_detail_lv_like_tv);
            mViewHolder.mLogo = (ImageView)view.findViewById(R.id.article_detail_lv_user_logo_iv);
            mViewHolder.mName = (TextView)view.findViewById(R.id.article_detail_lv_user_name_tv);
            view.setTag(mViewHolder);
        }else{
            view = convertView;
            mViewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }
    class ViewHolder{
        ImageView mLogo;
        TextView mName;
        TextView mComment;
        TextView mLike;
    }
}
