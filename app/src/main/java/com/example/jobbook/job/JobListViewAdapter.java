package com.example.jobbook.job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.jobbook.R;


/**
 * Created by 椰树 on 2016/5/21.
 */
public class JobListViewAdapter extends BaseAdapter {
    private Context mContext;

    public JobListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewholder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.job_listview_item, null);
            viewholder = new ViewHolder();
            viewholder.mCompanyLogo = (ImageView) view.findViewById(R.id.job_lv_company_iv);
            viewholder.mJobName = (TextView) view.findViewById(R.id.job_lv_name_tv);
            viewholder.mCompanyName = (TextView) view.findViewById(R.id.job_lv_company_tv);
            viewholder.mLocation = (TextView) view.findViewById(R.id.job_lv_location_tv);
            viewholder.mTime = (TextView) view.findViewById(R.id.job_lv_time_tv);
            viewholder.mSalary = (TextView) view.findViewById(R.id.job_lv_salary_tv);
            view.setTag(viewholder);
        } else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder {
        ImageView mCompanyLogo;
        TextView mJobName;
        TextView mCompanyName;
        TextView mLocation;
        TextView mTime;
        TextView mSalary;
    }
}
