package com.example.e_7_5;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BillPagerAdapter extends FragmentPagerAdapter {

    private String[] titles;

    public BillPagerAdapter(@NonNull FragmentManager fm, String[] titles) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return BillFragment.newInstance(position + 1); // 月份从1开始
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
