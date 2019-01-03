package com.app.cellstudio.pokemobile.interactor.viewmodel.impl

import androidx.databinding.ObservableBoolean
import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.pokemobile.interactor.viewmodel.PokemonTCGDetailsViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PokemonTCGDetailsViewModelImpl(private val pokemonTCGInteractor: PokemonTCGInteractor,
                                     scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), PokemonTCGDetailsViewModel {
    private val paginationLoading = PublishSubject.create<Boolean>()
    private val initialLoading = ObservableBoolean(false)

    override fun getInitialLoading(): ObservableBoolean {
        return initialLoading
    }

    override fun getPaginationLoading(): Observable<Boolean> {
        return paginationLoading
    }

    override fun getPokemonTCGCards(set: String, page: Int): Observable<List<PokemonTCGCard>> {
        // Is Initial Page
        if (page <= 1) {
            initialLoading.set(true)
        }

        paginationLoading.onNext(true)
        return pokemonTCGInteractor.getPokemonTCGCards(set, page)
                .doFinally { initialLoading.set(false)
                    paginationLoading.onNext(false) }
    }
}
