package com.example.jobbook.job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.CompanyCommentBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 椰树 on 2016/5/21.
 */
public class CompanyCommentListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<CompanyCommentBean> list = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public CompanyCommentListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewholder;
        CompanyCommentBean companyCommentBean = (CompanyCommentBean) getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.company_detail_listview_item, null);
            viewholder = new ViewHolder(view, position);
            viewholder.mCommentUserLogo = (ImageView) view.findViewById(R.id.company_detail_lv_user_logo_iv);
            viewholder.mCommentUserName = (TextView) view.findViewById(R.id.company_detail_lv_user_name_tv);
            viewholder.mCommentUserComment = (TextView) view.findViewById(R.id.company_detail_lv_user_comment_tv);
            view.setTag(viewholder);
        } else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        viewholder.mCommentUserName.setText(companyCommentBean.getAuthor().getUsername());
        viewholder.mCommentUserComment.setText(companyCommentBean.getContent());
        return view;
    }

    public void updateData(List<CompanyCommentBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    class ViewHolder implements View.OnClickListener{
        ImageView mCommentUserLogo;
        TextView mCommentUserName;
        TextView mCommentUserComment;
        int position;

        public ViewHolder(View view, int position) {
            super();
            this.position = position;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(v, position);
            }
        }
    }
}
