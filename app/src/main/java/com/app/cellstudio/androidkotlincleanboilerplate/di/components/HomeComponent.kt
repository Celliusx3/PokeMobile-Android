package com.app.cellstudio.androidkotlincleanboilerplate.di.components

import com.app.cellstudio.androidkotlincleanboilerplate.di.modules.HomeModule
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.fragment.HomeFragment
import dagger.Subcomponent

@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
}