package com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.app.cellstudio.androidkotlincleanboilerplate.BaseApplication
import com.app.cellstudio.androidkotlincleanboilerplate.R
import com.app.cellstudio.androidkotlincleanboilerplate.di.modules.PokemonTCGDetailsModule
import com.app.cellstudio.androidkotlincleanboilerplate.interactor.viewmodel.PokemonTCGDetailsViewModel
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.util.image.BaseImageLoader
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.adapter.PokemonTCGCardsAdapter
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.adapter.PokemonTCGSetsAdapter
import com.app.cellstudio.androidkotlincleanboilerplate.presentation.view.component.OnEndlessScrollListener
import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.stfalcon.imageviewer.StfalconImageViewer
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
    private var imageViewerPokemonTCGCard: StfalconImageViewer<PokemonTCGCard> ?= null

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
                    PokemonTCGSetsAdapter.VIEW_TYPE_MOVIE -> 1
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
                    imageViewerPokemonTCGCard = StfalconImageViewer.Builder<PokemonTCGCard>(this, pokemonTCGCardsAdapter!!.getData(), ::loadPosterImage)
                            .withStartPosition(it)
                            .withImagesMargin(this, R.dimen.image_viewer_padding)
                            .show()
                }
        compositeDisposable.add(disposable)
    }

    private fun loadPosterImage(imageView: ImageView, poster: PokemonTCGCard) {
        BaseImageLoader.getInstance().displayRawImage(poster.imageUrlHiRes, imageView, ImageView.ScaleType.FIT_CENTER)
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
        val disposable = pokemonTCGDetailsViewModel.getIsLoading()
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
