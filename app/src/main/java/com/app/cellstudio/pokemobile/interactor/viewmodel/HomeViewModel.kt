package com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel

import com.app.cellstudio.domain.entity.PokemonTCGSet
import io.reactivex.Observable

interface HomeViewModel : ViewModel {
    fun getPokemonTCGSets(page: Int): Observable<List<PokemonTCGSet>>
    fun getIsLoading(): Observable<Boolean>
}