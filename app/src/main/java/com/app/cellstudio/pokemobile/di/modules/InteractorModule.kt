package com.app.cellstudio.androidkotlincleanboilerplate.di.modules

import com.app.cellstudio.domain.interactor.MainInteractor
import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.domain.interactor.impl.MainInteractorImpl
import com.app.cellstudio.domain.interactor.impl.PokemonTCGInteractorImpl
import com.app.cellstudio.domain.repository.PokemonTCGRepository
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
