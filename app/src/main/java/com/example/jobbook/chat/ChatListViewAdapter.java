package com.example.jobbook.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.ChatBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/17.
 */
public class ChatListViewAdapter extends BaseAdapter{
    private Context mContext;
    private List<ChatBean> chats;

    public ChatListViewAdapter(Context mContext, List<ChatBean> chats) {
        this.mContext = mContext;
        this.chats = chats;
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        // Todo 判断类型
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewholder;
        ChatBean chatBean = chats.get(position);
        if (convertView == null) {
            viewholder = new ViewHolder();
            // TODO: 2016/7/19 判断类型
            if (true) {
                view = LayoutInflater.from(mContext).inflate(R.layout.chat_receive_layout, null);
                viewholder.mContent = (TextView) view.findViewById(R.id.chat_receive_layout_tv);
                view.setTag(viewholder);
            }
            else {
                view = LayoutInflater.from(mContext).inflate(R.layout.chat_send_layout, null);
                viewholder.mContent = (TextView) view.findViewById(R.id.chat_send_layout_tv);
                view.setTag(viewholder);
            }
        } else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder {
        TextView mContent;
    }
}
