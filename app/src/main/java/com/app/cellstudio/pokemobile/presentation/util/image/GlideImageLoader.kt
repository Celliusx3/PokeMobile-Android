package com.app.cellstudio.pokemobile.presentation.util.image

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.util.image.ImageLoader
import com.app.cellstudio.pokemobile.GlideApp
import com.app.cellstudio.pokemobile.R
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class GlideImageLoader : ImageLoader {

    private var context: Context? = null

    @DrawableRes
    private val resPlaceholder = R.drawable.ic_image_default_24dp

    @DrawableRes
    private val resError = R.drawable.ic_broken_image_24dp

    override fun init(context: Context) {
        this.context = context
    }

    override fun isInitialized(): Boolean {
        return context != null
    }

    override fun displayImage(uri: String, imageView: ImageView?) {

        if (!isInitialized() || imageView == null) {
            return
        }

        val tempUri = if (TextUtils.isEmpty(uri)) null else uri

        imageView.scaleType = ImageView.ScaleType.CENTER
        GlideApp.with(context!!)
                .load(tempUri)
                .fitCenter()
                .placeholder(resPlaceholder)
                .error(resError)
                .listener(object: RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        imageView.scaleType = ImageView.ScaleType.FIT_XY
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(imageView)
    }

    override fun displayRawImage(uri: String, imageView: ImageView?, scaleType: ImageView.ScaleType) {
        if (!isInitialized() || imageView == null) {
            return
        }

        val tempUri = if (TextUtils.isEmpty(uri)) null else uri
        imageView.scaleType = ImageView.ScaleType.CENTER

        GlideApp.with(context!!)
                .load(tempUri)
                .placeholder(resPlaceholder)
                .error(resError)
                .listener(object: RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        imageView.scaleType = scaleType//def=fitCentre
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(imageView)
    }
}