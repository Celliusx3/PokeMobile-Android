package com.app.cellstudio.pokemobile.presentation.view.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.databinding.FragmentSearchBinding
import com.app.cellstudio.pokemobile.di.modules.SearchModule
import com.app.cellstudio.pokemobile.interactor.viewmodel.SearchViewModel
import com.app.cellstudio.pokemobile.interactor.viewmodel.ViewModel
import com.app.cellstudio.pokemobile.presentation.view.adapter.PokemonTCGCardsAdapter
import com.app.cellstudio.pokemobile.presentation.view.adapter.PokemonTCGCardsAdapter.Companion.VIEW_TYPE_DATA
import com.app.cellstudio.pokemobile.presentation.view.adapter.PokemonTCGCardsAdapter.Companion.VIEW_TYPE_LOADING
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : BaseFragment() {
    @Inject
    lateinit var searchViewModel: SearchViewModel

    private var pokemonTCGCardsAdapter: PokemonTCGCardsAdapter? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_search
    }

    override fun getViewModel(): ViewModel {
        return searchViewModel
    }

    override fun onInject() {
        BaseApplication.getInstance()
                .getApplicationComponent()
                .plus(SearchModule())
                .inject(this)
    }

    override fun onBindData(view: View?) {
        super.onBindData(view)

        val binding = DataBindingUtil.bind<FragmentSearchBinding>(view!!)
        binding?.viewModel = searchViewModel

    }

    fun onSearchQueryUpdated(query: String) {
        this.searchPokemonTCGCard(query)
    }

    private fun searchPokemonTCGCard(name: String) {
        val disposable = searchViewModel.getSearchPokemonCards(name)
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    rlSearchEmpty.visibility = if (it.isEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                    rvSearchPokemonTCGCards.visibility = if (it.isEmpty()) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                    if (it.isEmpty()) {
                        return@subscribe
                    }
                    setupPokemonTCGCardsList(it)
                }

        compositeDisposable.add(disposable)
    }

    private fun setupPokemonTCGCardsList(pokemonTCGCards: List<PokemonTCGCard>) {
        if (pokemonTCGCardsAdapter != null) {
            pokemonTCGCardsAdapter!!.renewData(pokemonTCGCards)
            return
        }

        val spanCount = 2
        val layoutManager = GridLayoutManager(context, spanCount)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (pokemonTCGCardsAdapter?.getItemViewType(position)) {
                    VIEW_TYPE_DATA -> 1
                    VIEW_TYPE_LOADING -> spanCount //number of columns of the grid
                    else -> -1
                }
            }
        }
        rvSearchPokemonTCGCards.layoutManager = layoutManager
        pokemonTCGCardsAdapter = PokemonTCGCardsAdapter(pokemonTCGCards.toMutableList())
        rvSearchPokemonTCGCards.adapter = pokemonTCGCardsAdapter
        rvSearchPokemonTCGCards.isNestedScrollingEnabled = false
        subscribeSelectedModel()

    }

    private fun subscribeSelectedModel() {
        if (pokemonTCGCardsAdapter == null)
            return

        val disposable = pokemonTCGCardsAdapter!!.getSelectedModel().compose(bindToLifecycle())
                .observeOn(getUiScheduler())
                .subscribe {
                    val test = ArrayList<String>()
                    for (data in pokemonTCGCardsAdapter!!.getData()) {
                        test.add(data.imageUrlHiRes)
                    }
                    navigator.navigateToImageViewer(context, it, test)
                }
        compositeDisposable.add(disposable)
    }

    companion object {

        private val TAG = SearchFragment::class.java.simpleName

        fun newInstance(): SearchFragment {
            val args = Bundle()
            val fragment = SearchFragment()
            fragment.arguments = args
            return fragment
        }
    }

}