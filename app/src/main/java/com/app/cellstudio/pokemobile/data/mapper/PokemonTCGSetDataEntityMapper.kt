package com.app.cellstudio.pokemobile.data.mapper

import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import com.app.cellstudio.pokemobile.data.entity.PokemonTCGSetDataResponseModel

class PokemonTCGSetDataEntityMapper {

    companion object {
        fun create (pokemonTCGSetDataModel: PokemonTCGSetDataResponseModel.PokemonTCGSetDataModel): PokemonTCGSet {
            return PokemonTCGSet(pokemonTCGSetDataModel.code ?: "",
                    pokemonTCGSetDataModel.ptcgoCode ?: "",
                    pokemonTCGSetDataModel.name?: "",
                    pokemonTCGSetDataModel.series ?: "",
                    pokemonTCGSetDataModel.totalCards ?: 0,
                    pokemonTCGSetDataModel.standardLegal ?: false,
                    pokemonTCGSetDataModel.expandedLegal ?: false,
                    pokemonTCGSetDataModel.releaseDate?: "",
                    pokemonTCGSetDataModel.symbolUrl?: "",
                     pokemonTCGSetDataModel.logoUrl?: "",
                    pokemonTCGSetDataModel.updatedAt?: ""
                    )
        }
    }
}