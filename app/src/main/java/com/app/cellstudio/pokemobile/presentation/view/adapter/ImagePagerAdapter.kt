package com.app.cellstudio.pokemobile.presentation.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.app.cellstudio.pokemobile.presentation.util.image.BaseImageLoader
import com.github.chrisbanes.photoview.PhotoView

class ImagePagerAdapter(private val imageUrls: List<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val photoView = PhotoView(container.context)
        photoView.setPadding(16,16,16,16)

        BaseImageLoader.getInstance().displayRawImage(imageUrls[position], photoView, ImageView.ScaleType.FIT_CENTER)

        // Now just add PhotoView to ViewPager and return it
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return photoView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}