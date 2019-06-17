package com.singorenko.revoluttest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.singorenko.revoluttest.R
import com.singorenko.revoluttest.ui.adapter.MyPagerAdapter

class ExchangeListActivity : AppCompatActivity() {

    private  var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        configTabLayout()

        val adapter = MyPagerAdapter(supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter


        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager!!.currentItem = tab!!.position
            }
        })
    }

    private fun configTabLayout(){
        tabLayout!!.addTab(tabLayout!!.newTab().setText("All Rates"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Converter"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
    }
}
