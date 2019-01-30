package com.app.cellstudio.pokemobile.di.components

import android.content.Context
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.di.modules.*
import com.app.cellstudio.pokemobile.presentation.service.DownloadService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, InteractorModule::class, RepositoryModule::class, DatabaseModule::class ])
interface ApplicationComponent {
    fun getApplication(): BaseApplication
    fun getApplicationContext(): Context
    fun inject(baseApplication: BaseApplication)
//    fun inject(downloadService: DownloadService)
    fun plus(downloadModule: DownloadModule):DownloadComponent
    fun plus(splashModule: SplashModule): SplashComponent
    fun plus(mainModule: MainModule): MainComponent
    fun plus(homeModule: HomeModule): HomeComponent
    fun plus(pokemonTCGDetailsModule: PokemonTCGDetailsModule): PokemonTCGDetailsComponent
    fun plus(searchModule: SearchModule):SearchComponent
    fun plus(settingsModule: SettingsModule): SettingsComponent
}
