package com.app.cellstudio.pokemobile.data.db.entity

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
    @PrimaryKey var id: String,
    var name: String,
    var number: String,
    var text: String,
    var artist: String,
    var rarity: String,
    var nationalPokedexNumber: Int,
    var hp: Int,
    var retreatCost: Int,
    var types: String,
    var superType: String,
    var subType: String,
    var evolvesFrom: String,
    var series: String,
    var expansionSet: String,
    var setCode: String,
    var imageUrl: String,
    var imageUrlHiRes: String

//    @Embedded(prefix = "ability_") var ability: Abi?,
//    var weaknesses: String?,
//    var resistances: String?
)