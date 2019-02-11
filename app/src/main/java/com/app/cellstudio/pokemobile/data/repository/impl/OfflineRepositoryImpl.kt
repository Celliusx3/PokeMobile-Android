package com.app.cellstudio.pokemobile.data.repository.impl

import com.app.cellstudio.pokemobile.data.db.AppDatabase
import com.app.cellstudio.pokemobile.data.db.dao.PokemonTCGCardDao
import com.app.cellstudio.pokemobile.data.db.dao.PokemonTCGSetDao
import com.app.cellstudio.pokemobile.data.db.mapper.RoomEntityMapper
import com.app.cellstudio.pokemobile.data.repository.OfflineRepository
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import io.reactivex.Observable

class OfflineRepositoryImpl(private val database: AppDatabase) : OfflineRepository {
    private fun getPokemonTCGSetDao(): PokemonTCGSetDao {
        return database.pokemonTCGSetDao()
    }

    private fun getPokemonTCGCardDao(): PokemonTCGCardDao {
        return database.pokemonTCGCardDao()
    }

    override fun getAllPokemonTCGSetsOffline(): Observable<List<PokemonTCGSet>> {
        return getPokemonTCGSetDao().getSets()
                .toObservable()
                .flatMap { Observable.fromIterable(it) }
                .map { RoomEntityMapper.create(it)}
                .toList()
                .toObservable()
    }

    override fun setAllPokemonTCGSetsOffline(pokemonTCGSets: List<PokemonTCGSet>) {
        val sets = pokemonTCGSets.map { RoomEntityMapper.create(it) }
        getPokemonTCGSetDao().insertSets(sets)
    }

    override fun getPokemonTCGCardsOffline(code: String): Observable<List<PokemonTCGCard>> {
        return getPokemonTCGCardDao().getCardsFromSet(code)
                .toObservable()
                .flatMap { Observable.fromIterable(it) }
                .map { RoomEntityMapper.create(it)}
                .toList()
                .toObservable()
    }

    override fun setPokemonTCGCardsOffline(pokemonTCGCards: List<PokemonTCGCard>) {
        val cards = pokemonTCGCards.map { RoomEntityMapper.create(it) }
        getPokemonTCGCardDao().insertCards(cards)
    }

}