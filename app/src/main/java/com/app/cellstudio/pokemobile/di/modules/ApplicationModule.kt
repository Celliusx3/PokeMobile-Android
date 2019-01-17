package com.app.cellstudio.pokemobile.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.data.environment.BaseEnvironment
import com.app.cellstudio.pokemobile.data.environment.Environment
import com.app.cellstudio.pokemobile.data.http.BaseHttpClient
import com.app.cellstudio.pokemobile.data.http.HttpClient
import com.app.cellstudio.pokemobile.data.pref.BasePref
import com.app.cellstudio.pokemobile.data.pref.impl.BaseSharedPref
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val mApplication: BaseApplication) {

    private var mPrefs: SharedPreferences ?= null

    @Provides
    @Singleton
    fun provideApplication(): BaseApplication {
        return mApplication
    }

    @Provides
    @Singleton
    fun provideApplicationContext(baseApplication: BaseApplication): Context {
        return baseApplication
    }

    @Provides
    @Singleton
    fun provideScheduler(): BaseSchedulerProvider {
        return SchedulerProvider.getInstance()
    }

    @Provides
    @Singleton
    fun provideHttpClient(environment: Environment): HttpClient {
        return BaseHttpClient(environment)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        if (mPrefs == null) {
            val key = context.packageName ?: throw NullPointerException("Prefs key may not be null")
            mPrefs = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        }
        return mPrefs!!
    }

    @Provides
    @Singleton
    fun providePref(preferences: SharedPreferences): BasePref {
        return BaseSharedPref(preferences)
    }

    @Provides
    @Singleton
    fun provideEnvironment(context: Context): Environment {
        return BaseEnvironment(context)
    }
}