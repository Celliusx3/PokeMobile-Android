package com.app.cellstudio.pokemobile

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class BaseGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setResizeExecutor(GlideExecutor.newSourceExecutor(4, "glide-multi-executor", GlideExecutor.UncaughtThrowableStrategy.IGNORE))

        val diskCacheSize = 1024 * 1024 * 500.toLong()
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSize))
    }
}