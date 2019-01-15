package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.impl

import androidx.databinding.ObservableBoolean
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import com.app.cellstudio.pokemobile.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.HomeViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class HomeViewModelImpl(private val pokemonTCGInteractor: PokemonTCGInteractor,
                        scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), HomeViewModel {

    private val isLoading = ObservableBoolean(false)
    private val pokemonTCGSetsToShow = PublishSubject.create<List<PokemonTCGSet>>()

    private var allPokemonTCGSets: List<PokemonTCGSet> ?= null

    override fun onCreateView() {
        super.onCreateView()

        isLoading.set(true)
        val disposable = pokemonTCGInteractor.getAllPokemonTCGSets(true)
                .doFinally { isLoading.set(false) }
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe { pokemonTCGSetsToShow.onNext(it)
                    allPokemonTCGSets = it
                }
        subscriptions.add(disposable)
    }

    override fun getPokemonTCGSetsToShow(): Observable<List<PokemonTCGSet>> {
        return pokemonTCGSetsToShow
    }

    override fun getFilterLegalToShow(): Observable<List<String>> {
        return pokemonTCGInteractor.getFilterLegalToShow()
    }

    override fun getFilterSeriesToShow(): Observable<List<String>> {
        return pokemonTCGInteractor.getFilterSeriesToShow()
    }

    override fun getIsLoading(): ObservableBoolean {
        return isLoading
    }

    override fun onApplyClicked(filterLegal: List<String>, filterSeries: List<String>) {
        if (allPokemonTCGSets != null) {
            val legalFiltered = applyLegalFilter(allPokemonTCGSets!!, filterLegal)
            val seriesFiltered = applySeriesFilter(legalFiltered, filterSeries)
            pokemonTCGSetsToShow.onNext(seriesFiltered)
        }
    }

    private fun applyLegalFilter(pokemonTCGSets: List<PokemonTCGSet>, legalToShow: List<String>): List<PokemonTCGSet> {
        return pokemonTCGSets.filter {
            var shouldShow = false
            for (legal in legalToShow) {
                when (legal) {
                    "Standard" -> shouldShow =  shouldShow or it.standardLegal
                    "Expanded" ->  shouldShow =  shouldShow or it.expandedLegal
                    "None" -> shouldShow =  shouldShow or (!it.standardLegal && !it.expandedLegal)
                }
            }
            shouldShow
        }
    }

    private fun applySeriesFilter(pokemonTCGSets: List<PokemonTCGSet>, seriesToShow: List<String>): List<PokemonTCGSet> {
        return pokemonTCGSets.filter { seriesToShow.contains(it.series) }
    }

}