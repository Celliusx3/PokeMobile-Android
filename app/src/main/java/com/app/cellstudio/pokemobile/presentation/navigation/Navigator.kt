package com.app.cellstudio.pokemobile.presentation.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.app.cellstudio.pokemobile.presentation.view.activity.ImageViewActivity
import com.app.cellstudio.pokemobile.presentation.view.activity.MainActivity
import com.app.cellstudio.pokemobile.presentation.view.activity.PokemonTCGDetailsActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

    fun navigateToMain(context: Context?) {
        if (context != null) {
            val intentToLaunch = MainActivity.getCallingIntent(context)
            context.startActivity(intentToLaunch)
        }
    }

    fun navigateToDetails(context: Context?, pokemonTCGId: String, pokemonTCGTitle: String) {
        if (context != null) {
            val intentToLaunch = PokemonTCGDetailsActivity.getCallingIntent(context, pokemonTCGId, pokemonTCGTitle)
            context.startActivity(intentToLaunch)
        }
    }

    fun navigateToImageViewer(context: Context?, position: Int, imageUrls: List<String>) {
        if (context != null) {
            val intentToLaunch = ImageViewActivity.getCallingIntent(context, position, imageUrls)
            context.startActivity(intentToLaunch)
        }
    }

    companion object {
        fun navigateToYoutube(context: Context?, url: String) {
            if (context != null) {
                val webIntent = Intent(Intent.ACTION_VIEW,
                        Uri.parse(url))
                context.startActivity(webIntent)
            }
        }
    }
}