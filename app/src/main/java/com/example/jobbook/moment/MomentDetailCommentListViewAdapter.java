package com.example.jobbook.moment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.MomentCommentBean;
import com.example.jobbook.util.ImageLoadUtils;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class MomentDetailCommentListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<MomentCommentBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public MomentDetailCommentListViewAdapter(Context mContext){
        this.mContext = mContext;
    }

    private String calculateSpace(String username) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < username.length(); i++) {
            if ((username.charAt(i) >= 'A' && username.charAt(i) <= 'Z') || (username.charAt(i) >= 'a' && username.charAt(i) <= 'z')) {
                sb.append("   ");
            } else {
                sb.append("     ");
            }
        }
        return sb.toString();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.moment_detail_comment_listview_item, parent, false);
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
        if(holder instanceof ItemViewHolder) {
            MomentCommentBean comment = mData.get(position);
            if(comment == null){
                return;
            }
            ImageLoadUtils.display(mContext, ((ItemViewHolder)holder).mLogo, comment.getApplier().getHead());
            ((ItemViewHolder) holder).mContent.setText(calculateSpace(comment.getApplier().getUsername()) + comment.getContent());
            ((ItemViewHolder) holder).mName.setText(comment.getApplier().getUsername());
        }
    }

    public void updateData(List<MomentCommentBean> mData){
        this.mData = mData;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter?1:0;
        if(mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if(!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mLogo;
        TextView mName;
        TextView mContent;

        public ItemViewHolder(View view){
            super(view);
            mLogo = (ImageView) view.findViewById(R.id.moment_detail_comment_lv_head_iv);
            mName = (TextView) view.findViewById(R.id.moment_detail_comment_lv_name_tv);
            mContent = (TextView) view.findViewById(R.id.moment_detail_comment_lv_content_tv);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, this.getLayoutPosition());
            }
        }
    }

    public MomentCommentBean getItem(int position){
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
