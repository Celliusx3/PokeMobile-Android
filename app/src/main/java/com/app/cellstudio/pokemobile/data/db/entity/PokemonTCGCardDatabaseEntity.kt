package com.app.cellstudio.pokemobile.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "cards",
        foreignKeys = [ForeignKey(entity = PokemonTCGSetDatabaseEntity::class,
        parentColumns = ["code"],
        childColumns = ["setCode"],
        onDelete = CASCADE)])
class PokemonTCGCardDatabaseEntity(
        @PrimaryKey val id: String,
        val name: String,
        val nationalPokedexNumber: Int,
        val imageUrl: String,
        val imageUrlHiRes: String,
        val types: List<String>,
        val supertype: String,
        val subtype: String,
        @Embedded(prefix = "ability_") val ability: AbilityDatabaseEntity?,
        val hp: String,
        val retreatCost: List<String>,
        val convertedRetreatCost: Int,
        val number: String,
        val artist: String,
        val rarity: String,
        val series: String,
        val set: String,
        val setCode: String,
        val attacks: String,
        val weaknesses: String
) {
    class AbilityDatabaseEntity (
        val name: String?,
        val text: String?,
        val type: String?
    )
}