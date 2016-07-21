package com.example.jobbook.question;

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
public class QuestionDetailListViewAdapter extends BaseAdapter{
    private Context mContext;
    public QuestionDetailListViewAdapter(Context mContext){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.question_listview_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mContent = (TextView) view.findViewById(R.id.question_detail_content_tv);
            mViewHolder.mFloor = (TextView) view.findViewById(R.id.question_detail_lv_floor_tv);
            mViewHolder.mLike = (TextView) view.findViewById(R.id.question_detail_lv_like_tv);
            mViewHolder.mLogo = (ImageView) view.findViewById(R.id.question_detail_lv_user_logo_iv);
            mViewHolder.mName = (TextView) view.findViewById(R.id.question_detail_lv_user_name_tv);
            mViewHolder.mTime = (TextView) view.findViewById(R.id.question_detail_lv_time_tv);
            mViewHolder.mUnlike = (TextView) view.findViewById(R.id.question_detail_lv_unlike_tv);
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
        TextView mContent;
        TextView mTime;
        TextView mFloor;
        TextView mLike;
        TextView mUnlike;
    }
}
