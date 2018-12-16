package com.app.cellstudio.data.http

import com.app.cellstudio.data.api.ApiService

interface HttpClient {
    fun getApiService() : ApiService
}