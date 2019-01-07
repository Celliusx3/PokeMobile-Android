package com.app.cellstudio.pokemobile.presentation.view.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.app.cellstudio.domain.entity.PokemonTCGSet
import com.app.cellstudio.pokemobile.BaseApplication
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.databinding.FragmentHomeBinding
import com.app.cellstudio.pokemobile.di.modules.HomeModule
import com.app.cellstudio.pokemobile.interactor.viewmodel.HomeViewModel
import com.app.cellstudio.pokemobile.interactor.viewmodel.ViewModel
import com.app.cellstudio.pokemobile.presentation.view.adapter.PokemonTCGSetsAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.bottom_sheet_view_pokemon_tcg_set_filter.*
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {
    @Inject
    lateinit var homeViewModel: HomeViewModel

    private var pokemonTCGSetsAdapter: PokemonTCGSetsAdapter? = null

    private var seriesChips: MutableList<Chip> = ArrayList()
    private var legalChips: MutableList<Chip> = ArrayList()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>

    override fun getViewModel(): ViewModel {
        return homeViewModel
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_home
    }

    override fun onInject() {
        BaseApplication.getInstance()
                .getApplicationComponent()
                .plus(HomeModule())
                .inject(this)
    }

    override fun onBindView(view: View?) {
        super.onBindView(view)
        bottomSheetBehavior = BottomSheetBehavior.from(rlPokemonTCGSetFilter)

    }

    override fun onBindData(view: View?) {
        super.onBindData(view)

        val binding = DataBindingUtil.bind<FragmentHomeBinding>(view!!)
        binding?.viewModel = homeViewModel

        getPokemonTCGSetsToShow()
        getFilterLegalToShow()
        getFilterSeriesToShow()

        onBottomSheetViewClick ()
        onBottomSheetViewStateChanged()
        onApplyClicked()

    }

    override fun onResume() {
        super.onResume()
        subscribeSelectedModel()
    }

    private fun setupPokemonTCGSetsList(pokemonTCGSets: List<PokemonTCGSet>) {
        if (pokemonTCGSetsAdapter != null) {
            pokemonTCGSetsAdapter!!.refreshData(pokemonTCGSets)
            return
        }

        val spanCount = 2
        val layoutManager = GridLayoutManager(context, spanCount)
        rvHomeItem.layoutManager = layoutManager
        pokemonTCGSetsAdapter = PokemonTCGSetsAdapter(pokemonTCGSets.toMutableList())
        pokemonTCGSetsAdapter!!.setHasStableIds(true)
        rvHomeItem.adapter = pokemonTCGSetsAdapter
        rvHomeItem.isNestedScrollingEnabled = false
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

    private fun getPokemonTCGSetsToShow() {
        val disposable = homeViewModel.getPokemonTCGSetsToShow()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    this.setupPokemonTCGSetsList(it) }

        compositeDisposable.add(disposable)
    }

    private fun getFilterSeriesToShow() {
        val disposable = homeViewModel.getFilterSeriesToShow()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    for (series in it) {

                        val chip = Chip(context)
                        chip.text = series
                        chip.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                        chip.isCheckable = true

                        chip.setOnCheckedChangeListener { _, isChecked ->
                            chip.setChipBackgroundColorResource(if (isChecked) R.color.blue_A200 else R.color.card_view_background)
                        }

                        chip.isChecked = true

                        cgFilterSeries.addView(chip)
                        seriesChips.add(chip)
                    }
                }

        compositeDisposable.add(disposable)
    }

    private fun getFilterLegalToShow() {
        val disposable = homeViewModel.getFilterLegalToShow()
                .compose(bindToLifecycle())
                .subscribeOn(getIoScheduler())
                .observeOn(getUiScheduler())
                .subscribe {
                    for (legal in it) {
                        val chip = Chip(context)
                        chip.text = legal
                        chip.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                        chip.isCheckable = true

                        chip.setOnCheckedChangeListener { _, isChecked ->
                            chip.setChipBackgroundColorResource(if (isChecked) R.color.pink_A200 else R.color.card_view_background)
                        }

                        chip.isChecked = true

                        cgFilterLegal.addView(chip)
                        legalChips.add(chip)
                    }
                }

        compositeDisposable.add(disposable)
    }

    private fun onApplyClicked() {
        tvApply.setOnClickListener {
            val seriesChecked = ArrayList<String>()
            for (checkBox in seriesChips.filter {it.isChecked}) {
                seriesChecked.add(checkBox.text.toString())
            }

            val legalChecked = ArrayList<String>()

            for (checkBox in legalChips.filter {it.isChecked}) {
                legalChecked.add(checkBox.text.toString())
            }

            homeViewModel.onApplyClicked(legalChecked, seriesChecked)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    /**
     * Bottom Sheet View
     */
    private fun onBottomSheetViewClick () {
        rlFilterMain.setOnClickListener {
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