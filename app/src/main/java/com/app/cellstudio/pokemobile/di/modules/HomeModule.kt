package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.pokemobile.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.interactor.viewmodel.HomeViewModel
import com.app.cellstudio.pokemobile.interactor.viewmodel.impl.HomeViewModelImpl
import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
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
