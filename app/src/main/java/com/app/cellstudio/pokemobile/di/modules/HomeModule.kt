package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.HomeViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.impl.HomeViewModelImpl
import com.app.cellstudio.pokemobile.domain.interactor.PokemonTCGInteractor
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun provideHomeViewModel(pokemonTCGInteractor: PokemonTCGInteractor,
                             provider: BaseSchedulerProvider): HomeViewModel {
        return HomeViewModelImpl(pokemonTCGInteractor, provider)
    }
}
