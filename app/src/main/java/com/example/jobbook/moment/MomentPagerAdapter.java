package com.example.jobbook.moment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by root on 16-11-8.
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
