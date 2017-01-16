package com.example.jobbook.person;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;

import java.util.List;

/**
 * Created by root on 16-11-30.
 */

public class ShowFanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<PersonBean> mData;
    private boolean mShowFooter = true;

    public ShowFanListAdapter(Context mContext, List<PersonBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mHeadImageView;
        TextView mNameTextView;
        TextView mWorkSpacePositionTextView;

        public ViewHolder(View view) {
            super(view);
            mHeadImageView = (ImageView) view.findViewById(R.id.user_detail_follow_lv_item_head_iv);
            mNameTextView = (TextView) view.findViewById(R.id.user_detail_title_follower_tv);
            mWorkSpacePositionTextView = (TextView) view.findViewById(R.id.user_detail_follow_lv_item_space_position_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public PersonBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public boolean ismShowFooter() {
        return mShowFooter;
    }

    public void setmShowFooter(boolean mShowFooter) {
        this.mShowFooter = mShowFooter;
    }

    public void refreshData(List<PersonBean> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }
}
