package com.app.cellstudio.domain.interactor.impl

import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.domain.entity.PokemonTCGSet
import com.app.cellstudio.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.domain.repository.PokemonTCGRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PokemonTCGInteractorImpl(private val pokemonTCGRepository: PokemonTCGRepository) : PokemonTCGInteractor {
    private val filterSeriesToShow = PublishSubject.create<List<String>>()
    private val filterLegalToShow = PublishSubject.create<List<String>>()

    override fun getFilterLegalToShow(): Observable<List<String>> {
        return filterLegalToShow
    }

    override fun getFilterSeriesToShow(): Observable<List<String>> {
        return filterSeriesToShow
    }

    override fun searchPokemonTCGCards(name: String): Observable<List<PokemonTCGCard>> {
        return pokemonTCGRepository.searchPokemonTCGCards(name)
    }

    override fun getPokemonTCGCards(code: String, page: Int): Observable<List<PokemonTCGCard>> {
        return pokemonTCGRepository.getPokemonTCGCards(code, page, PAGE_SIZE)
    }

    override fun getAllPokemonTCGSets(isReverseOrder: Boolean): Observable<List<PokemonTCGSet>> {
        return pokemonTCGRepository.getAllPokemonTCGSets()
                .map {
                    val filterSeries = generateFilterSeriesToShow(it)
                    filterSeriesToShow.onNext(filterSeries)
                    return@map it
                }
                .map {
                    val filterLegal = generateFilterLegalsToShow()
                    filterLegalToShow.onNext(filterLegal)
                    return@map it
                }
                .map {
                    if (isReverseOrder) return@map it.asReversed()
                    return@map it
                }
    }

    private fun generateFilterSeriesToShow(pokemonTCGSets: List<PokemonTCGSet>): List<String>{
        val series = ArrayList<String>()
        for (pokemonTCGSet in pokemonTCGSets) {
            val pokemonTCGSeries = pokemonTCGSet.series
            if (pokemonTCGSeries.isNotBlank() && !series.contains(pokemonTCGSeries)) {
                series.add(pokemonTCGSeries)
            }
        }
        return series
    }

    private fun generateFilterLegalsToShow(): List<String>{
        val legals = ArrayList<String>()
        legals.add("Standard")
        legals.add("Expanded")
        legals.add("None")
        return legals
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}