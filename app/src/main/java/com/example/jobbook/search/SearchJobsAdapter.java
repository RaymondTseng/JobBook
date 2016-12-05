package com.example.jobbook.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.util.ImageLoadUtils;

import java.util.List;

/**
 * Created by 椰树 on 2016/8/30.
 */
public class SearchJobsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<JobBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public SearchJobsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.job_recyclerview_item, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.loadingfooter_layout, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            JobBean job = mData.get(position);
            if (job == null) {
                return;
            }
//            L.i("search_job", job.getLogo());
            ImageLoadUtils.display(mContext, ((ItemViewHolder) holder).mCompanyLogo, job.getLogo());
            ((ItemViewHolder) holder).mCompanyName.setText(job.getCompanyName());
            ((ItemViewHolder) holder).mJobName.setText(job.getName());
            ((ItemViewHolder) holder).mLocation.setText(job.getCompanyLocation());
            ((ItemViewHolder) holder).mSalary.setText(job.getSalary());
            ((ItemViewHolder) holder).mTime.setText(job.getTime());
        }
    }

    public void updateData(List<JobBean> mData) {
        this.mData = mData;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mCompanyLogo;
        TextView mJobName;
        TextView mCompanyName;
        TextView mLocation;
        TextView mTime;
        TextView mSalary;

        public ItemViewHolder(View view) {
            super(view);
            mCompanyLogo = (ImageView) view.findViewById(R.id.job_lv_iv);
            mJobName = (TextView) view.findViewById(R.id.job_lv_name_tv);
            mCompanyName = (TextView) view.findViewById(R.id.job_lv_company_tv);
            mLocation = (TextView) view.findViewById(R.id.job_lv_location_tv);
            mTime = (TextView) view.findViewById(R.id.job_lv_time_tv);
            mSalary = (TextView) view.findViewById(R.id.job_lv_salary_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, this.getPosition());
            }
        }
    }

    public JobBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public boolean ismShowFooter() {
        return mShowFooter;
    }

    public void setmShowFooter(boolean mShowFooter) {
        this.mShowFooter = mShowFooter;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }
}
