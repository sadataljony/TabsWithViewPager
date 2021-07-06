package com.sadataljony.app.android.tabswithviewpager.utils

import androidx.viewpager.widget.ViewPager
import androidx.fragment.app.FragmentStatePagerAdapter
import android.util.SparseArray
import com.sadataljony.app.android.tabswithviewpager.utils.StaticValueParser
import androidx.viewpager.widget.PagerAdapter
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.ArrayList

/**
 * Created by Sadat Al Jony on 06/07/2021. Email: sadataljony@gmail.com
 */
class TabAdapter(
    fm: FragmentManager?,
    private val mNumOfTabs: Int,
    private val viewPager: ViewPager,
    private val fragments: ArrayList<Fragment>
) : FragmentStatePagerAdapter(
    fm!!
) {
    private val registeredFragments = SparseArray<Fragment>()
    override fun getItem(position: Int): Fragment {
        StaticValueParser.viewPagerOvertime = viewPager
        return fragments[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }

    /**
     * On each Fragment instantiation we are saving the reference of that Fragment in a Map
     * It will help us to retrieve the Fragment by position
     *
     * @param container
     * @param position
     * @return
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    /**
     * Remove the saved reference from our Map on the Fragment destroy
     *
     * @param container
     * @param position
     * @param object
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    /**
     * Get the Fragment by position
     *
     * @param position tab position of the fragment
     * @return
     */
    fun getRegisteredFragment(position: Int): Fragment {
        return registeredFragments[position]
    }
}