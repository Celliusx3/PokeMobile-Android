package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.interactor.viewmodel.PokemonTCGDetailsViewModel
import com.app.cellstudio.pokemobile.interactor.viewmodel.impl.PokemonTCGDetailsViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class PokemonTCGDetailsModule {

    @Provides
    fun providePokemonTCGDetailsViewModel(pokemonTCGInteractor: PokemonTCGInteractor,
                                     provider: BaseSchedulerProvider): PokemonTCGDetailsViewModel {
        return PokemonTCGDetailsViewModelImpl(pokemonTCGInteractor, provider)
    }
}