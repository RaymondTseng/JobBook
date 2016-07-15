package com.example.jobbook.job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;


/**
 * Created by 椰树 on 2016/5/21.
 */
public class CompanyCommentListViewAdapter extends BaseAdapter {
    private Context mContext;

    public CompanyCommentListViewAdapter(Context mContext) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewholder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.company_detail_listview_item, null);
            viewholder = new ViewHolder();
            viewholder.mCommentUserLogo = (ImageView) view.findViewById(R.id.company_detail_lv_user_logo_iv);
            viewholder.mCommentUserName = (TextView) view.findViewById(R.id.company_detail_lv_user_name_tv);
            viewholder.mCommentUserComment = (TextView) view.findViewById(R.id.company_detail_lv_user_comment_tv);
            view.setTag(viewholder);
        } else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder {
        ImageView mCommentUserLogo;
        TextView mCommentUserName;
        TextView mCommentUserComment;
    }
}
