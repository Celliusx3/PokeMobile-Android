package com.app.cellstudio.androidkotlincleanboilerplate.di.modules

import com.app.cellstudio.data.http.HttpClient
import com.app.cellstudio.data.repository.MovieRepositoryImpl
import com.app.cellstudio.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(httpClient: HttpClient): MovieRepository {
        return MovieRepositoryImpl(httpClient)
    }
}