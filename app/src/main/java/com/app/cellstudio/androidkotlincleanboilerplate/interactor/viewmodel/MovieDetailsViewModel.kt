package com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import io.reactivex.subjects.PublishSubject

interface MovieDetailsViewModel {
    fun getWatchTrailerButtonClicked(): PublishSubject<Boolean>
    fun getSynopsisSize(): ObservableInt
    fun getMoreText(): ObservableField<String>
    fun getIsExpand(): ObservableBoolean

    fun onWatchTrailerButtonClicked()
    fun onMoreButtonClicked(view: View)

}