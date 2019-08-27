package com.example.jobbook.ui.moment.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Xu on 16-11-8.
 */

public class MomentPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> mFragments;

    public MomentPagerAdapter(FragmentManager fm, List<Fragment> mFragments){
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

}
