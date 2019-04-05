package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel

import androidx.databinding.ObservableBoolean
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import io.reactivex.Observable

interface PokemonTCGDetailsViewModel: ViewModel {
    fun getAllPokemonTCGCardsInASet(set: String)
    fun getPokemonTCGCardsToShow(): Observable<List<PokemonTCGCard>>
    fun getIsLoading(): ObservableBoolean
    fun getFilterTypesToShow(): Observable<List<String>>
    fun getFilterSubtypesToShow(): Observable<List<String>>
    fun getFilterSupertypesToShow(): Observable<List<String>>
    fun getStartDownloadService(): Observable<Unit>

    fun onApplyClicked(filterCardType: List<String>, filterType: List<String>, filterSubtype: List<String>)
    fun onDownloadButtonClicked()
}