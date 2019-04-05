package com.app.cellstudio.pokemobile.data.db.mapper

import com.app.cellstudio.pokemobile.data.db.entity.PokemonTCGCardDatabaseEntity
import com.app.cellstudio.pokemobile.data.db.entity.PokemonTCGSetDatabaseEntity
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
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

        fun create (pokemonTCGCardDatabaseEntity: PokemonTCGCardDatabaseEntity): PokemonTCGCard {
            return PokemonTCGCard(pokemonTCGCardDatabaseEntity.id,
                    pokemonTCGCardDatabaseEntity.name,
                    pokemonTCGCardDatabaseEntity.nationalPokedexNumber,
                    pokemonTCGCardDatabaseEntity.imageUrl,
                    pokemonTCGCardDatabaseEntity.imageUrlHiRes,
                    pokemonTCGCardDatabaseEntity.types,
                    pokemonTCGCardDatabaseEntity.supertype,
                    pokemonTCGCardDatabaseEntity.subtype,
                    PokemonTCGCard.Ability("", "",""),
                    pokemonTCGCardDatabaseEntity.hp,
                    pokemonTCGCardDatabaseEntity.retreatCost,
                    pokemonTCGCardDatabaseEntity.convertedRetreatCost,
                    pokemonTCGCardDatabaseEntity.number,
                    pokemonTCGCardDatabaseEntity.artist,
                    pokemonTCGCardDatabaseEntity.rarity,
                    pokemonTCGCardDatabaseEntity.series,
                    pokemonTCGCardDatabaseEntity.set,
                    pokemonTCGCardDatabaseEntity.setCode,
                    ArrayList(),
                    ArrayList())
        }

        fun create (pokemonTCGCard: PokemonTCGCard): PokemonTCGCardDatabaseEntity {
            return PokemonTCGCardDatabaseEntity(pokemonTCGCard.id,
                    pokemonTCGCard.name,
                    pokemonTCGCard.nationalPokedexNumber,
                    pokemonTCGCard.imageUrl,
                    pokemonTCGCard.imageUrlHiRes,
                    pokemonTCGCard.types,
                    pokemonTCGCard.supertype,
                    pokemonTCGCard.subtype,
                    PokemonTCGCardDatabaseEntity.AbilityDatabaseEntity("", "", ""),
                    pokemonTCGCard.hp,
                    pokemonTCGCard.retreatCost,
                    pokemonTCGCard.convertedRetreatCost,
                    pokemonTCGCard.number,
                    pokemonTCGCard.artist,
                    pokemonTCGCard.rarity,
                    pokemonTCGCard.series,
                    pokemonTCGCard.set,
                    pokemonTCGCard.setCode,
                    "",
                    "")
        }
    }
}