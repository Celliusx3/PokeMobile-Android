package com.app.cellstudio.pokemobile.data.db.converter

import androidx.room.TypeConverter
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PokemonTCGCardConverter {

    private val gson = Gson()

    @JvmStatic
    @TypeConverter
    fun stringToTypes(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @JvmStatic
    @TypeConverter
    fun typesToString(types: List<String>): String {
        return gson.toJson(types)
    }

    @JvmStatic
    @TypeConverter
    fun attacksToString(attacks: List<PokemonTCGCard.Attack>): String {
        return gson.toJson(attacks)
    }

    @JvmStatic
    @TypeConverter
    fun stringToAttacks(data: String): List<PokemonTCGCard.Attack> {
        val listType = object : TypeToken<List<PokemonTCGCard.Attack>>() {}.type
        return gson.fromJson(data, listType)
    }

    @JvmStatic
    @TypeConverter
    fun weaknessesToString(weaknesses: List<PokemonTCGCard.Weakness>): String {
        return gson.toJson(weaknesses)
    }

    @JvmStatic
    @TypeConverter
    fun stringToWeaknesses(data: String): List<PokemonTCGCard.Weakness> {
        val listType = object : TypeToken<List<PokemonTCGCard.Weakness>>() {}.type
        return gson.fromJson(data, listType)
    }
}