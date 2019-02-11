package com.app.cellstudio.pokemobile.domain.interactor.impl

import com.app.cellstudio.pokemobile.data.repository.OfflineRepository
import com.app.cellstudio.pokemobile.data.repository.PokemonTCGRepository
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import com.app.cellstudio.pokemobile.domain.interactor.PokemonTCGInteractor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject



class PokemonTCGInteractorImpl(private val pokemonTCGRepository: PokemonTCGRepository,
                               private val offlineRepository: OfflineRepository) : PokemonTCGInteractor {
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

    override fun getAllPokemonTCGCards(code: String, isConnected: Boolean): Observable<List<PokemonTCGCard>> {
        val obs = if(!isConnected) {offlineRepository.getPokemonTCGCardsOffline(code)} else {
            pokemonTCGRepository.getPokemonTCGCards(code, PAGE_SIZE) .map {
                offlineRepository.setPokemonTCGCardsOffline(it)
                return@map it
            }
        }
        return obs.map {
                    val regex = Regex("[^0-9]")
                    return@map it.sortedBy {
                        regex.replace(it.number, "").toInt()
                    }
                }
    }

    override fun getAllPokemonTCGSets(isReverseOrder: Boolean, isConnected: Boolean): Observable<List<PokemonTCGSet>> {
        val obs = if (!isConnected) { offlineRepository.getAllPokemonTCGSetsOffline() }
        else { pokemonTCGRepository.getAllPokemonTCGSets()
                .map {
                    offlineRepository.setAllPokemonTCGSetsOffline(it)
                    return@map it
                }
        }

        return obs.map {
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

    override fun getFilterSupertypesToShow(): Observable<List<String>> {
        return pokemonTCGRepository.getPokemonTCGCardSupertypes()
    }

    override fun getFilterSubtypesToShow(): Observable<List<String>> {
        return pokemonTCGRepository.getPokemonTCGCardSubtypes()
    }

    override fun getFilterTypesToShow(): Observable<List<String>> {
        return pokemonTCGRepository.getPokemonTCGCardTypes()
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
        private const val PAGE_SIZE = 1000
    }
}