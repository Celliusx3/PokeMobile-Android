package com.app.cellstudio.pokemobile.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.databinding.ActivityPokemonTcgDetailsBinding
import com.app.cellstudio.pokemobile.di.modules.PokemonTCGDetailsModule
import com.app.cellstudio.pokemobile.interactor.viewmodel.PokemonTCGDetailsViewModel
import com.app.cellstudio.pokemobile.presentation.view.adapter.PokemonTCGCardsAdapter
import com.app.cellstudio.pokemobile.presentation.view.adapter.PokemonTCGSetsAdapter
import com.app.cellstudio.pokemobile.presentation.view.component.OnEndlessScrollListener
import kotlinx.android.synthetic.main.activity_pokemon_tcg_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class PokemonTCGDetailsActivity : BaseActivity() {

    @Inject
    lateinit var pokemonTCGDetailsViewModel: PokemonTCGDetailsViewModel

    private var pokemonTCGCardsAdapter: PokemonTCGCardsAdapter? = null
    private lateinit var pokemonTCGId: String
    private lateinit var pokemonTCGTitle: String
    private var currentPageInIndex: Int = 1
    private var isLastPage: Boolean = false

    override fun getLayoutResource(): Int {
        return R.layout.activity_pokemon_tcg_details
    }

    override fun getRootView(): View {
        return rlDetails
    }

    override fun getToolbarTitle(): String {
        return pokemonTCGTitle
    }

    override fun onInject() {
        BaseApplication.getInstance()
                .getApplicationComponent()
                .plus(PokemonTCGDetailsModule())
                .inject(this)
    }

    override fun onBindView() {
        super.onBindView()
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBindData(view: View?, savedInstanceState: Bundle?) {
        super.onBindData(view, savedInstanceState)

        val binding = DataBindingUtil.bind<ActivityPokemonTcgDetailsBinding>(view!!)
        binding?.viewModel = pokemonTCGDetailsViewModel

        getSpecificPage(currentPageInIndex)
        getLoading()
    }

    override fun onGetInputData(savedInstanceState: Bundle?) {
        super.onGetInputData(savedInstanceState)
        if (intent != null) {
            pokemonTCGId = intent.getStringExtra(EXTRA_POKEMON_TCG_ID)
            pokemonTCGTitle = intent.getStringExtra(EXTRA_POKEMON_TCG_TITLE)
        }
    }

    override fun onResume() {
        super.onResume()
        subscribeSelectedModel()
    }

    private fun setupPokemonTCGCardsList(pokemonTCGCards: List<PokemonTCGCard>) {
        if (pokemonTCGCardsAdapter != null) {
            pokemonTCGCardsAdapter!!.updateData(pokemonTCGCards)
            return
        }

        val spanCount = 2
        val layoutManager = GridLayoutManager(this, spanCount)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (pokemonTCGCardsAdapter?.getItemViewType(position)) {
                    PokemonTCGSetsAdapter.VIEW_TYPE_DATA -> 1
                    PokemonTCGSetsAdapter.VIEW_TYPE_LOADING -> spanCount //number of columns of the grid
                    else -> -1
                }
            }
        }
        rvPokemonTCGCards.layoutManager = layoutManager
        pokemonTCGCardsAdapter = PokemonTCGCardsAdapter(pokemonTCGCards.toMutableList())
        rvPokemonTCGCards.adapter = pokemonTCGCardsAdapter
        rvPokemonTCGCards.isNestedScrollingEnabled = false
        rvPokemonTCGCards.addOnScrollListener(object : OnEndlessScrollListener() {
            override fun onLoadMore() {
                if (!isLastPage && pokemonTCGCardsAdapter != null && !pokemonTCGCardsAdapter!!.getLoading()) {
                    getSpecificPage(currentPageInIndex)
                }
            }
        })
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
                    navigator.navigateToImageViewer(this, it, test)
                }
        compositeDisposable.add(disposable)
    }

    private fun getSpecificPage(pageNumber: Int) {
         val disposable = pokemonTCGDetailsViewModel.getPokemonTCGCards(pokemonTCGId, pageNumber)
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    if (it.isEmpty()) {
                        isLastPage = true
                        return@subscribe
                    }

                    currentPageInIndex ++
                    this.setupPokemonTCGCardsList(it)
                }

        compositeDisposable.add(disposable)
    }

    private fun getLoading() {
        val disposable = pokemonTCGDetailsViewModel.getPaginationLoading()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe { isLoading -> this.pokemonTCGCardsAdapter?.setLoading(isLoading) }
        compositeDisposable.add(disposable)
    }

    companion object {

        private val TAG = PokemonTCGDetailsActivity::class.java.simpleName

        private const val EXTRA_POKEMON_TCG_ID = "EXTRA_POKEMON_TCG_ID"
        private const val EXTRA_POKEMON_TCG_TITLE = "EXTRA_POKEMON_TCG_TITLE"

        fun getCallingIntent(context: Context, pokemonTCGId: String, pokemonTCGTitle: String): Intent {
            val intent = Intent(context, PokemonTCGDetailsActivity::class.java)
            intent.putExtra(EXTRA_POKEMON_TCG_ID, pokemonTCGId)
            intent.putExtra(EXTRA_POKEMON_TCG_TITLE, pokemonTCGTitle)
            return intent
        }
    }
}
