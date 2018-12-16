package com.app.cellstudio.androidkotlincleanboilerplate.di.modules

import com.app.cellstudio.domain.interactor.MainInteractor
import com.app.cellstudio.domain.interactor.MovieInteractor
import com.app.cellstudio.domain.interactor.impl.MainInteractorImpl
import com.app.cellstudio.domain.interactor.impl.MovieInteractorImpl
import com.app.cellstudio.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides
    @Singleton
    fun provideMovieInteractor(movieRepository: MovieRepository): MovieInteractor {
        return MovieInteractorImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun provideMainInteractor(): MainInteractor {
        return MainInteractorImpl()
    }
}
