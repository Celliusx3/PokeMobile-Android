package com.app.cellstudio.pokemobile.presentation.view.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.databinding.ActivityPokemonTcgDetailsBinding
import com.app.cellstudio.pokemobile.di.modules.PokemonTCGDetailsModule
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.PokemonTCGDetailsViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.ViewModel
import com.app.cellstudio.pokemobile.presentation.service.DownloadService
import com.app.cellstudio.pokemobile.presentation.view.adapter.PokemonTCGCardsAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_pokemon_tcg_details.*
import kotlinx.android.synthetic.main.bottom_sheet_view_pokemon_tcg_card_filter.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class PokemonTCGDetailsActivity : BaseActivity() {
    @Inject
    lateinit var pokemonTCGDetailsViewModel: PokemonTCGDetailsViewModel

    private var pokemonTCGCardsAdapter: PokemonTCGCardsAdapter? = null
    private lateinit var pokemonTCGId: String
    private lateinit var pokemonTCGTitle: String

    private var typesChips: MutableList<Chip> = ArrayList()
    private var subtypesChips: MutableList<Chip> = ArrayList()
    private var supertypesChips: MutableList<Chip> = ArrayList()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>

    override fun getViewModel(): ViewModel? {
        return pokemonTCGDetailsViewModel
    }

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
        setupDownloadButton()
        bottomSheetBehavior = BottomSheetBehavior.from(rlPokemonTCGSetFilter)
    }

    override fun onBindData(view: View?, savedInstanceState: Bundle?) {
        super.onBindData(view, savedInstanceState)

        val binding = DataBindingUtil.bind<ActivityPokemonTcgDetailsBinding>(view!!)
        binding?.viewModel = pokemonTCGDetailsViewModel

        pokemonTCGDetailsViewModel.getAllPokemonTCGCardsInASet(pokemonTCGId)
        getPokemonTCGCardsToShow()
        getFilterSubtypesToShow()
        getFilterSupertypesToShow()
        getFilterTypesToShow()

        val downloadServiceDisposable = pokemonTCGDetailsViewModel.getStartDownloadService()
            .compose(bindToLifecycle())
            .observeOn(getUiScheduler())
            .subscribe {
                DownloadService.startService(this, pokemonTCGId, pokemonTCGTitle)
            }

        onBottomSheetViewClick()
        onBottomSheetViewStateChanged()
        onApplyClicked()
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
            pokemonTCGCardsAdapter!!.refreshData(pokemonTCGCards)
            return
        }

        val spanCount = 2
        val layoutManager = GridLayoutManager(this, spanCount)
        rvPokemonTCGCards.layoutManager = layoutManager
        pokemonTCGCardsAdapter = PokemonTCGCardsAdapter(pokemonTCGCards.toMutableList())
        rvPokemonTCGCards.adapter = pokemonTCGCardsAdapter
        rvPokemonTCGCards.isNestedScrollingEnabled = false
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

    private fun getPokemonTCGCardsToShow() {
         val disposable = pokemonTCGDetailsViewModel.getPokemonTCGCardsToShow()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    rlFilterEmpty.visibility = if (it.isEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                    rvPokemonTCGCards.visibility = if (it.isEmpty()) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                    if (it.isEmpty()) {
                        return@subscribe
                    }
                    this.setupPokemonTCGCardsList(it)
                }

        compositeDisposable.add(disposable)
    }

    private fun getFilterSubtypesToShow() {
        val disposable = pokemonTCGDetailsViewModel.getFilterSubtypesToShow()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    for (type in it) {
                        val chip = Chip(this)
                        chip.text = type
                        chip.setTextColor(ContextCompat.getColor(this, R.color.white))
                        chip.isCheckable = true

                        chip.setOnCheckedChangeListener { _, isChecked ->
                            chip.setChipBackgroundColorResource(if (isChecked) R.color.blue_A200 else R.color.card_view_background)
                        }

                        chip.isChecked = true

                        cgFilterSubType.addView(chip)
                        subtypesChips.add(chip)
                    }
                }

        compositeDisposable.add(disposable)
    }

    private fun getFilterSupertypesToShow() {
        val disposable = pokemonTCGDetailsViewModel.getFilterSupertypesToShow()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    for (type in it) {
                        val chip = Chip(this)
                        chip.text = type
                        chip.setTextColor(ContextCompat.getColor(this, R.color.white))
                        chip.isCheckable = true

                        chip.setOnCheckedChangeListener { _, isChecked ->
                            chip.setChipBackgroundColorResource(if (isChecked) R.color.green_A200 else R.color.card_view_background)
                        }

                        chip.isChecked = true

                        cgFilterCardType.addView(chip)
                        supertypesChips.add(chip)
                    }
                }

        compositeDisposable.add(disposable)
    }

    private fun getFilterTypesToShow() {
        val disposable = pokemonTCGDetailsViewModel.getFilterTypesToShow()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    for (type in it) {
                        val chip = Chip(this)
                        chip.text = type
                        chip.setTextColor(ContextCompat.getColor(this, R.color.white))
                        chip.isCheckable = true

                        chip.setOnCheckedChangeListener { _, isChecked ->
                            chip.setChipBackgroundColorResource(if (isChecked) R.color.pink_A200 else R.color.card_view_background)
                        }

                        chip.isChecked = true

                        cgFilterType.addView(chip)
                        typesChips.add(chip)
                    }
                }

        compositeDisposable.add(disposable)
    }

    /**
     * Bottom Sheet View
     */
    private fun onBottomSheetViewClick () {
        rlPokemonTCGSetFilter.setOnClickListener {
            bottomSheetBehavior.state = if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun onBottomSheetViewStateChanged() {
        bottomSheetBehavior.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        ivFilter.animate().alpha(0.0f)
                                .setDuration(300)
                                .setListener(object: AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator?) {
                                        super.onAnimationEnd(animation)
                                        ivFilter.visibility = View.GONE
                                    }
                                })
                        tvApply.animate().alpha(1.0f)
                                .setDuration(300)
                                .setListener(object: AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator?) {
                                        super.onAnimationEnd(animation)
                                        tvApply.visibility = View.VISIBLE
                                    }
                                })
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        tvApply.animate().alpha(0.0f)
                                .setDuration(300)
                                .setListener(object: AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator?) {
                                        super.onAnimationEnd(animation)
                                        tvApply.visibility = View.GONE
                                    }
                                })
                        ivFilter.animate().alpha(1.0f)
                                .setDuration(300)
                                .setListener(object: AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator?) {
                                        super.onAnimationEnd(animation)
                                        ivFilter.visibility = View.VISIBLE
                                    }
                                })
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })
    }

    private fun onApplyClicked() {
        tvApply.setOnClickListener {
            val typesChecked = ArrayList<String>()
            val subtypesChecked = ArrayList<String>()
            val supertypesChecked = ArrayList<String>()

            for (checkBox in typesChips.filter {it.isChecked}) {
                typesChecked.add(checkBox.text.toString())
            }

            for (checkBox in subtypesChips.filter {it.isChecked}) {
                subtypesChecked.add(checkBox.text.toString())
            }

            for (checkBox in supertypesChips.filter {it.isChecked}) {
                supertypesChecked.add(checkBox.text.toString())
            }

            pokemonTCGDetailsViewModel.onApplyClicked(supertypesChecked, typesChecked, subtypesChecked)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    /**
     * Functions for download
     */
    private fun setupDownloadButton() {
        btnDownload.visibility = View.VISIBLE
        btnDownload.setOnClickListener { pokemonTCGDetailsViewModel.onDownloadButtonClicked() }
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
