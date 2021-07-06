package com.sadataljony.app.android.tabswithviewpager.utils

import androidx.fragment.app.Fragment
import java.io.Serializable

/**
 * Created by Sadat Al Jony on 06/07/2021. Email: sadataljony@gmail.com
 */
open class RootFragment : Fragment(), OnBackPressListener, Serializable {
    override fun onBackPressed(): Boolean {
        return BackPressImpl(this).onBackPressed()
    }
}