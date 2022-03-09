package com.downloader.classes

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class Navigator {
    companion object {
        fun loadFragment(
            activity: FragmentActivity?,
            baseFragment: Fragment?,
            containerId: Int,
            isStacked: Boolean
        ) {
            if (!isStacked)
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(containerId, baseFragment!!)
                    .commitAllowingStateLoss()
            else
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(containerId, baseFragment!!).addToBackStack("").commit()
        }
    }
}