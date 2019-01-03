package com.app.cellstudio.pokemobile.interactor.viewmodel.impl

import androidx.databinding.ObservableBoolean
import com.app.cellstudio.domain.entity.PokemonTCGSet
import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.pokemobile.interactor.viewmodel.HomeViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class HomeViewModelImpl(private val pokemonTCGInteractor: PokemonTCGInteractor,
                        scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), HomeViewModel {

    private val paginationLoading = PublishSubject.create<Boolean>()
    private val initialLoading = ObservableBoolean(false)

    override fun getPokemonTCGSets(page: Int): Observable<List<PokemonTCGSet>> {
        // Is Initial Page
        if (page <= 1) {
            initialLoading.set(true)
        }

        paginationLoading.onNext(true)
        return pokemonTCGInteractor.getPokemonTCGSets(page)
                .doFinally { initialLoading.set(false)
                    paginationLoading.onNext(false) }
    }

    override fun getPaginationLoading(): Observable<Boolean> {
        return paginationLoading
    }

    override fun getInitialLoading(): ObservableBoolean {
        return initialLoading
    }
}