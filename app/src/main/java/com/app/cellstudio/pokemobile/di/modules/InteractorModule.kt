package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.pokemobile.data.repository.ConfigRepository
import com.app.cellstudio.pokemobile.data.repository.OfflineRepository
import com.app.cellstudio.pokemobile.data.repository.PokemonTCGRepository
import com.app.cellstudio.pokemobile.domain.interactor.ConfigInteractor
import com.app.cellstudio.pokemobile.domain.interactor.MainInteractor
import com.app.cellstudio.pokemobile.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.domain.interactor.impl.ConfigInteractorImpl
import com.app.cellstudio.pokemobile.domain.interactor.impl.MainInteractorImpl
import com.app.cellstudio.pokemobile.domain.interactor.impl.PokemonTCGInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides
    @Singleton
    fun providePokemonTCGInteractor(pokemonTCGRepository: PokemonTCGRepository, offlineRepository: OfflineRepository): PokemonTCGInteractor {
        return PokemonTCGInteractorImpl(pokemonTCGRepository, offlineRepository)
    }

    @Provides
    @Singleton
    fun provideMainInteractor(): MainInteractor {
        return MainInteractorImpl()
    }

    @Provides
    @Singleton
    fun provideConfigInteractor(configRepository: ConfigRepository): ConfigInteractor {
        return ConfigInteractorImpl(configRepository)
    }
}
