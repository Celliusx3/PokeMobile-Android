package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel

import androidx.databinding.ObservableBoolean
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import io.reactivex.Observable

interface SearchViewModel: ViewModel {
    fun getSearchPokemonCards(keyword: String): Observable<List<PokemonTCGCard>>
    fun getLoading():ObservableBoolean
}