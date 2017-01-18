package com.example.jobbook.userdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.util.ImageLoadUtils;

import java.util.List;

/**
 * Created by root on 16-11-28.
 */

public class UserDetailFollowAdapter extends BaseAdapter {
    private List<PersonBean> mData;
    private Context mContext;
    private OnUserFollowItemClickListener listener;

    public UserDetailFollowAdapter(Context mContext, List<PersonBean> mData){
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
        final PersonBean mPersonBean = mData.get(position);
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.user_detail_follow_lv_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mHeadImageView = (ImageView) view.findViewById(R.id.user_detail_follow_lv_item_head_iv);
            mViewHolder.mNameTextView = (TextView) view.findViewById(R.id.user_detail_follow_lv_item_name_tv);
            mViewHolder.mWorkSpacePositionTextView = (TextView) view.findViewById(R.id.user_detail_follow_lv_item_space_position_tv);
            mViewHolder.mParentView = (RelativeLayout) view.findViewById(R.id.user_detail_follow_lv_item_rl);
            view.setTag(mViewHolder);
        }else{
            view = convertView;
            mViewHolder = (ViewHolder) view.getTag();
        }
        ImageLoadUtils.display(mContext, mViewHolder.mHeadImageView, mPersonBean.getHead());
        mViewHolder.mNameTextView.setText(mPersonBean.getUsername());
        mViewHolder.mWorkSpacePositionTextView.setText(mPersonBean.getWorkSpace() + " " +
                mPersonBean.getWorkPosition());
        mViewHolder.mParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onUserFollowItemClick(mPersonBean);
                }
            }
        });
        return view;
    }

    class ViewHolder{
        ImageView mHeadImageView;
        TextView mNameTextView;
        TextView mWorkSpacePositionTextView;
        RelativeLayout mParentView;
    }

    public void refreshData(List<PersonBean> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setOnUserFollowItemClickListener(OnUserFollowItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnUserFollowItemClickListener{
        void onUserFollowItemClick(PersonBean personBean);
    }
}
