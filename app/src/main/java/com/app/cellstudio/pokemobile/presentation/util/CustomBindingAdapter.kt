package com.app.cellstudio.androidkotlincleanboilerplate.presentation.util

import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.util.image.BaseImageLoader

@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("invisible")
fun setInvisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("android:src")
fun setImageUri(view: ImageView, imageUri: String) {
    BaseImageLoader.getInstance().displayImage(imageUri, view)
}

@BindingAdapter("srcOffline")
fun setImageUriOffline(view: ImageView, imageUri: String) {
    BaseImageLoader.getInstance().displayImageOffline(imageUri, view)
}

@BindingAdapter("android:src")
fun setImageDrawable(view: ImageView, drawable: Drawable) {
    view.setImageDrawable(drawable)
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("src_rawImage")
fun setImageResourceFitCenter(imageView: ImageView, resource: String) {
    BaseImageLoader.getInstance().displayRawImage(resource, imageView, imageView.scaleType)
}

@BindingAdapter("textWatcher")
fun bindTextWatcher(view: View, textWatcher: TextWatcher) {
    if (view is EditText) {
        view.addTextChangedListener(textWatcher)
    }
}