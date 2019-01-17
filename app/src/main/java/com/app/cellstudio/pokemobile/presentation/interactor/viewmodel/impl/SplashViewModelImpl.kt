package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.impl

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.app.cellstudio.pokemobile.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.SplashViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class SplashViewModelImpl(private val pokemonTCGInteractor: PokemonTCGInteractor,
                        scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), SplashViewModel {
    private val isLoading = ObservableBoolean(false)
    private val loadingText = ObservableField<String>("")
    private val isNavigatingToMain = PublishSubject.create<Boolean>()

    override fun onCreateView() {
        super.onCreateView()

        isLoading.set(true)
        loadingText.set("Getting Data Types...")
        val disposable = pokemonTCGInteractor.getFilterTypesToShow()
                .flatMap {
                    loadingText.set("Getting Data Subtypes...")
                    pokemonTCGInteractor.getFilterSubtypesToShow() }
                .flatMap {
                    loadingText.set("Getting Data Supertypes...")
                    pokemonTCGInteractor.getFilterSupertypesToShow() }
                .doFinally {
                    loadingText.set("")
                    isLoading.set(false) }
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe ({ isNavigatingToMain.onNext(true)},
                        { isNavigatingToMain.onNext(true)})
        subscriptions.add(disposable)
    }

    override fun navigateToMain(): Observable<Boolean> {
        return isNavigatingToMain
    }

    override fun getIsLoading(): ObservableBoolean {
        return isLoading
    }

    override fun getLoadingText(): ObservableField<String> {
        return loadingText
    }
}