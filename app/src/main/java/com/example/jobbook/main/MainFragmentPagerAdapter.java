package com.example.jobbook.main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


import com.example.jobbook.login.widget.LoginFragment;
import com.example.jobbook.person.widget.PersonFragment;
import com.example.jobbook.register.widget.RegisterFragment;

import java.util.List;

/**
 * Created by 椰树 on 2016/5/22.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

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
        Log.i("TAG", "Call");
        return POSITION_NONE;
    }

    public void toPersonFragment(){
        mFragmentManager.beginTransaction().remove(mFragments.get(3)).commit();
        mFragments.set(3,  new PersonFragment());
        notifyDataSetChanged();
    }

    public void toRegisterFragment() {
        mFragmentManager.beginTransaction().remove(mFragments.get(3)).commit();
        mFragments.set(3,  new RegisterFragment());
        notifyDataSetChanged();
    }

    public void toLoginFragment() {
        mFragmentManager.beginTransaction().remove(mFragments.get(3)).commit();
        mFragments.set(3,  new LoginFragment());
        notifyDataSetChanged();
    }

}

