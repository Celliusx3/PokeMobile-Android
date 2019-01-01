package com.app.cellstudio.androidkotlincleanboilerplate.di.modules

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.MainViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.impl.MainViewModelImpl
import com.app.cellstudio.domain.interactor.MainInteractor
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainViewModel(mainInteractor: MainInteractor, provider: BaseSchedulerProvider): MainViewModel {
        return MainViewModelImpl(mainInteractor, provider)
    }
}