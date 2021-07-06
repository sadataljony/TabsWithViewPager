package com.sadataljony.app.android.tabswithviewpager.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


/**
 * Created by Sadat Al Jony on 06/07/2021. Email: sadataljony@gmail.com
 */
public class BackPressImpl implements OnBackPressListener {

    private final Fragment parentFragment;

    public BackPressImpl(Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    @Override
    public boolean onBackPressed() {

        if (parentFragment == null) return false;

        int childCount = parentFragment.getChildFragmentManager().getBackStackEntryCount();

        if (childCount == 0) {
//            if (CarouselFragment.stackkk.size() > 1) {
//                CarouselFragment.stackkk.pop();
//                CarouselFragment.getViewPager().setCurrentItem(CarouselFragment.stackkk.lastElement());
//                return true;
//            } else {
//                Intent intent = new Intent(parentFragment.getContext(), HomeActivity.class);
//                parentFragment.getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
//                parentFragment.getActivity().startActivity(intent);
//                return true;
//            }
            return false;
        } else {
            // get the child Fragment
            FragmentManager childFragmentManager = parentFragment.getChildFragmentManager();
            OnBackPressListener childFragment = (OnBackPressListener) childFragmentManager.getFragments().get(0);

            // propagate onBackPressed method call to the child Fragment
            if (!childFragment.onBackPressed()) {
                // child Fragment was unable to handle the task
                // It could happen when the child Fragment is last last leaf of a chain
                // removing the child Fragment from stack
                childFragmentManager.popBackStackImmediate();
                childFragmentManager.beginTransaction().commit();
                try {
                    int backStackEntryCount = childFragmentManager.getBackStackEntryCount() - 1;
                    if (backStackEntryCount >= 0)
                        childFragmentManager.getFragments().get(backStackEntryCount).onResume();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // either this Fragment or its child handled the task
            // either way we are successful and done here
            return true;
        }
    }
}
