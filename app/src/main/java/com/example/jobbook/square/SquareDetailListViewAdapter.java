package com.example.jobbook.square;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.MomentCommentBean;
import com.example.jobbook.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-11-23.
 */

public class SquareDetailListViewAdapter extends BaseAdapter {
    private List<MomentCommentBean> mComments = new ArrayList<>();
    private Context context;

    public SquareDetailListViewAdapter(Context context){
        this.context = context;
    }

    public void updateData(List<MomentCommentBean> comments){
        this.mComments = comments;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        MomentCommentBean momentCommentBean = (MomentCommentBean) getItem(position);
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.square_detail_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mHeadImageView = (ImageView) view.findViewById(R.id.square_detail_lv_head_iv);
            viewHolder.mNameTextView = (TextView) view.findViewById(R.id.square_detail_lv_name_tv);
            viewHolder.mContentTextView = (TextView) view.findViewById(R.id.square_detail_lv_content_tv);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
            ImageLoadUtils.display(context, viewHolder.mHeadImageView, momentCommentBean.getApplier().getHead());
            viewHolder.mNameTextView.setText(momentCommentBean.getApplier().getUsername());
            viewHolder.mContentTextView.setText(momentCommentBean.getContent());
        }
        return view;
    }

    class ViewHolder{
        ImageView mHeadImageView;
        TextView mNameTextView;
        TextView mContentTextView;
    }


}
