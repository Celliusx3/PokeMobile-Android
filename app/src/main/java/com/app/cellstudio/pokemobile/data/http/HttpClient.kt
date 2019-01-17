package com.app.cellstudio.pokemobile.data.http

import com.app.cellstudio.pokemobile.data.api.ApiService

interface HttpClient {
    fun getApiService() : ApiService
}