package com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.fragment.HomeFragment
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.fragment.SettingsFragment
import com.app.cellstudio.domain.entity.Page
import java.util.*

class MainPagerAdapter(fragmentManager: FragmentManager,
                       private val fragmentPages: List<Page>) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragmentPageIds = ArrayList<Int>()

    private var homeFragment: HomeFragment? = null
    private var settingsFragment: SettingsFragment? = null

    init {

        fragmentPageIds.clear()
        for (page in fragmentPages) {
            fragmentPageIds.add(page.pageId)
        }
    }

    override fun getItem(position: Int): Fragment {
        val fragmentPage = fragmentPages[position]

        when (fragmentPage) {
            Page.HomePage -> {
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance()
                }
                return homeFragment as HomeFragment
            }
            Page.SettingsPage -> {
                if (settingsFragment == null) {
                    settingsFragment = SettingsFragment.newInstance()
                }
                return settingsFragment as SettingsFragment
            }
            else -> {
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance()
                }
                return homeFragment as HomeFragment
            }
        }
    }

    override fun getCount(): Int {
        return fragmentPages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentPages[position].title
    }

    fun getPagePositionById(pageId: Int): Int {
        return fragmentPageIds.indexOf(pageId)
    }
}