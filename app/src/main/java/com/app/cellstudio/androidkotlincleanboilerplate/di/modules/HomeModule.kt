package com.app.cellstudio.androidkotlincleanboilerplate.di.modules

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.HomeViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.impl.HomeViewModelImpl
import com.app.cellstudio.domain.interactor.MovieInteractor
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun provideHomeViewModel(movieInteractor: MovieInteractor,
                                      provider: BaseSchedulerProvider): HomeViewModel {
        return HomeViewModelImpl(movieInteractor, provider)
    }
}
