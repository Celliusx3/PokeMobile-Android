package com.app.cellstudio.data.mapper

import com.app.cellstudio.data.entity.PokemonTCGSetDataResponseModel
import com.app.cellstudio.domain.entity.PokemonTCGSet

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