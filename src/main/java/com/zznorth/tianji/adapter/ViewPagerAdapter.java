package com.zznorth.tianji.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * coder by 中资北方 on 2015/12/3.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{

    private final List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //禁止懒加载的Fragment被销毁
        //super.destroyItem(container, position, object);
    }
}
