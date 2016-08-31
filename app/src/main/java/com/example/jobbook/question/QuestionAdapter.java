package com.example.jobbook.question;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.commons.Urls;

import java.util.List;

/**
 * Created by 椰树 on 2016/8/30.
 */
public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<QuestionBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public QuestionAdapter(Context mContext){
        this.mContext = mContext;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_listview_item, parent, false);
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
        if(holder instanceof ItemViewHolder){
            QuestionBean question = mData.get(position);
            if(question == null){
                return;
            }
            ((ItemViewHolder) holder).mTitle.setText(question.getTitle());
            ((ItemViewHolder) holder).mContent.setText(question.getContent());
            ((ItemViewHolder) holder).mUserName.setText(question.getAuthor().getUsername());
            ((ItemViewHolder) holder).mCommentNumbers.setText(question.getCommentnum() + "");
            ((ItemViewHolder) holder).mTime.setText(question.getDate());
        }
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

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTitle;
        TextView mContent;
        ImageView mUserHead;
        TextView mUserName;
        TextView mCommentNumbers;
        TextView mTime;
        public ItemViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.question_lv_title_tv);
            mContent = (TextView) view.findViewById(R.id.question_lv_content_tv);
            mUserHead = (ImageView) view.findViewById(R.id.question_lv_user_head_iv);
            mUserName = (TextView) view.findViewById(R.id.question_lv_user_name_tv);
            mCommentNumbers = (TextView) view.findViewById(R.id.question_lv_comment_tv);
            mTime = (TextView) view.findViewById(R.id.question_lv_time_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, this.getPosition());
            }
        }
    }

    public void updateData(List<QuestionBean> mData){
        this.mData = mData;
        if(mData.size() % Urls.PAZE_SIZE != 0){
            this.setmShowFooter(false);
        }
        this.notifyDataSetChanged();
    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public QuestionBean getItem(int position){
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
