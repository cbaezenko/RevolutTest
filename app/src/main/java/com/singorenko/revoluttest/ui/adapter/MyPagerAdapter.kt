package com.singorenko.revoluttest.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.singorenko.revoluttest.ui.ExchangeListFragment
import com.singorenko.revoluttest.ui.ListValuesFragment

class MyPagerAdapter(fm: FragmentManager, private var totalTabs: Int) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> {
              return ListValuesFragment.newInstance()
            }
            1 -> {
               return ExchangeListFragment.newInstance()
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}