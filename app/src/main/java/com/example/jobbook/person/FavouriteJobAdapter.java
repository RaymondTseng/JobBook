package com.example.jobbook.person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.Util;

import java.util.List;

/**
 * Created by root on 16-12-9.
 */

public class FavouriteJobAdapter extends BaseAdapter {
    private Context mContext;
    private List<JobBean> mData;

    public FavouriteJobAdapter(Context mContext, List<JobBean> mData){
        this.mContext = mContext;
        this.mData = mData;
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
        JobBean job = mData.get(position);
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.job_recyclerview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mCompanyLogo = (ImageView) view.findViewById(R.id.job_lv_iv);
            viewHolder.mCompanyName = (TextView) view.findViewById(R.id.job_lv_company_tv);
            viewHolder.mJobName = (TextView) view.findViewById(R.id.job_lv_name_tv);
            viewHolder.mLocation = (TextView) view.findViewById(R.id.job_lv_location_tv);
            viewHolder.mSalary = (TextView) view.findViewById(R.id.job_lv_salary_tv);
            viewHolder.mTime = (TextView) view.findViewById(R.id.job_lv_time_tv);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        ImageLoadUtils.display(mContext, viewHolder.mCompanyLogo, job.getLogo());
        viewHolder.mCompanyName.setText(job.getCompanyName());
        viewHolder.mTime.setText(job.getTime());
        viewHolder.mSalary.setText(job.getSalary());
        viewHolder.mLocation.setText(Util.subContent(job.getCompanyLocation(), 7));
        viewHolder.mJobName.setText(job.getName());
        return view;
    }

    public void onRefreshData(List<JobBean> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    class ViewHolder{
        ImageView mCompanyLogo;
        TextView mJobName;
        TextView mCompanyName;
        TextView mLocation;
        TextView mTime;
        TextView mSalary;
    }
}
