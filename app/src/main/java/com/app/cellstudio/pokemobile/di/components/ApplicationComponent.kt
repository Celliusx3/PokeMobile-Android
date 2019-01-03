package com.app.cellstudio.pokemobile.di.components

import android.content.Context
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.di.modules.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, InteractorModule::class, RepositoryModule::class ])
interface ApplicationComponent {
    fun getApplication(): BaseApplication
    fun getApplicationContext(): Context
    fun inject(baseApplication: BaseApplication)
    fun plus(mainModule: MainModule): MainComponent
    fun plus(homeModule: HomeModule): HomeComponent
    fun plus(movieDetailsModule: PokemonTCGDetailsModule): PokemonTCGDetailsComponent
    fun plus(searchModule: SearchModule):SearchComponent
}
