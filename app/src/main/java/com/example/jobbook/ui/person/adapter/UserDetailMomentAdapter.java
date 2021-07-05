package com.example.jobbook.ui.person.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.jobbook.R;
import com.example.jobbook.model.bean.MomentBean;

import java.util.List;

/**
 * Created by Xu on 16-11-28.
 */

public class UserDetailMomentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<MomentBean> mData;
    private OnMomentItemClickListener onMomentItemClickListener;

    public UserDetailMomentAdapter(Context mContext, List<MomentBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_detail_moment_lv_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            MomentBean moment = mData.get(position);
            if (moment == null) {
                return;
            }
            ((ViewHolder) holder).mContentTextView.setText(moment.getContent());
            ((ViewHolder) holder).mTimeTextView.setText(moment.getDate());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }

        return mData.size();
    }


    public void refreshData(List<MomentBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mContentTextView;
        TextView mTimeTextView;
        LinearLayout mParentView;

        public ViewHolder(View view) {
            super(view);
            mContentTextView = (TextView) view.findViewById(R.id.user_detail_moment_content_tv);
            mTimeTextView = (TextView) view.findViewById(R.id.user_detail_moment_time_tv);
            mParentView = (LinearLayout) view.findViewById(R.id.user_detail_moment_lv_item_ll);
            mParentView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onMomentItemClickListener != null) {
                onMomentItemClickListener.onMomentItemClick(mData.get(getLayoutPosition()));
            }
        }
    }

    public interface OnMomentItemClickListener {
        void onMomentItemClick(MomentBean momentBean);
    }

    public void setOnMomentItemClickListener(OnMomentItemClickListener listener) {
        this.onMomentItemClickListener = listener;
    }
}
