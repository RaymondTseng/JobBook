package com.example.jobbook.main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

/**
 * Created by 椰树 on 2016/5/22.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    //login = 0; register = 1; person = 2;

    private List<Fragment> mFragments;
    private FragmentManager mFragmentManager;

    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
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

