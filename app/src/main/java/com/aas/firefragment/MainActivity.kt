package com.aas.firefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aas.firefragment.utils.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager.adapter = PagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewpager)
    }
}
