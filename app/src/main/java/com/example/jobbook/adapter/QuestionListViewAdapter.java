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
 * Created by Xu on 2016/5/21.
 */
public class QuestionListViewAdapter extends BaseAdapter {
    private Context mContext;

    public QuestionListViewAdapter(Context mContext) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.question_listview_item, null);
            viewholder = new ViewHolder();
            viewholder.mTitle = (TextView) view.findViewById(R.id.question_lv_title_tv);
            viewholder.mContent = (TextView) view.findViewById(R.id.question_lv_title_tv);
            viewholder.mUserHead = (ImageView) view.findViewById(R.id.question_lv_user_head_iv);
            viewholder.mUserName = (TextView) view.findViewById(R.id.question_lv_user_name_tv);
            viewholder.mCommentNumbers = (TextView) view.findViewById(R.id.question_lv_comment_tv);
            viewholder.mTime = (TextView) view.findViewById(R.id.question_lv_time_tv);
            view.setTag(viewholder);
        } else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder {
        TextView mTitle;
        TextView mContent;
        ImageView mUserHead;
        TextView mUserName;
        TextView mCommentNumbers;
        TextView mTime;
    }
}
