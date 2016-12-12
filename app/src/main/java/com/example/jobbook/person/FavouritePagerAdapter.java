package com.example.jobbook.person;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import java.util.List;

/**
 * Created by root on 16-12-7.
 */

public class FavouritePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    public FavouritePagerAdapter(FragmentManager fm, List<Fragment> mFragments){
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
}
