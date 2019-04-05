package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.pokemobile.data.db.AppDatabase
import com.app.cellstudio.pokemobile.data.environment.Environment
import com.app.cellstudio.pokemobile.data.http.HttpClient
import com.app.cellstudio.pokemobile.data.pref.BasePref
import com.app.cellstudio.pokemobile.data.repository.ConfigRepository
import com.app.cellstudio.pokemobile.data.repository.OfflineRepository
import com.app.cellstudio.pokemobile.data.repository.PokemonTCGRepository
import com.app.cellstudio.pokemobile.data.repository.impl.ConfigRepositoryImpl
import com.app.cellstudio.pokemobile.data.repository.impl.OfflineRepositoryImpl
import com.app.cellstudio.pokemobile.data.repository.impl.PokemonTCGRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonTCGRepository(httpClient: HttpClient, pref: BasePref, environment: Environment): PokemonTCGRepository {
        return PokemonTCGRepositoryImpl(httpClient, pref, environment)
    }

    @Provides
    @Singleton
    fun provideConfigRepository(pref: BasePref): ConfigRepository {
        return ConfigRepositoryImpl(pref)
    }

    @Provides
    @Singleton
    fun provideOfflineRepository(database: AppDatabase): OfflineRepository {
        return OfflineRepositoryImpl(database)
    }
}