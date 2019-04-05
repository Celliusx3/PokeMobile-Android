package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.impl

import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.DownloadViewModel
import io.reactivex.Observable

class DownloadViewModelImpl(private val pokemonTCGInteractor: PokemonTCGInteractor,
                        scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), DownloadViewModel {
    override fun getAllPokemonTCGCardsInASet(set: String): Observable<List<PokemonTCGCard>> {
        return pokemonTCGInteractor.getAllPokemonTCGCards(set, true)
    }
}