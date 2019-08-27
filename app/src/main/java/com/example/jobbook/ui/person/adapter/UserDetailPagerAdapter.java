package com.example.jobbook.ui.person.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Xu on 16-11-21.
 */

public class UserDetailPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private FragmentManager mFragmentManager;

    public UserDetailPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
        mFragmentManager = fm;
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
