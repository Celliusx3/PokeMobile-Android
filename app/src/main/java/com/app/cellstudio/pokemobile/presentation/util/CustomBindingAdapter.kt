package com.app.cellstudio.pokemobile.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.app.cellstudio.pokemobile.presentation.util.image.BaseImageLoader

@BindingAdapter("android:src")
fun setImageUri(view: ImageView, imageUri: String) {
    BaseImageLoader.getInstance().displayImage(imageUri, view)
}

@BindingAdapter("src_imageFitStart")
fun setImageResourceFitStart(imageView: ImageView, resource: String) {
    BaseImageLoader.getInstance().displayRawImage(resource, imageView, ImageView.ScaleType.FIT_START)
}
