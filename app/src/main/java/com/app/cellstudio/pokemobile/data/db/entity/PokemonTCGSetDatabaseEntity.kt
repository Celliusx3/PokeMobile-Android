package com.app.cellstudio.pokemobile.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets")
class PokemonTCGSetDatabaseEntity(
    @PrimaryKey var code: String,
    var ptcgoCode: String,
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