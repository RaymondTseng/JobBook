package com.example.jobbook.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jobbook.R;

import java.util.LinkedList;

/**
 * Created by Xu on 2016/8/31.
 */
public class SearchRecordListViewAdapter extends BaseAdapter {

    private Context mContext;
    private LinkedList<String> list;

    public SearchRecordListViewAdapter(Context context, LinkedList<String> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        Viewholder viewholder;
        String word = list.get(list.size() - position - 1);
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.job_search_record, null);
            viewholder = new Viewholder();
            viewholder.textView = (TextView) view.findViewById(R.id.job_search_item_tv);
            view.setTag(viewholder);
        } else {
            view = convertView;
            viewholder = (Viewholder) view.getTag();
        }
        if (word != null) {
            viewholder.textView.setText(word);
        }
        return view;
    }

    public void clearData() {
        list = null;
        this.notifyDataSetChanged();
    }

    class Viewholder {
        TextView textView;
    }
}
