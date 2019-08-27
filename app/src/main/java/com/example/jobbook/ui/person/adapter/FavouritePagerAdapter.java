package com.example.jobbook.ui.person.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Xu on 16-12-7.
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
