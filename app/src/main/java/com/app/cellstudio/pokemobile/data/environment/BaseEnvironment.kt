package com.app.cellstudio.pokemobile.data.environment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.app.cellstudio.pokemobile.BuildConfig
import com.app.cellstudio.pokemobile.R

class BaseEnvironment(val context: Context) : Environment {
    override fun getAppName(): String {
        return context.getString(R.string.app_name)
    }

    override fun getPackageName(): String {
        return BuildConfig.APPLICATION_ID
    }

    override fun getAppVersion(): String {
        return BuildConfig.VERSION_NAME
    }

    override fun getBuildNumber(): String {
        return BuildConfig.VERSION_CODE.toString()
    }

    @SuppressLint("HardwareIds")
    override fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    override fun getDeviceName(): String {
        return Build.MODEL
    }

    override fun getOSName(): String {
        return Build.VERSION.SDK_INT.toString()
    }

    override fun getOSVersion(): String {
        return Build.VERSION.RELEASE
    }

    override fun getBaseUrl(): String {
        return "https://api.pokemontcg.io/v1/"
    }

    override fun getNextUpdateDuration(): Long {
        // 10 days
        return 1000 * 60 * 60 * 24 * 10
    }
}