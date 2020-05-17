package com.aas.firefragment.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aas.firefragment.ui.create.CreateFragment
import com.aas.firefragment.ui.get.GetFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val pages = listOf(
        GetFragment(), CreateFragment()
    )

    override fun getItem(position: Int): Fragment = pages[position]

    override fun getCount(): Int = pages.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Get Employees"
            1 -> "Create Employees"
            else -> "Update Employees"
        }
    }
}