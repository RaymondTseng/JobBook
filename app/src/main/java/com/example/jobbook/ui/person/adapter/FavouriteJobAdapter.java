package com.example.jobbook.ui.person.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.Util;

import java.util.List;

/**
 * Created by root on 16-12-9.
 */

public class FavouriteJobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<JobBean> mData;

    private OnJobItemClickListener onJobItemClickListener;

    public FavouriteJobAdapter(Context mContext, List<JobBean> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_recyclerview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            JobBean job = mData.get(position);
            if(job == null){
                return;
            }
            ImageLoadUtils.display(mContext, ((ViewHolder) holder).mCompanyLogo, job.getLogo());
            ((ViewHolder) holder).mCompanyName.setText(job.getCompanyName());
            ((ViewHolder) holder).mTime.setText(job.getTime());
            ((ViewHolder) holder).mSalary.setText(job.getSalary());
            ((ViewHolder) holder).mLocation.setText(Util.subContent(job.getCompanyLocation(), 7));
            ((ViewHolder) holder).mJobName.setText(job.getName());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(mData == null){
            return 0;
        }
        return mData.size();
    }


    public void onRefreshData(List<JobBean> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mCompanyLogo;
        TextView mJobName;
        TextView mCompanyName;
        TextView mLocation;
        TextView mTime;
        TextView mSalary;
        RelativeLayout mParentView;

        public ViewHolder(View view) {
            super(view);
            mCompanyLogo = (ImageView) view.findViewById(R.id.job_lv_iv);
            mCompanyName = (TextView) view.findViewById(R.id.job_lv_company_tv);
            mJobName = (TextView) view.findViewById(R.id.job_lv_name_tv);
            mLocation = (TextView) view.findViewById(R.id.job_lv_location_tv);
            mSalary = (TextView) view.findViewById(R.id.job_lv_salary_tv);
            mTime = (TextView) view.findViewById(R.id.job_lv_time_tv);
            mParentView = (RelativeLayout) view.findViewById(R.id.job_recyclerview_item_rl);
            mParentView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onJobItemClickListener != null){
                onJobItemClickListener.onJobItemClick(mData.get(getLayoutPosition()));
            }
        }
    }

    public interface OnJobItemClickListener{
        void onJobItemClick(JobBean jobBean);
    }

    public void setOnJobItemClickListener(OnJobItemClickListener onJobItemClickListener) {
        this.onJobItemClickListener = onJobItemClickListener;
    }
}
