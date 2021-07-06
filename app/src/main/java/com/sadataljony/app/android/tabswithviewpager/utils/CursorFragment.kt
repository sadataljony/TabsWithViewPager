package com.sadataljony.app.android.tabswithviewpager.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.sadataljony.app.android.tabswithviewpager.R
import java.io.Serializable
import java.util.*

/**
 * Created by Sadat Al Jony on 06/07/2021. Email: sadataljony@gmail.com
 */
class CursorFragment : Fragment(), Serializable {
    protected var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null
    private var adapter: TabAdapter? = null
    var stackkk = Stack<Int>()
    var tabPosition = 0
    var fragments: ArrayList<Fragment>? = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_carousel, container, false)
        tabLayout = rootView.findViewById(R.id.tab_layout)
        viewPager = rootView.findViewById(R.id.pager)
        try {
            if (arguments != null) {
                val tabsList = requireArguments().getCharSequenceArrayList(Constants.TABS_NAME)
                for (i in tabsList!!.indices) {
                    with(tabLayout) { this?.addTab(newTab().setText(tabsList[i].toString())) }
                }
                fragments =
                    requireArguments().getSerializable(Constants.FRAGMENT_NAME) as ArrayList<Fragment>?
                requireArguments().clear()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = TabAdapter(
            childFragmentManager,
            tabLayout!!.tabCount,
            viewPager!!,
            fragments!!
        )
        viewPager!!.offscreenPageLimit = 1
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tabPosition = tab.position
                viewPager!!.currentItem = tab.position
                if (stackkk.empty()) stackkk.push(0)
                if (stackkk.contains(tabPosition)) {
                    stackkk.removeAt(stackkk.indexOf(tabPosition))
                    stackkk.push(tabPosition)
                } else {
                    stackkk.push(tabPosition)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    /**
     * Retrieve the currently visible Tab Fragment and propagate the onBackPressed callback
     *
     * @return true = if this fragment and/or one of its associates Fragment can handle the backPress
     */
    @SuppressLint("RestrictedApi")
    fun onBackPressed(context: Activity): Boolean {
        val currentFragment =
            adapter!!.getRegisteredFragment(viewPager!!.currentItem) as OnBackPressListener
        if (currentFragment != null) {
            if ((currentFragment as Fragment).childFragmentManager.fragments.size == 0) {
                if (stackkk.size > 1) {
                    stackkk.pop()
                    viewPager!!.currentItem = stackkk.lastElement()
                    return true
                } else {
                    context.finish()
                }
            } else {
                return currentFragment.onBackPressed()
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (arguments != null) {
            requireArguments().clear()
        }
    }
}