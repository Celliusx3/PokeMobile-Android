package com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.impl

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.PokemonTCGDetailsViewModel
import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PokemonTCGDetailsViewModelImpl(private val pokemonTCGInteractor: PokemonTCGInteractor,
                                     scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), PokemonTCGDetailsViewModel {
    private val isLoading = PublishSubject.create<Boolean>()

    override fun getIsLoading(): Observable<Boolean> {
        return isLoading
    }

    override fun getPokemonTCGCards(set: String, page: Int): Observable<List<PokemonTCGCard>> {
        isLoading.onNext(true)
        return pokemonTCGInteractor.getPokemonTCGCards(set, page)
                .doFinally { isLoading.onNext(false) }
    }
}
