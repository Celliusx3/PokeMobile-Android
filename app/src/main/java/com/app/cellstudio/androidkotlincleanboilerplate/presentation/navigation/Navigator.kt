package com.app.cellstudio.androidkotlincleanboilerplate.presentation.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.model.MoviePresentationModel
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.activity.MainActivity
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.activity.MovieDetailsActivity
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

    fun navigateToMovieDetails(context: Context?, movie: MoviePresentationModel) {
        if (context != null) {
            val intentToLaunch = MovieDetailsActivity.getCallingIntent(context, movie)
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