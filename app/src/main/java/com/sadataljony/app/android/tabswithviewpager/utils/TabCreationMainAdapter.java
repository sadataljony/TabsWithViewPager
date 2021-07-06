package com.sadataljony.app.android.tabswithviewpager.utils;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

/**
 * Created by Sadat Al Jony on 06/07/2021. Email: sadataljony@gmail.com
 */
public class TabCreationMainAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;
    private ViewPager viewPager;
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private ArrayList<Fragment> fragments;

    public TabCreationMainAdapter(FragmentManager fm, int NumOfTabs, ViewPager viewPager, ArrayList<Fragment> fragments) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.viewPager = viewPager;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        StaticValueParser.viewPagerOvertime = viewPager;
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    /**
     * On each Fragment instantiation we are saving the reference of that Fragment in a Map
     * It will help us to retrieve the Fragment by position
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    /**
     * Remove the saved reference from our Map on the Fragment destroy
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }


    /**
     * Get the Fragment by position
     *
     * @param position tab position of the fragment
     * @return
     */
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}