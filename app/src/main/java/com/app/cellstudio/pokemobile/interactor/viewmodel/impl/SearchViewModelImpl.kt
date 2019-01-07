package com.app.cellstudio.pokemobile.interactor.viewmodel.impl

import androidx.databinding.ObservableBoolean
import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.pokemobile.interactor.viewmodel.SearchViewModel
import io.reactivex.Observable

class SearchViewModelImpl(private val pokemonTCGInteractor: PokemonTCGInteractor,
                          scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), SearchViewModel {

    private val isLoading = ObservableBoolean(false)

    override fun getSearchPokemonCards(keyword: String): Observable<List<PokemonTCGCard>> {
        isLoading.set(true)
        return pokemonTCGInteractor.searchPokemonTCGCards(keyword)
                .doFinally { isLoading.set(false) }
    }

    override fun getLoading(): ObservableBoolean {
        return isLoading
    }
}