package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.data.http.HttpClient
import com.app.cellstudio.data.repository.PokemonTCGRepositoryImpl
import com.app.cellstudio.domain.repository.PokemonTCGRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonTCGRepository(httpClient: HttpClient): PokemonTCGRepository {
        return PokemonTCGRepositoryImpl(httpClient)
    }
}