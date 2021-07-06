package com.sadataljony.app.android.tabswithviewpager.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sadataljony.app.android.tabswithviewpager.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;


/**
 * Created by Sadat Al Jony on 06/07/2021. Email: sadataljony@gmail.com
 */
public class CursorFragment extends Fragment implements Serializable {
    protected ViewPager viewPager;
    private TabLayout tabLayout;
    private TabCreationMainAdapter adapter;
    public Stack<Integer> stackkk = new Stack<>();
    int tabPosition = 0;
    ArrayList<Fragment> fragments = new ArrayList<>();

    public CursorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carousel, container, false);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.pager);

        try {

            if (getArguments() != null) {
                ArrayList<CharSequence> tabsList = getArguments().getCharSequenceArrayList(ConstName.TABS_NAME);
                for (int i = 0; i < tabsList.size(); i++) {
                    tabLayout.addTab(tabLayout.newTab().setText(tabsList.get(i).toString()));
                }
                fragments = (ArrayList<Fragment>) getArguments().getSerializable(ConstName.FRAGMENT_NAME);
                getArguments().clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new TabCreationMainAdapter(getChildFragmentManager(), tabLayout.getTabCount(), viewPager, fragments);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
                if (stackkk.empty())
                    stackkk.push(0);

                if (stackkk.contains(tabPosition)) {
                    stackkk.remove(stackkk.indexOf(tabPosition));
                    stackkk.push(tabPosition);
                } else {
                    stackkk.push(tabPosition);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Retrieve the currently visible Tab Fragment and propagate the onBackPressed callback
     *
     * @return true = if this fragment and/or one of its associates Fragment can handle the backPress
     */
    @SuppressLint("RestrictedApi")
    public boolean onBackPressed(Activity context) {
        OnBackPressListener currentFragment = (OnBackPressListener) adapter.getRegisteredFragment(viewPager.getCurrentItem());

        if (currentFragment != null) {
            if (((Fragment) currentFragment).getChildFragmentManager().getFragments().size() == 0) {
                if (stackkk.size() > 1) {
                    stackkk.pop();
                    viewPager.setCurrentItem(stackkk.lastElement());
                    return true;
                } else {
                    context.finish();
                }
            } else {
                return currentFragment.onBackPressed();
            }
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getArguments() != null) {
            getArguments().clear();
        }
    }
}
