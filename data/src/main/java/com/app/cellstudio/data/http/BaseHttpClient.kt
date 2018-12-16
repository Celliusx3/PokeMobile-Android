package com.app.cellstudio.data.http

import android.util.Log.INFO
import com.app.cellstudio.data.api.ApiService
import com.app.cellstudio.data.environment.Environment
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BaseHttpClient(private val environment: Environment) : HttpClient {

    private val okHttpClient: OkHttpClient
    private lateinit var apiService: ApiService

    init {
        okHttpClient = createOkHttpClient()
        createService(okHttpClient)
    }

    override fun getApiService(): ApiService {
        return apiService
    }

    private fun createOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor { message -> Platform.get().log(INFO, message, null) }
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()
    }

    private fun createService(okHttpClient: OkHttpClient) {
        apiService = createApiService(okHttpClient)
    }

    private fun createApiService(client: OkHttpClient): ApiService {
        val baseUrl = environment.getBaseUrl()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

        return retrofit.create(ApiService::class.java)
    }
}