package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.interactor.viewmodel.SearchViewModel
import com.app.cellstudio.pokemobile.interactor.viewmodel.impl.SearchViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    fun provideSearchViewModel(pokemonTCGInteractor: PokemonTCGInteractor, provider: BaseSchedulerProvider): SearchViewModel {
        return SearchViewModelImpl(pokemonTCGInteractor, provider)
    }
}