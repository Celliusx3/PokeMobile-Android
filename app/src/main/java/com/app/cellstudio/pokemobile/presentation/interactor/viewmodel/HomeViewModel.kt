package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel

import androidx.databinding.ObservableBoolean
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import io.reactivex.Observable

interface HomeViewModel : ViewModel {
    fun getPokemonTCGSetsToShow(): Observable<List<PokemonTCGSet>>

    fun getFilterSeriesToShow(): Observable<List<String>>
    fun getFilterLegalToShow(): Observable<List<String>>

    fun getIsLoading(): ObservableBoolean

    fun onApplyClicked(filterLegal: List<String>, filterSeries: List<String>)
}