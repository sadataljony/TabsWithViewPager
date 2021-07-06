package com.sadataljony.app.android.tabswithviewpager.utils

import androidx.fragment.app.Fragment

/**
 * Created by Sadat Al Jony on 06/07/2021. Email: sadataljony@gmail.com
 */
class BackPressImpl(private val parentFragment: Fragment?) : OnBackPressListener {
    override fun onBackPressed(): Boolean {
        if (parentFragment == null) return false
        val childCount = parentFragment.childFragmentManager.backStackEntryCount
        return if (childCount == 0) {
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
            false
        } else {
            // get the child Fragment
            val childFragmentManager = parentFragment.childFragmentManager
            val childFragment = childFragmentManager.fragments[0] as OnBackPressListener

            // propagate onBackPressed method call to the child Fragment
            if (!childFragment.onBackPressed()) {
                // child Fragment was unable to handle the task
                // It could happen when the child Fragment is last last leaf of a chain
                // removing the child Fragment from stack
                childFragmentManager.popBackStackImmediate()
                childFragmentManager.beginTransaction().commit()
                try {
                    val backStackEntryCount = childFragmentManager.backStackEntryCount - 1
                    if (backStackEntryCount >= 0) childFragmentManager.fragments[backStackEntryCount].onResume()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            // either this Fragment or its child handled the task
            // either way we are successful and done here
            true
        }
    }
}