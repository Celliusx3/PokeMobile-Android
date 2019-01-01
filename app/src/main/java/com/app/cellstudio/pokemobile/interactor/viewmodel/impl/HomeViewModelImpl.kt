package com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.impl

import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.HomeViewModel
import com.app.cellstudio.domain.entity.PokemonTCGSet
import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class HomeViewModelImpl(private val pokemonTCGInteractor: PokemonTCGInteractor,
                        scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), HomeViewModel {

    private val isLoading = PublishSubject.create<Boolean>()

    override fun getIsLoading(): Observable<Boolean> {
        return isLoading
    }

    override fun getPokemonTCGSets(page: Int): Observable<List<PokemonTCGSet>> {
        isLoading.onNext(true)
        return pokemonTCGInteractor.getPokemonTCGSets(page)
                .doFinally { isLoading.onNext(false) }
    }
}