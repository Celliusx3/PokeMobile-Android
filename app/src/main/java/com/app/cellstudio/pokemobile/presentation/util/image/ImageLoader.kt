package com.app.cellstudio.androidkotlincleanboilerplate.presentation.util.image

import android.content.Context
import android.widget.ImageView

interface ImageLoader {
    fun isInitialized(): Boolean
    fun init(context: Context)
    fun displayImage(uri: String, imageView: ImageView?)
    fun displayRawImage(uri: String, imageView: ImageView?, scaleType: ImageView.ScaleType)
}