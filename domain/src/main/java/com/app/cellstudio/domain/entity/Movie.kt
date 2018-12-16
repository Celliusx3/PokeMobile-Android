package com.app.cellstudio.domain.entity

class Movie(val id: String,
            val title: String,
            val year: String,
            val synopsis: String,
            val runtime: String,
            val released: Int,
            val trailer: String,
            val genres: List<String>,
            val images: Image,
            val rating: Rating) {

    fun getYearRuntimeGenres(): String {
        val sb = StringBuilder()
        sb.append(this.year).append(" • ").append(this.runtime).append(" mins")
        if (!genres.isEmpty()) {
            sb.append(" • ").append(genres[0])
        }

        return sb.toString()
    }
}