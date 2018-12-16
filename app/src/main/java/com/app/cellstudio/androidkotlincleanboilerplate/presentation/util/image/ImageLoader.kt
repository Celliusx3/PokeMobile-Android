package com.app.cellstudio.androidkotlincleanboilerplate.presentation.util.image

import android.content.Context
import android.graphics.Bitmap
import android.support.annotation.DrawableRes
import android.widget.ImageView

interface ImageLoader {
    fun isInitialized(): Boolean
    fun init(context: Context)
    fun displayImage(uri: String, imageView: ImageView?)
    fun displayImageOffline(uri: String, imageView: ImageView?)
    fun displayRawImage(uri: String, imageView: ImageView?, scaleType: ImageView.ScaleType)
    fun displayImage(uri: String, imageView: ImageView?, @DrawableRes resError: Int)
    fun loadImage(uri: String, listener: ImageLoadingListener)

    interface ImageLoadingListener {
        fun onSuccess(loadedImage: Bitmap)
        fun onFailure()
    }
}