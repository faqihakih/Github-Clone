package com.akih.submission2bfaa.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.akih.submission2bfaa.FollowersFragment
import com.akih.submission2bfaa.FollowingFragment
import com.akih.submission2bfaa.R

class ViewPagerAdapter(private val context: Context, fragmentManager: FragmentManager, data: Bundle)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentBundle : Bundle
    init {
        fragmentBundle = data
    }
    @StringRes
    private val title = intArrayOf(R.string.tab_1, R.string.tab_2)

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FollowersFragment()
            else -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(title[position])
    }
}