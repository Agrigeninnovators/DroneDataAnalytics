package com.example.agis

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.agis.ui.main.PlaceholderFragment

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MainFragment()
            else-> ChartFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Farm"
            1 -> "Stats"
            else -> null
        }
    }
}
