package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.pokemobile.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.SplashViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.impl.SplashViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @Provides
    fun provideSplashViewModel(pokemonTCGInteractor: PokemonTCGInteractor, provider: BaseSchedulerProvider): SplashViewModel {
        return SplashViewModelImpl(pokemonTCGInteractor, provider)
    }
}