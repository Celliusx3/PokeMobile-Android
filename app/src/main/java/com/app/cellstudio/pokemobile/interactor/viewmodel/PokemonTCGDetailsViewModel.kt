package com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel

import com.app.cellstudio.domain.entity.PokemonTCGCard
import io.reactivex.Observable

interface PokemonTCGDetailsViewModel {
    fun getPokemonTCGCards(set: String, page: Int): Observable<List<PokemonTCGCard>>
    fun getIsLoading(): Observable<Boolean>
}