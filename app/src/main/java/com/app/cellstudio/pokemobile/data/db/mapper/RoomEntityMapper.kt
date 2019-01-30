package com.app.cellstudio.pokemobile.data.db.mapper

import com.app.cellstudio.pokemobile.data.db.entity.PokemonTCGSetDatabaseEntity
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet

class RoomEntityMapper {

    companion object {
        fun create (pokemonTCGSetDatabaseEntity: PokemonTCGSetDatabaseEntity): PokemonTCGSet {
            return PokemonTCGSet(pokemonTCGSetDatabaseEntity.code,
                    pokemonTCGSetDatabaseEntity.ptcgoCode,
                    pokemonTCGSetDatabaseEntity.name,
                    pokemonTCGSetDatabaseEntity.series,
                    pokemonTCGSetDatabaseEntity.totalCards,
                    pokemonTCGSetDatabaseEntity.standardLegal,
                    pokemonTCGSetDatabaseEntity.expandedLegal,
                    pokemonTCGSetDatabaseEntity.releaseDate,
                    pokemonTCGSetDatabaseEntity.symbolUrl,
                    pokemonTCGSetDatabaseEntity.logoUrl,
                    pokemonTCGSetDatabaseEntity.updatedAt)
        }

        fun create (pokemonTCGSet: PokemonTCGSet): PokemonTCGSetDatabaseEntity {
            return PokemonTCGSetDatabaseEntity(pokemonTCGSet.code,
                    pokemonTCGSet.ptcgoCode,
                    pokemonTCGSet.name,
                    pokemonTCGSet.series,
                    pokemonTCGSet.totalCards,
                    pokemonTCGSet.standardLegal,
                    pokemonTCGSet.expandedLegal,
                    pokemonTCGSet.releaseDate,
                    pokemonTCGSet.symbolUrl,
                    pokemonTCGSet.logoUrl,
                    pokemonTCGSet.updatedAt)
        }
    }
}