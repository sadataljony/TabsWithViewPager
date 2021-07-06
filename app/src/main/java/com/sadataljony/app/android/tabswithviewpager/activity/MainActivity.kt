package com.sadataljony.app.android.tabswithviewpager.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.sadataljony.app.android.tabswithviewpager.R
import com.sadataljony.app.android.tabswithviewpager.fragment.FragmentTwo
import com.sadataljony.app.android.tabswithviewpager.fragment.FragmentOne
import com.sadataljony.app.android.tabswithviewpager.fragment.FragmentThree
import com.sadataljony.app.android.tabswithviewpager.utils.Constants
import com.sadataljony.app.android.tabswithviewpager.utils.CursorFragment
import java.util.*

/**
 * Created by Sadat Al Jony on 06/07/2021. Email: sadataljony@gmail.com
 */
class MainActivity : AppCompatActivity() {

    private var carouselFragment: CursorFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeFragments(savedInstanceState)
        setToolbar()
    }

    private fun initializeFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            initScreen()
        } else {
            carouselFragment = supportFragmentManager.fragments[0] as CursorFragment
        }
    }

    private fun initScreen() {
        carouselFragment = CursorFragment()
        val fragmentManager = supportFragmentManager
        val bundle = Bundle()
        val tabsList = ArrayList<CharSequence>()
        tabsList.add("ONE")
        tabsList.add("TWO")
        tabsList.add("THREE")
        bundle.putCharSequenceArrayList(Constants.TABS_NAME, tabsList)
        sendFragments(bundle)
        carouselFragment!!.arguments = bundle
        fragmentManager.beginTransaction()
            .replace(R.id.container, carouselFragment!!)
            .commit()
    }

    private fun sendFragments(bundle: Bundle) {
        val fragment01 = FragmentOne()
        val fragment02 = FragmentTwo()
        val fragment03 = FragmentThree()
        val fragments = ArrayList<Fragment>()
        fragments.add(fragment01)
        fragments.add(fragment02)
        fragments.add(fragment03)
        bundle.putSerializable(Constants.FRAGMENT_NAME, fragments)
    }

    private fun setToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { v: View? ->
            if (!carouselFragment!!.onBackPressed(
                    this
                )
            ) {
            } else {
            }
        }
    }
}