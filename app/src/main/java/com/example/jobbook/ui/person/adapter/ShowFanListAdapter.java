package com.example.jobbook.ui.person.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.util.ImageLoadUtils;

import java.util.List;

/**
 * Created by root on 16-11-30.
 */

public class ShowFanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<TypePersonBean> mData;
    private boolean mShowFooter = true;
    private OnFanItemClickListener onFanItemClickListener;
    private OnFollowFanClickListener onFollowFanClickListener;

    private static int UNFOLLOW_TYPE = 0;
    private static int FOLLOW_TYPE = 1;

    public ShowFanListAdapter(Context mContext, List<TypePersonBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == UNFOLLOW_TYPE){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_detail_fan_lv_item, parent, false);
            return new ViewHolder(v, UNFOLLOW_TYPE);
        }else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_detail_fan_lv_item, parent, false);
            return new ViewHolder(v, FOLLOW_TYPE);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder) {
            TypePersonBean personBean = mData.get(position);
            if(personBean == null){
                return;
            }
            ImageLoadUtils.display(mContext, ((ViewHolder)holder).mHeadImageView, personBean.getHead());
            ((ViewHolder) holder).mNameTextView.setText(personBean.getUsername());
            ((ViewHolder) holder).mWorkSpacePositionTextView.setText(personBean.getWorkSpace()
                    + " " + personBean.getWorkPosition());
        }
    }

    @Override
    public int getItemCount() {
//        int begin = mShowFooter ? 1 : 0;
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getType() == UNFOLLOW_TYPE){
            return UNFOLLOW_TYPE;
        }
        return FOLLOW_TYPE;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mHeadImageView;
        TextView mNameTextView;
        TextView mWorkSpacePositionTextView;
        View itemView;
        ImageButton mFollowImageButton;

        public ViewHolder(View view, int type) {
            super(view);
            mHeadImageView = (ImageView) view.findViewById(R.id.user_detail_fan_lv_item_head_iv);
            mNameTextView = (TextView) view.findViewById(R.id.user_detail_fan_lv_item_name_tv);
            mWorkSpacePositionTextView = (TextView) view.findViewById(R.id.user_detail_fan_lv_item_space_position_tv);
            itemView = view.findViewById(R.id.user_detail_fan_lv_item_ll);
            mFollowImageButton = (ImageButton) view.findViewById(R.id.user_detail_fan_lv_item_follow_ib);
            itemView.setOnClickListener(this);
            if(type == UNFOLLOW_TYPE){
                mFollowImageButton.setOnClickListener(this);
            }else{
                mFollowImageButton.setVisibility(View.GONE);
            }
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.user_detail_fan_lv_item_ll:
                    if(onFanItemClickListener != null){
                        onFanItemClickListener.onFanItemClick(mData.get(getLayoutPosition()));
                    }
                    break;
                case R.id.user_detail_fan_lv_item_follow_ib:
                    if(onFollowFanClickListener != null){
                        onFollowFanClickListener.onFollowFanClick(mData.get(getLayoutPosition()));
                    }
                    break;
            }
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

    public void refreshData(List<TypePersonBean> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    public interface OnFanItemClickListener{
        void onFanItemClick(TypePersonBean personBean);
    }

    public interface OnFollowFanClickListener{
        void onFollowFanClick(TypePersonBean personBean);
    }

    public void setOnFanItemClickListener(OnFanItemClickListener onFanItemClickListener) {
        this.onFanItemClickListener = onFanItemClickListener;
    }

    public void setOnFollowFanClickListener(OnFollowFanClickListener onFollowFanClickListener) {
        this.onFollowFanClickListener = onFollowFanClickListener;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }
}
