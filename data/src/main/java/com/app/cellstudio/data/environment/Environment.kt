package com.app.cellstudio.data.environment

import com.app.cellstudio.data.entity.MovieDataModel
import retrofit2.http.GET
import retrofit2.http.Path

interface Environment {
    fun getAppName() : String
    fun getPackageName() : String
    fun getAppVersion() : String
    fun getBuildNumber() : String
    fun getDeviceId() : String
    fun getDeviceName() : String
    fun getOSName() : String
    fun getOSVersion() : String
    fun getBaseUrl() : String
}