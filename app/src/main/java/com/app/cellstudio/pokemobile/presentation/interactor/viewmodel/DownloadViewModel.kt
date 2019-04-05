package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel

import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import io.reactivex.Observable

interface DownloadViewModel : ViewModel {
    fun getAllPokemonTCGCardsInASet(set: String): Observable<List<PokemonTCGCard>>
}