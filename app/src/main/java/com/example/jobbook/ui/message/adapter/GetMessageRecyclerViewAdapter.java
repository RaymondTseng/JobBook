package com.example.jobbook.ui.message.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jobbook.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Xu on 2016/12/7.
 */

public class GetMessageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER_USER = 1;
    private static final int HEADER_SYSTEM = 2;
    private static final int USER = 3;
    private static final int SYSTEM = 4;

    private Context context;
    private LayoutInflater mInflater;
    private String[] headers;
    private Object[] messages;
    private OnItemClickListener mOnItemClickListener;

    public GetMessageRecyclerViewAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.headers = context.getResources().getStringArray(R.array.header);
        this.messages = messages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == USER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_getmessage_lv_user_item, parent, false);
            return new ItemViewHolder(v);
        } else if (viewType == SYSTEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_getmessage_rv_system_item, parent, false);
            return new ItemViewHolder(v);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CircleImageView head;
        TextView name;
        TextView content;
        TextView time;

        public ItemViewHolder(View view) {
            super(view);
            head = (CircleImageView) view.findViewById(R.id.getmessage_lv_item_head_iv);
            name = (TextView) view.findViewById(R.id.getmessage_lv_item_name_tv);
            content = (TextView) view.findViewById(R.id.getmessage_lv_item_content_tv);
            time = (TextView) view.findViewById(R.id.getmessage_lv_item_time_tv);
        }
    }

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
