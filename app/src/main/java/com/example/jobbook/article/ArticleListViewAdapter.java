package com.example.jobbook.article;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.util.Util;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 椰树 on 2016/5/22.
 */
public class ArticleListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ArticleBean> mList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public ArticleListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewholder;
        ArticleBean articleBean = (ArticleBean) getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.article_listview_item, null);
            viewholder = new ViewHolder(view, position);
            viewholder.mContent = (TextView) view.findViewById(R.id.article_lv_content_tv);
            viewholder.mLabel = (ImageView) view.findViewById(R.id.article_lv_label_iv);
            viewholder.mLogo = (ImageView) view.findViewById(R.id.article_lv_iv);
            viewholder.mTime = (TextView) view.findViewById(R.id.article_lv_time_tv);
            viewholder.mTitle = (TextView) view.findViewById(R.id.article_lv_title_tv);
            view.setTag(viewholder);
        } else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        viewholder.mContent.setText(Util.subContent(articleBean.getContent()));
//        viewholder.mLabel.setBackgroundResource();
//        viewholder.mLogo
        viewholder.mTime.setText(articleBean.getDate());
        viewholder.mTitle.setText(articleBean.getTitle());
        return view;
    }

    class ViewHolder implements View.OnClickListener {
        ImageView mLabel;
        TextView mTitle;
        TextView mContent;
        TextView mTime;
        ImageView mLogo;
        int position;

        public ViewHolder(View view, int position) {
            super();
            this.position = position;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, position);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void updateData(List<ArticleBean> list){
        this.mList = list;
        notifyDataSetChanged();
    }
}
