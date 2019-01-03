package com.app.cellstudio.pokemobile.interactor.viewmodel

import androidx.databinding.ObservableBoolean
import com.app.cellstudio.domain.entity.PokemonTCGSet
import io.reactivex.Observable

interface HomeViewModel : ViewModel {
    fun getPokemonTCGSets(page: Int): Observable<List<PokemonTCGSet>>
    fun getPaginationLoading(): Observable<Boolean>
    fun getInitialLoading(): ObservableBoolean
}