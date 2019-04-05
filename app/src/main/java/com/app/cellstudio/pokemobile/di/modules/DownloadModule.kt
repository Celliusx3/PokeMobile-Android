package com.app.cellstudio.pokemobile.di.modules

import com.app.cellstudio.pokemobile.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.DownloadViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.impl.DownloadViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class DownloadModule {

    @Provides
    fun provideDownloadViewModel(pokemonTCGInteractor: PokemonTCGInteractor,
                             provider: BaseSchedulerProvider): DownloadViewModel {
        return DownloadViewModelImpl(pokemonTCGInteractor, provider)
    }
}
