package com.app.cellstudio.androidkotlincleanboilerplate.di.components

import com.app.cellstudio.androidkotlincleanboilerplate.di.modules.MovieDetailsModule
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.activity.MovieDetailsActivity
import dagger.Subcomponent

@Subcomponent(modules = [MovieDetailsModule::class])
interface MovieDetailsComponent {
    fun inject(movieDetailsActivity: MovieDetailsActivity)
}
