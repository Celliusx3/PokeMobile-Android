package com.app.cellstudio.pokemobile.data.repository

import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import io.reactivex.Observable

interface PokemonTCGRepository {
    fun getAllPokemonTCGSets(): Observable<List<PokemonTCGSet>>
    fun getPokemonTCGCards(code: String, pageSize: Int): Observable<List<PokemonTCGCard>>
    fun searchPokemonTCGCards(name: String): Observable<List<PokemonTCGCard>>
    fun getPokemonTCGCardTypes(): Observable<List<String>>
    fun getPokemonTCGCardSubtypes(): Observable<List<String>>
    fun getPokemonTCGCardSupertypes(): Observable<List<String>>
}