package com.example.jobbook.blacklist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Xu on 2016/12/8.
 */

public class BlackListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public BlackListRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_blacklist_rv_item, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mHead;
        TextView mUserName;
        TextView mUserPosition;
        ImageButton mCancel;

        public ItemViewHolder(View view) {
            super(view);
            mHead = (CircleImageView) view.findViewById(R.id.blacklist_rv_item_head_iv);
            mUserName = (TextView) view.findViewById(R.id.blacklist_rv_item_username_tv);
            mUserPosition = (TextView) view.findViewById(R.id.blacklist_rv_item_userposition_tv);
            mCancel = (ImageButton) view.findViewById(R.id.blacklist_rv_item_cancel_ib);
        }
    }

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
