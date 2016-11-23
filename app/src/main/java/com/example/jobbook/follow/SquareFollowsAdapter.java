package com.example.jobbook.follow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.ImageLoadUtils;

import java.util.List;

/**
 * Created by 椰树 on 2016/8/30.
 */
public class SquareFollowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ITEM_OVER = 1;
    private static final int TYPE_FOOTER = 2;

    private List<MomentBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private OnHeadClickListener mOnHeadClickListener;
    private OnFavouriteButtonClickListener mOnFavouriteButtonClickListener;

    public SquareFollowsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.square_follow_rv_item, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else if (viewType == TYPE_ITEM_OVER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.square_follow_recycleview_item_oversize, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.loadingfooter_layout, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            MomentBean moment = mData.get(position);
            if (moment == null) {
                return;
            }
            ((ItemViewHolder) holder).mContent.setText(moment.getContent());
            ImageLoadUtils.display(mContext, ((ItemViewHolder) holder).mUserHead, moment.getAuthor().getHead());
//            ((ItemViewHolder) holder).mUserName.setText(moment.getAuthor().getUsername());
            ((ItemViewHolder) holder).mFavouriteNumbers.setText(moment.getLikesNum() + "");
            ((ItemViewHolder) holder).mCommentNumbers.setText(moment.getCommentNum() + "");
            ((ItemViewHolder) holder).mTime.setText(moment.getDate());
        }
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
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (!mShowFooter) {
            if (mData.get(position).getContent().length() > 75) {
                return TYPE_ITEM_OVER;
            } else {
                return TYPE_ITEM;
            }
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            if (mData.get(position).getContent().length() > 75) {
                return TYPE_ITEM_OVER;
            } else {
                return TYPE_ITEM;
            }
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mContent;
        ImageView mUserHead;
        TextView mUserName;
        TextView mFavouriteNumbers;
        TextView mCommentNumbers;
        TextView mTime;
//        ImageButton mNoInterestButton;

        public ItemViewHolder(View view) {
            super(view);
            mContent = (TextView) view.findViewById(R.id.square_follow_rv_content_tv);
            mUserHead = (ImageView) view.findViewById(R.id.square_follow_rv_head_iv);
//            mUserName = (TextView) view.findViewById(R.id.moment_lv_user_name_tv);
            mFavouriteNumbers = (TextView) view.findViewById(R.id.square_follow_rv_favourite_tv);
            mCommentNumbers = (TextView) view.findViewById(R.id.square_follow_rv_comment_tv);
            mTime = (TextView) view.findViewById(R.id.square_follow_rv_time_tv);
//            mNoInterestButton = (ImageButton) view.findViewById(R.id.square_follow_rv_no_interest_ib);
            itemView.setOnClickListener(this);
//            mNoInterestButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.square_follow_rv_favourite_ib:
                    if (mOnFavouriteButtonClickListener != null) {
                        mOnFavouriteButtonClickListener.onFavouriteButtonClick(v, this.getLayoutPosition());
                    }
                    break;

                case R.id.square_follow_rv_head_iv:
                    if (mOnHeadClickListener != null) {
                        mOnHeadClickListener.onHeadClick(v, this.getLayoutPosition());
                    }
                    break;

                default:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, this.getPosition());
                    }
                    break;

            }
        }
    }

    public void updateData(List<MomentBean> mData) {
        this.mData = mData;
        if (mData.size() % Urls.PAZE_SIZE != 0) {
            this.setmShowFooter(false);
        }
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnHeadClickListener {
        void onHeadClick(View view, int position);
    }

    public interface OnFavouriteButtonClickListener {
        void onFavouriteButtonClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnHeadClickListener(OnHeadClickListener onHeadClickListener) {
        this.mOnHeadClickListener = onHeadClickListener;
    }

    public void setOnFavouriteButtonClickListener(OnFavouriteButtonClickListener onFavouriteButtonClickListener) {
        this.mOnFavouriteButtonClickListener = onFavouriteButtonClickListener;
    }

    public MomentBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public boolean ismShowFooter() {
        return mShowFooter;
    }

    public void setmShowFooter(boolean mShowFooter) {
        this.mShowFooter = mShowFooter;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }
}
