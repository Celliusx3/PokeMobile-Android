package com.app.cellstudio.androidkotlincleanboilerplate.di.modules

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.PokemonTCGDetailsViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.impl.PokemonTCGDetailsViewModelImpl
import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
import dagger.Module
import dagger.Provides

@Module
class PokemonTCGDetailsModule {

    @Provides
    fun provideMovieDetailsViewModel(pokemonTCGInteractor: PokemonTCGInteractor,
                                     provider: BaseSchedulerProvider): PokemonTCGDetailsViewModel {
        return PokemonTCGDetailsViewModelImpl(pokemonTCGInteractor, provider)
    }
}