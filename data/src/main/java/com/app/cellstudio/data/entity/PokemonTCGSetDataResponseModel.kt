package com.app.cellstudio.data.entity

class PokemonTCGSetDataResponseModel {
    val sets: List<PokemonTCGSetDataModel> ?= null

    class PokemonTCGSetDataModel {
        val code: String? = null
        val ptcgoCode: String? = null
        val name: String ? = null
        val series: String? = null
        val totalCards: Int ? = null
        val standardLegal: Boolean ? = null
        val expandedLegal: Boolean ? = null
        val releaseDate: String ? = null
        val symbolUrl: String ? = null
        val logoUrl: String ? = null
        val updatedAt: String ? = null
    }
}