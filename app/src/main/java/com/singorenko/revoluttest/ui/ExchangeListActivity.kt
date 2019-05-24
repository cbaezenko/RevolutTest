package com.singorenko.revoluttest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.singorenko.revoluttest.R

class ExchangeListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showExchangeListFragment()
    }

   private fun showExchangeListFragment(){
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, ExchangeListFragment.newInstance()).commit()
    }
}
