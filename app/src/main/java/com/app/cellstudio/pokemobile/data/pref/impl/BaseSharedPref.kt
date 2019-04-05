package com.app.cellstudio.pokemobile.data.pref.impl

import android.content.SharedPreferences
import android.text.TextUtils
import com.app.cellstudio.pokemobile.data.entity.PokemonTCGCardSubtypeDataResponseModel
import com.app.cellstudio.pokemobile.data.entity.PokemonTCGCardSupertypeDataResponseModel
import com.app.cellstudio.pokemobile.data.entity.PokemonTCGCardTypeDataResponseModel
import com.app.cellstudio.pokemobile.data.pref.BasePref
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken


class BaseSharedPref(private val preferences: SharedPreferences) : BasePref {
    override fun getDownloadedSets(): List<PokemonTCGSet>? {
        val type = object : TypeToken<List<PokemonTCGSet>>() {}
        return this.getObject(DOWNLOADED_SETS, type)
    }

    override fun setDownloadedSet(pokemonTCGSet: PokemonTCGSet) {
        val type = object : TypeToken<List<PokemonTCGSet>>() {}
        val list = this.getObject(DOWNLOADED_SETS, type)?.toMutableList()
        if (list != null) {
            list.add(pokemonTCGSet)
            this.putObject(DOWNLOADED_SETS, list)
        } else {
            this.putObject(DOWNLOADED_SETS, listOf(pokemonTCGSet))
        }
    }

    override fun getNextFilterTypesUpdate(): Long {
        return this.getLong(CARD_TYPE_NEXT_UPDATE)
    }

    override fun getNextFilterSupertypesUpdate(): Long {
        return this.getLong(CARD_SUPERTYPE_NEXT_UPDATE)
    }

    override fun getNextFilterSubtypesUpdate(): Long {
        return this.getLong(CARD_SUBTYPE_NEXT_UPDATE)
    }

    override fun setNextFilterTypesUpdate(updateTime: Long) {
        this.putLong(CARD_TYPE_NEXT_UPDATE, updateTime)
    }

    override fun setNextFilterSupertypesUpdate(updateTime: Long) {
        this.putLong(CARD_SUPERTYPE_NEXT_UPDATE, updateTime)
    }

    override fun setNextFilterSubtypesUpdate(updateTime: Long) {
        this.putLong(CARD_SUBTYPE_NEXT_UPDATE, updateTime)
    }

    override fun setFilterTypes(pokemonTCGCardTypeDataResponseModel: PokemonTCGCardTypeDataResponseModel) {
        this.putObject(CARD_TYPE, pokemonTCGCardTypeDataResponseModel)
    }

    override fun setFilterSupertypes(pokemonTCGCardSupertypeDataResponseModel: PokemonTCGCardSupertypeDataResponseModel) {
        this.putObject(CARD_SUPERTYPE, pokemonTCGCardSupertypeDataResponseModel)
    }

    override fun setFilterSubtypes(pokemonTCGCardSubtypeDataResponseModel: PokemonTCGCardSubtypeDataResponseModel) {
        this.putObject(CARD_SUBTYPE, pokemonTCGCardSubtypeDataResponseModel)
    }

    override fun getFilterTypes(): PokemonTCGCardTypeDataResponseModel? {
        val type = object : TypeToken<PokemonTCGCardTypeDataResponseModel>() {}
        return this.getObject(CARD_TYPE, type)
    }

    override fun getFilterSupertypes(): PokemonTCGCardSupertypeDataResponseModel?{
        val type = object : TypeToken<PokemonTCGCardSupertypeDataResponseModel>() {}
        return this.getObject(CARD_SUPERTYPE, type)
    }

    override fun getFilterSubtypes(): PokemonTCGCardSubtypeDataResponseModel? {
        val type = object : TypeToken<PokemonTCGCardSubtypeDataResponseModel>() {}
        return this.getObject(CARD_SUBTYPE, type)
    }

    override fun clearAllSharedPref() {
        preferences.edit().clear().apply()
    }

    private fun putInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    private fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    private fun putBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    private fun getLong(key: String): Long {
        return preferences.getLong(key, 0)
    }

    private fun putLong(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    private fun getString(key: String): String {
        return preferences.getString(key, "")!!
    }

    private fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    private fun createGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    private fun putObject(key: String, jsonObject: Any) {
        val gson = createGson()
        val jsonObjectInString = gson.toJson(jsonObject)
        preferences.edit().putString(key, jsonObjectInString).apply()
    }

//    private fun <T> getObject(key: String, objectClass: Class<T>): T? {
//        val gson = createGson()
//        val json = preferences.getString(key, "")
//        if (!TextUtils.isEmpty(json)) {
//            return try {
//                gson.fromJson(json, objectClass)
//            } catch (e: JsonSyntaxException) {
//                null
//            }
//
//        }
//        return null
//    }

    private fun <T> getObject(key: String, objectClass: TypeToken<T>): T? {
        val gson = createGson()
        val json = preferences.getString(key, "")
        if (!TextUtils.isEmpty(json)) {
            return try {
                gson.fromJson(json, objectClass.type)
            } catch (e: JsonSyntaxException) {
                null
            }

        }
        return null
    }

    companion object {
        private const val DOWNLOADED_SETS = "DOWNLOADED_SETS"
        private const val CARD_TYPE = "CARD_TYPE"
        private const val CARD_SUBTYPE = "CARD_SUBTYPE"
        private const val CARD_SUPERTYPE = "CARD_SUPERTYPE"
        private const val CARD_TYPE_NEXT_UPDATE = "CARD_TYPE_NEXT_UPDATE"
        private const val CARD_SUBTYPE_NEXT_UPDATE = "CARD_SUBTYPE_NEXT_UPDATE"
        private const val CARD_SUPERTYPE_NEXT_UPDATE = "CARD_SUPERTYPE_NEXT_UPDATE"
    }
}
