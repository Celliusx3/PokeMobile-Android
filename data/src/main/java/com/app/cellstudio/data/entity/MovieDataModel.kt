package com.app.cellstudio.data.entity

import java.util.*

class MovieDataModel {
    val _id: String? = null
    val imdb_id: String? = null
    val title: String? = null
    val year: String? = null
    val synopsis: String? = null
    val runtime: String? = null
    val released: Int ?= null
    val trailer: String? = null
    val certification: String? = null
    val genres: List<String>? = null
    val torrents: HashMap<String, Any>? = null
    val images: ImageDataModel? = null
    val rating: RatingDataModel? = null
}