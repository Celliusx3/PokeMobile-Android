package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.pokemobile.data.http.HttpClient
import com.app.cellstudio.pokemobile.data.repository.PokemonTCGRepository
import com.app.cellstudio.pokemobile.data.repository.impl.PokemonTCGRepositoryImpl
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