package com.example.jobbook.question;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.bean.QuestionCommentBean;
import com.example.jobbook.bean.QuestionDetailBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class QuestionDetailListViewAdapter extends BaseAdapter{
    private Context mContext;
    private List<QuestionCommentBean> mData;

    private OnItemClickListener onItemClickListener;
    public QuestionDetailListViewAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void updateData(List<QuestionCommentBean> mData){
        this.mData = mData;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder mViewHolder;
        QuestionCommentBean questionComment = mData.get(position);
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.question_detail_listview_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.mContent = (TextView) view.findViewById(R.id.question_detail_lv_content_tv);
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
        mViewHolder.mContent.setText(questionComment.getContent());
        mViewHolder.mFloor.setText(position + "");
        mViewHolder.mLike.setText(questionComment.getGood() + "");
//        mViewHolder.mLogo
        mViewHolder.mName.setText(questionComment.getApplier().getUsername());
        mViewHolder.mTime.setText(questionComment.getAsk_time());
        mViewHolder.mUnlike.setText(questionComment.getBad() + "");
        return view;
    }

    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder{
        ImageView mLogo;
        TextView mName;
        TextView mContent;
        TextView mTime;
        TextView mFloor;
        TextView mLike;
        TextView mUnlike;
        int position;


    }
}
