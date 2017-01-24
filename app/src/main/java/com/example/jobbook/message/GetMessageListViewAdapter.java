package com.example.jobbook.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.MessageBean;
import com.example.jobbook.util.L;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Xu on 2016/12/7.
 */

public class GetMessageListViewAdapter extends BaseAdapter {

    private static final int HEADER_USER = 1;
    private static final int HEADER_SYSTEM = 2;
    private static final int USER = 3;
    private static final int SYSTEM = 4;

    private Context context;
    //    private String[] headers;
    private List<MessageBean> mData;
    private OnMessageItemClickListener listener;

    public GetMessageListViewAdapter(Context context, List<MessageBean> list) {
        this.context = context;
        this.mData = list;
//        this.headers = context.getResources().getStringArray(R.array.header);
//        this.messages = messages;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        final MessageBean messageBean = mData.get(position);
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_getmessage_lv_user_item, null);
            viewHolder = new ViewHolder();
            viewHolder.parentview = (LinearLayout) view.findViewById(R.id.getmessage_lv_user_item_parent_view_ll);
            viewHolder.head = (CircleImageView) view.findViewById(R.id.getmessage_lv_user_item_head_iv);
            viewHolder.title = (TextView) view.findViewById(R.id.getmessage_lv_user_item_title_tv);
            viewHolder.content = (TextView) view.findViewById(R.id.getmessage_lv_user_item_content_tv);
            viewHolder.time = (TextView) view.findViewById(R.id.getmessage_lv_user_item_time_tv);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.parentview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(messageBean);
                }
            }
        });
        switch (messageBean.getType()) {
            case 1:
                viewHolder.title.setText("有人关注你啦");
                break;
            case 2:
                viewHolder.title.setText("有人给你的工作圈点赞啦");
                break;
            case 3:
                viewHolder.title.setText("有人评论你的工作圈啦");
                break;
        }
        viewHolder.content.setText("点击查看");
        viewHolder.time.setText(messageBean.getTime());
        L.i("getmessage" , "success");
        return view;
    }

    class ViewHolder {
        LinearLayout parentview;
        CircleImageView head;
        TextView title;
        TextView content;
        TextView time;
    }

    public void refreshData(List<MessageBean> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setOnMessageItemClickLitener(OnMessageItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnMessageItemClickListener {
        void onItemClick(MessageBean messageBean);
    }
}
