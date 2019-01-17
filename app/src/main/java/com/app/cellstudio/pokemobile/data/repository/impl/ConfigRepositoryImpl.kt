package com.app.cellstudio.pokemobile.data.repository.impl

import com.app.cellstudio.pokemobile.data.pref.BasePref
import com.app.cellstudio.pokemobile.data.repository.ConfigRepository

class ConfigRepositoryImpl(private val pref: BasePref) : ConfigRepository {
    private fun getPref(): BasePref {
        return pref
    }

    override fun clearAllSharedPref() {
        getPref().clearAllSharedPref()
    }
}