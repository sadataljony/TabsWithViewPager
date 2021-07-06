package com.sadataljony.app.android.tabswithviewpager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sadataljony.app.android.tabswithviewpager.databinding.FragmentOneBinding
import com.sadataljony.app.android.tabswithviewpager.utils.RootFragment

/**
 * Created by Sadat Al Jony on 06/07/2021. Email: sadataljony@gmail.com
 */
class FragmentOne : RootFragment() {
    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root
    }
}