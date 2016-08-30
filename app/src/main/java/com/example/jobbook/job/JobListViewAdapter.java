package com.example.jobbook.job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 椰树 on 2016/5/21.
 */
public class JobListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<JobBean> mList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public JobListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if(mList == null){
            return 0;
        }
        return mList.size();

    }

    @Override
    public Object getItem(int position) {

        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewholder;
        JobBean jobBean = (JobBean) getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.job_listview_item, null);
            viewholder = new ViewHolder(view, position);
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
        viewholder.mCompanyName.setText(jobBean.getCompanyName());
        viewholder.mJobName.setText(jobBean.getName());
        viewholder.mLocation.setText(jobBean.getCompanyLocation());
        viewholder.mSalary.setText(jobBean.getSalary());
        viewholder.mTime.setText(jobBean.getTime());
        return view;
    }

    public void updateData(List<JobBean> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    class ViewHolder implements View.OnClickListener{
        ImageView mCompanyLogo;
        TextView mJobName;
        TextView mCompanyName;
        TextView mLocation;
        TextView mTime;
        TextView mSalary;
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
