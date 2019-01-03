package com.app.cellstudio.pokemobile.presentation.view.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.app.cellstudio.domain.entity.PokemonTCGSet
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.databinding.FragmentHomeBinding
import com.app.cellstudio.pokemobile.di.modules.HomeModule
import com.app.cellstudio.pokemobile.interactor.viewmodel.HomeViewModel
import com.app.cellstudio.pokemobile.presentation.view.adapter.PokemonTCGSetsAdapter
import com.app.cellstudio.pokemobile.presentation.view.component.OnEndlessScrollListener
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {
    @Inject
    lateinit var homeViewModel: HomeViewModel

    private var pokemonTCGSetsAdapter: PokemonTCGSetsAdapter? = null
    private var currentPageInIndex: Int = 1
    private var isLastPage: Boolean = false

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

        val binding = DataBindingUtil.bind<FragmentHomeBinding>(view!!)
        binding?.viewModel = homeViewModel

        getSpecificPage(currentPageInIndex)
        getPaginationLoading()
    }

    override fun onResume() {
        super.onResume()
        subscribeSelectedModel()
    }

    private fun setupPokemonTCGSetsList(pokemonTCGSets: List<PokemonTCGSet>) {
        if (pokemonTCGSetsAdapter != null) {
            pokemonTCGSetsAdapter!!.updateData(pokemonTCGSets)
            return
        }

        val spanCount = 2
        val layoutManager = GridLayoutManager(context, spanCount)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (pokemonTCGSetsAdapter?.getItemViewType(position)) {
                    PokemonTCGSetsAdapter.VIEW_TYPE_DATA -> 1
                    PokemonTCGSetsAdapter.VIEW_TYPE_LOADING -> spanCount //number of columns of the grid
                    else -> -1
                }
            }
        }
        rvHomeItem.layoutManager = layoutManager
        pokemonTCGSetsAdapter = PokemonTCGSetsAdapter(pokemonTCGSets.toMutableList())
        rvHomeItem.adapter = pokemonTCGSetsAdapter
        rvHomeItem.isNestedScrollingEnabled = false
        rvHomeItem.addOnScrollListener(object : OnEndlessScrollListener() {
            override fun onLoadMore() {
                if (!isLastPage && pokemonTCGSetsAdapter != null && !pokemonTCGSetsAdapter!!.getLoading()) {
                    getSpecificPage(currentPageInIndex)
                }
            }
        })
        subscribeSelectedModel()

    }

    private fun subscribeSelectedModel() {
        if (pokemonTCGSetsAdapter == null)
            return

        val disposable = pokemonTCGSetsAdapter!!.getSelectedModel()
                .compose(bindToLifecycle())
                .observeOn(getUiScheduler())
                .subscribe { model -> navigator.navigateToDetails(context, model.code, model.name) }
        compositeDisposable.add(disposable)
    }

    private fun getSpecificPage(pageNumber: Int) {
        val disposable = homeViewModel.getPokemonTCGSets(pageNumber)
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    if (it.isEmpty()) {
                        isLastPage = true
                        return@subscribe
                    }
                    this.currentPageInIndex ++
                    this.setupPokemonTCGSetsList(it) }

        compositeDisposable.add(disposable)
    }

    private fun getPaginationLoading() {
        val disposable =homeViewModel.getPaginationLoading()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe { isLoading -> this.pokemonTCGSetsAdapter?.setLoading(isLoading) }
        compositeDisposable.add(disposable)
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