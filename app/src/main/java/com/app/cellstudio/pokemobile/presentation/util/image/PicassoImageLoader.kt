package com.app.cellstudio.pokemobile.presentation.util.image

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.app.cellstudio.pokemobile.R
import com.squareup.picasso.Callback
import com.squareup.picasso.LruCache
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class PicassoImageLoader : ImageLoader {

    private var context: Context? = null

    @DrawableRes
    private val resPlaceholder = R.drawable.ic_image_default_24dp

    @DrawableRes
    private val resError = R.drawable.ic_broken_image_24dp

    override fun init(context: Context) {
        this.context = context

        val builder = Picasso.Builder(context)
        builder.memoryCache(LruCache(context))

        Picasso.setSingletonInstance(builder.build())
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
        Picasso.with(context)
                .load(tempUri)
                .fit()
                .placeholder(resPlaceholder)
                .noFade()
                .error(resError)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        imageView.scaleType = ImageView.ScaleType.FIT_XY
                    }

                    override fun onError() {
                    }
                })
    }

    override fun displayImageOffline(uri: String, imageView: ImageView?) {
        if (!isInitialized() || imageView == null) {
            return
        }

        val tempUri = if (TextUtils.isEmpty(uri)) null else uri

        imageView.scaleType = ImageView.ScaleType.CENTER
        Picasso.with(context)
                .load(tempUri)
                .fit()
                .placeholder(resPlaceholder)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .noFade()
                .error(resError)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        imageView.scaleType = ImageView.ScaleType.FIT_XY
                    }

                    override fun onError() {
                        Picasso.with(context)
                                .load(uri)
                                .fit()
                                .noFade()
                                .into(imageView, object : Callback {
                                    override fun onSuccess() {
                                        imageView.scaleType = ImageView.ScaleType.FIT_XY
                                    }

                                    override fun onError() {

                                    }
                                })
                    }
                })
    }

    override fun displayRawImage(uri: String, imageView: ImageView?, scaleType: ImageView.ScaleType) {
        if (!isInitialized() || imageView == null) {
            return
        }

        val tempUri = if (TextUtils.isEmpty(uri)) null else uri
        imageView.scaleType = ImageView.ScaleType.CENTER

        Picasso.with(context)
                .load(tempUri)
                .placeholder(resPlaceholder)
                .noFade()
                .error(resError)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        imageView.scaleType = scaleType//def=fitCentre
                    }

                    override fun onError() {}
                })
    }

    override fun displayImage(uri: String, imageView: ImageView?, @DrawableRes resError: Int) {
        if (!isInitialized() || imageView == null) {
            return
        }

        val tempUri = if (TextUtils.isEmpty(uri)) null else uri
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        Picasso.with(context)
                .load(tempUri)
                .fit()
                .centerCrop()
                .placeholder(resPlaceholder)
                .noFade()
                .error(resError)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    }

                    override fun onError() {
                        Picasso.with(context).load(resError).fit().centerCrop().noFade().into(imageView)
                    }
                })
    }

    override fun loadImage(uri: String, listener: ImageLoader.ImageLoadingListener) {
        if (!isInitialized()) {
            return
        }

        val tempUri = if (TextUtils.isEmpty(uri)) null else uri

        val requestCreator = Picasso.with(context).load(tempUri)
        requestCreator.fetch(object : Callback {
            override fun onSuccess() {
                Observable.just(requestCreator)
                        .subscribeOn(Schedulers.io())
                        .subscribe { o ->
                            try {
                                listener.onSuccess(o.get())
                            } catch (e: IOException) {
                                listener.onFailure()
                            }
                        }
            }

            override fun onError() {
                listener.onFailure()
            }
        })
    }
}