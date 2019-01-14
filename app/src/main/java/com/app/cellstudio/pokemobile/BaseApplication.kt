package com.app.cellstudio.pokemobile

import android.app.Application
import com.app.cellstudio.pokemobile.di.components.ApplicationComponent
import com.app.cellstudio.pokemobile.di.components.DaggerApplicationComponent
import com.app.cellstudio.pokemobile.di.modules.ApplicationModule
import com.app.cellstudio.pokemobile.presentation.util.image.BaseImageLoader
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class BaseApplication : Application() {

    private lateinit var applicationComponent: ApplicationComponent
    private val mLock = Any()

    override fun onCreate() {
        super.onCreate()
        synchronized(mLock) {
            singleton = this
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(ApplicationModule(this))
                    .build()
            applicationComponent.inject(this)
        }
        initializeSDKs()
    }

    private fun initializeSDKs() {
        BaseImageLoader.getInstance().init(this)
        Fabric.with(this, Crashlytics())
    }

    fun getApplicationComponent() : ApplicationComponent {
        return applicationComponent
    }

    companion object {
        // Singleton Instance
        private lateinit var singleton: BaseApplication

        fun getInstance() : BaseApplication { return singleton }
    }

}