package com.app.cellstudio.pokemobile.presentation.view.component

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class DisableSwipeViewPager : ViewPager {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, false)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }
}


