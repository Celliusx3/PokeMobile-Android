package com.app.cellstudio.domain.interactor.impl

import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.domain.entity.PokemonTCGSet
import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.domain.repository.PokemonTCGRepository
import io.reactivex.Observable

class PokemonTCGInteractorImpl(private val pokemonTCGRepository: PokemonTCGRepository) : PokemonTCGInteractor {
    override fun searchPokemonTCGCards(name: String): Observable<List<PokemonTCGCard>> {
        return pokemonTCGRepository.searchPokemonTCGCards(name)
    }

    override fun getPokemonTCGCards(code: String, page: Int): Observable<List<PokemonTCGCard>> {
        return pokemonTCGRepository.getPokemonTCGCards(code, page, PAGE_SIZE)
    }

    override fun getPokemonTCGSets(page: Int): Observable<List<PokemonTCGSet>> {
        return pokemonTCGRepository.getPokemonTCGSets(page, PAGE_SIZE)
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}