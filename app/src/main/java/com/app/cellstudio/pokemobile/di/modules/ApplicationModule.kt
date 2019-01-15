package com.app.cellstudio.pokemobile.di.modules

import android.content.Context
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.SchedulerProvider
import com.app.cellstudio.pokemobile.data.environment.BaseEnvironment
import com.app.cellstudio.pokemobile.data.environment.Environment
import com.app.cellstudio.pokemobile.data.http.BaseHttpClient
import com.app.cellstudio.pokemobile.data.http.HttpClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val mApplication: BaseApplication) {

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
    fun provideEnvironment(context: Context): Environment {
        return BaseEnvironment(context)
    }
}