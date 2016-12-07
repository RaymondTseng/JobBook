package com.example.jobbook.userdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.MomentBean;

import java.util.List;

/**
 * Created by root on 16-11-28.
 */

public class UserDetailMomentAdapter extends BaseAdapter {
    private Context mContext;
    private List<MomentBean> mData;

    public UserDetailMomentAdapter(Context mContext, List<MomentBean> mData){
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
        ViewHolder mViewHolder;
        MomentBean moment = mData.get(position);
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.user_detail_moment_lv_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mContentTextView = (TextView) view.findViewById(R.id.user_detail_moment_content_tv);
            mViewHolder.mTimeTextView = (TextView) view.findViewById(R.id.user_detail_moment_time_tv);
            view.setTag(mViewHolder);
        }else{
            view = convertView;
            mViewHolder = (ViewHolder) view.getTag();
        }
        mViewHolder.mContentTextView.setText(moment.getContent());
        mViewHolder.mTimeTextView.setText(moment.getDate());
        return view;
    }

    public void refreshData(List<MomentBean> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    class ViewHolder{
        TextView mContentTextView;
        TextView mTimeTextView;
    }
}
