package com.example.jobbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;

/**
 * Created by 椰树 on 2016/5/22.
 */
public class ArticleListViewAdapter extends BaseAdapter {
    private Context mContext;
    public ArticleListViewAdapter(Context mContext){
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
        ViewHolder viewholder;
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.article_listview_item, null);
            viewholder = new ViewHolder();
            viewholder.mContent = (TextView) view.findViewById(R.id.article_lv_content_tv);
            viewholder.mLabel = (ImageView) view.findViewById(R.id.article_lv_label_iv);
            viewholder.mLogo = (ImageView) view.findViewById(R.id.article_lv_iv);
            viewholder.mTime = (TextView) view.findViewById(R.id.article_lv_time_tv);
            viewholder.mTitle= (TextView) view.findViewById(R.id.article_lv_title_tv);
            view.setTag(viewholder);
        }else{
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        return view;
    }
    class ViewHolder{
        ImageView mLabel;
        TextView mTitle;
        TextView mContent;
        TextView mTime;
        ImageView mLogo;
    }
}
