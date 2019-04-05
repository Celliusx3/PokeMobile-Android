package com.app.cellstudio.pokemobile.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class CacheStatus : Parcelable {

    @Parcelize
    object Empty : CacheStatus()

    @Parcelize
    object Queued : CacheStatus()

    @Parcelize
    data class Downloading(val progress: Float? = null) : CacheStatus()

    @Parcelize
    object Cached : CacheStatus()
}