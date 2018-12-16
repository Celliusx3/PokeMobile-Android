package com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.app.cellstudio.androidkotlincleanboilerplate.BaseApplication
import com.app.cellstudio.androidkotlincleanboilerplate.R
import com.app.cellstudio.androidkotlincleanboilerplate.databinding.ActivityMovieDetailsBinding
import com.app.cellstudio.androidkotlincleanboilerplate.di.modules.MovieDetailsModule
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.model.MoviePresentationModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.MovieDetailsViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.navigation.Navigator
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MovieDetailsActivity : BaseActivity() {

    @Inject
    lateinit var movieDetailsViewModel: MovieDetailsViewModel

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var movie: MoviePresentationModel

    override fun getLayoutResource(): Int {
        return R.layout.activity_movie_details
    }

    override fun getRootView(): View {
        return rlDetails
    }

    override fun onInject() {
        BaseApplication.getInstance()
                .getApplicationComponent()
                .plus(MovieDetailsModule())
                .inject(this)
    }

    override fun onBindView() {
        super.onBindView()
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        isMoreButtonVisible()
    }

    override fun onBindData(view: View?, savedInstanceState: Bundle?) {
        super.onBindData(view, savedInstanceState)

        val disposable = movieDetailsViewModel.getWatchTrailerButtonClicked()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    Navigator.navigateToYoutube(this, movie.trailer)
                }

        compositeDisposable.add(disposable)

        binding = DataBindingUtil.bind(view!!)!!
        binding.model = movie
        binding.viewModel = movieDetailsViewModel
    }

    override fun onGetInputData(savedInstanceState: Bundle?) {
        super.onGetInputData(savedInstanceState)
        if (intent != null) {
            movie = intent.getParcelableExtra(EXTRA_MOVIE)
        }
    }

    private fun isMoreButtonVisible() {
        tvDetailsSynopsis.post {
            var ellipsized = false
            val layout = tvDetailsSynopsis.layout
            if (layout != null) {
                val lines = layout.lineCount
                if (lines > 0) {
                    val ellipsisCount = layout.getEllipsisCount(lines - 1)
                    if (ellipsisCount > 0) {
                        ellipsized = true
                    }
                }
            }
            tvMore.visibility = if (ellipsized) View.VISIBLE else View.GONE
        }
    }

    companion object {

        private val TAG = MovieDetailsActivity::class.java.simpleName

        private val EXTRA_MOVIE = "EXTRA_MOVIE"

        fun getCallingIntent(context: Context, movie: MoviePresentationModel): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movie)
            return intent
        }
    }
}
