package com.app.cellstudio.pokemobile.data.pref

import com.app.cellstudio.pokemobile.data.entity.PokemonTCGCardSubtypeDataResponseModel
import com.app.cellstudio.pokemobile.data.entity.PokemonTCGCardSupertypeDataResponseModel
import com.app.cellstudio.pokemobile.data.entity.PokemonTCGCardTypeDataResponseModel

interface BasePref {
    fun getFilterTypes(): PokemonTCGCardTypeDataResponseModel?
    fun getNextFilterTypesUpdate(): Long

    fun getFilterSupertypes(): PokemonTCGCardSupertypeDataResponseModel?
    fun getNextFilterSupertypesUpdate(): Long

    fun getFilterSubtypes(): PokemonTCGCardSubtypeDataResponseModel?
    fun getNextFilterSubtypesUpdate(): Long

    fun setFilterTypes(pokemonTCGCardTypeDataResponseModel: PokemonTCGCardTypeDataResponseModel)
    fun setNextFilterTypesUpdate(updateTime: Long)

    fun setFilterSupertypes(pokemonTCGCardSupertypeDataResponseModel: PokemonTCGCardSupertypeDataResponseModel)
    fun setNextFilterSupertypesUpdate(updateTime: Long)

    fun setFilterSubtypes(pokemonTCGCardSubtypeDataResponseModel: PokemonTCGCardSubtypeDataResponseModel)
    fun setNextFilterSubtypesUpdate(updateTime: Long)

    fun clearAllSharedPref()
}