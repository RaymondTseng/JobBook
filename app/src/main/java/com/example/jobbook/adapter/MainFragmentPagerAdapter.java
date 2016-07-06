package com.example.jobbook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.jobbook.fragment.LoginFragment;
import com.example.jobbook.fragment.PersonFragment;

import java.util.List;

/**
 * Created by 椰树 on 2016/5/22.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private FragmentManager mFragmentManager;
    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        mFragmentManager = fm;
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

    public void toPersonFragment(){
        mFragmentManager.beginTransaction().remove(mFragments.get(3)).commit();
        mFragments.set(3,  new PersonFragment());
        notifyDataSetChanged();
    }
}

