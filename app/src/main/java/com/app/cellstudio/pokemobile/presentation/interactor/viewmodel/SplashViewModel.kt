package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.reactivex.Observable

interface SplashViewModel: ViewModel {
    fun navigateToMain(): Observable<Boolean>
    fun getIsLoading(): ObservableBoolean
    fun getLoadingText(): ObservableField<String>
}