package com.app.cellstudio.pokemobile.domain.entity

class PokemonTCGSet(
    val code: String,
    val ptcgoCode: String,
    val name: String,
    val series: String,
    val totalCards: Int,
    val standardLegal: Boolean,
    val expandedLegal: Boolean,
    val releaseDate: String,
    val symbolUrl: String,
    val logoUrl: String,
    val updatedAt: String
)