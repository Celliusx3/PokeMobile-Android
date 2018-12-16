package com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.app.cellstudio.androidkotlincleanboilerplate.BaseApplication
import com.app.cellstudio.androidkotlincleanboilerplate.R
import com.app.cellstudio.androidkotlincleanboilerplate.di.modules.HomeModule
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.model.MoviePresentationModel
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.HomeViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.adapter.MovieListAdapter
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.component.OnEndlessScrollListener
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {
    @Inject
    lateinit var homeViewModel: HomeViewModel

    private var movieListAdapter: MovieListAdapter? = null
    private var currentMoviePageInIndex: Int = 0
    private var moviePages: List<String>? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_home
    }

    override fun onInject() {
        BaseApplication.getInstance()
                .getApplicationComponent()
                .plus(HomeModule())
                .inject(this)
    }

    override fun onBindData(view: View?) {
        super.onBindData(view)

        val disposable = homeViewModel.getMoviePages()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe { moviePages ->
                    this.moviePages = moviePages
                    this.currentMoviePageInIndex = 0

                    this.getSpecificMoviePage(moviePages[currentMoviePageInIndex])
                            .subscribe { this.setupMoviesList(it) }
                }
        compositeDisposable.add(disposable)
    }

    override fun onResume() {
        super.onResume()

        val disposable = homeViewModel.getIsLoading()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe { isLoading -> this.movieListAdapter?.setLoading(isLoading) }
        compositeDisposable.add(disposable)
        subscribeSelectedMovie()
    }

    private fun setupMoviesList(movies: List<MoviePresentationModel>) {
        val spanCount = 2
        val layoutManager = GridLayoutManager(context, spanCount)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (movieListAdapter?.getItemViewType(position)) {
                    MovieListAdapter.VIEW_TYPE_MOVIE -> 1
                    MovieListAdapter.VIEW_TYPE_LOADING -> spanCount //number of columns of the grid
                    else -> -1
                }
            }
        }
        rvHomeItem.layoutManager = layoutManager
        movieListAdapter = MovieListAdapter(movies.toMutableList())
        rvHomeItem.adapter = movieListAdapter
        rvHomeItem.isNestedScrollingEnabled = false
        rvHomeItem.addOnScrollListener(object : OnEndlessScrollListener() {
            override fun onLoadMore() {
                currentMoviePageInIndex += 1
                if (currentMoviePageInIndex < moviePages!!.size) {
                    val disposable = getSpecificMoviePage(moviePages!![currentMoviePageInIndex])
                            .subscribe { moviePage -> movieListAdapter?.updateData(moviePage) }
                    compositeDisposable.add(disposable)
                }
            }
        })
        subscribeSelectedMovie()

    }

    private fun subscribeSelectedMovie() {
        if (movieListAdapter == null)
            return

        val disposable = movieListAdapter!!.getSelectedModel().compose(bindToLifecycle())
                .observeOn(getUiScheduler())
                .subscribe { selectedMovie -> navigator.navigateToMovieDetails(context, selectedMovie) }
        compositeDisposable.add(disposable)
    }

    private fun getSpecificMoviePage(path: String): Observable<List<MoviePresentationModel>> {
        return homeViewModel.getMoviePage(path)
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
    }

    companion object {

        private val TAG = HomeFragment::class.java.simpleName

        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}