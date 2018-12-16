package com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.impl

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import com.app.cellstudio.androidkotlincleanboilerplate.R
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.MovieDetailsViewModel
import io.reactivex.subjects.PublishSubject

class MovieDetailsViewModelImpl(scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), MovieDetailsViewModel {
    private val watchTrailerButtonClicked = PublishSubject.create<Boolean>()
    private val moreText = ObservableField("More")
    private val isExpand = ObservableBoolean(false)
    private val synopsisSize = ObservableInt(5)

    override fun getIsExpand(): ObservableBoolean {
        return isExpand
    }

    override fun getMoreText(): ObservableField<String> {
        return moreText
    }

    override fun getSynopsisSize(): ObservableInt {
        return synopsisSize
    }

    override fun getWatchTrailerButtonClicked(): PublishSubject<Boolean> {
        return watchTrailerButtonClicked
    }

    override fun onWatchTrailerButtonClicked() {
        watchTrailerButtonClicked.onNext(true)
    }

    override fun onMoreButtonClicked(view: View) {
        if (isExpand.get()) {
            moreText.set(view.resources.getString(R.string.text_MORE))
            synopsisSize.set(view.resources.getInteger(R.integer.synopsis_max_lines))
        } else {
            moreText.set(view.resources.getString(R.string.text_LESS))
            synopsisSize.set(Integer.MAX_VALUE)
        }
        isExpand.set(!isExpand.get())
    }
}
