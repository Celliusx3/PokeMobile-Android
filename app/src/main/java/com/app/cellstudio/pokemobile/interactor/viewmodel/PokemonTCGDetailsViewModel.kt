package com.app.cellstudio.pokemobile.interactor.viewmodel

import androidx.databinding.ObservableBoolean
import com.app.cellstudio.domain.entity.PokemonTCGCard
import io.reactivex.Observable

interface PokemonTCGDetailsViewModel {
    fun getPokemonTCGCards(set: String, page: Int): Observable<List<PokemonTCGCard>>
    fun getPaginationLoading(): Observable<Boolean>
    fun getInitialLoading(): ObservableBoolean

}