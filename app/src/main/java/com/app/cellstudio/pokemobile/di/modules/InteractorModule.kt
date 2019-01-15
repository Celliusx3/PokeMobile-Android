package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.pokemobile.domain.interactor.MainInteractor
import com.app.cellstudio.pokemobile.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.domain.interactor.impl.MainInteractorImpl
import com.app.cellstudio.pokemobile.domain.interactor.impl.PokemonTCGInteractorImpl
import com.app.cellstudio.pokemobile.data.repository.PokemonTCGRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides
    @Singleton
    fun providePokemonTCGInteractor(pokemonTCGRepository: PokemonTCGRepository): PokemonTCGInteractor {
        return PokemonTCGInteractorImpl(pokemonTCGRepository)
    }

    @Provides
    @Singleton
    fun provideMainInteractor(): MainInteractor {
        return MainInteractorImpl()
    }
}
