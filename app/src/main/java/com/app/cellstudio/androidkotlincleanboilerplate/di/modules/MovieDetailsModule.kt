package com.app.cellstudio.androidkotlincleanboilerplate.di.modules

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.MovieDetailsViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.impl.MovieDetailsViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class MovieDetailsModule {

    @Provides
    fun provideMovieDetailsViewModel(provider: BaseSchedulerProvider): MovieDetailsViewModel {
        return MovieDetailsViewModelImpl(provider)
    }
}