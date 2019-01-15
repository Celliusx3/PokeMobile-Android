package com.app.cellstudio.data.entity

class PokemonTCGCardDataResponseModel {
    val cards: List<PokemonTCGCardDataModel> ?= null

    class PokemonTCGCardDataModel {
        val id: String? = null
        val name: String? = null
        val nationalPokedexNumber: Int ? = null
        val imageUrl: String? = null
        val imageUrlHiRes: String ? = null
        val types: List<String> ? = null
        val supertype: String ? = null
        val subtype: String ? = null
        val ability: AbilityDataModel ? = null
        val hp: String ? = null
        val retreatCost: List<String> ? = null
        val convertedRetreatCost: Int ? = null
        val number: String? = null
        val artist: String? = null
        val rarity: String ? = null
        val series: String? = null
        val set: String ? = null
        val setCode: String ? = null
        val attacks: List<AttackDataModel> ? = null
        val weaknesses: List<WeaknessDataModel> ? = null

        class AbilityDataModel {
            val name: String ? = null
            val text: String ? = null
            val type: String ? = null
        }

        class AttackDataModel {
            val cost: List<String> ? = null
            val name: String ? = null
            val text: String ? = null
            val damage: String ? = null
            val convertedEnergyCost: Int ?= null
        }

        class WeaknessDataModel {
            val type: String ? = null
            val value: String ? = null
        }
    }
}