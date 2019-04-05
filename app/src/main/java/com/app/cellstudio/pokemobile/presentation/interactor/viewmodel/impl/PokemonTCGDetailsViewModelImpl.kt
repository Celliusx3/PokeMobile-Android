package com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.impl

import androidx.databinding.ObservableBoolean
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.domain.interactor.PokemonTCGInteractor
import com.app.cellstudio.pokemobile.presentation.interactor.scheduler.BaseSchedulerProvider
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.BaseViewModel
import com.app.cellstudio.pokemobile.presentation.interactor.viewmodel.PokemonTCGDetailsViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PokemonTCGDetailsViewModelImpl(private val pokemonTCGInteractor: PokemonTCGInteractor,
                                     scheduler: BaseSchedulerProvider) : BaseViewModel(scheduler), PokemonTCGDetailsViewModel {
    private val isLoading = ObservableBoolean(false)
    private val pokemonTCGCardsToShow = PublishSubject.create<List<PokemonTCGCard>>()
    private val filterTypesToShow = PublishSubject.create<List<String>>()
    private val filterSubtypesToShow = PublishSubject.create<List<String>>()
    private val filterSupertypesToShow = PublishSubject.create<List<String>>()
    private val startDownloadService = PublishSubject.create<Unit>()

    private var allPokemonTCGCardsInASet: List<PokemonTCGCard> ?= null

    override fun onCreateView() {
        super.onCreateView()
        val typesDisposable = pokemonTCGInteractor.getFilterTypesToShow()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe { filterTypesToShow.onNext(it) }
        subscriptions.add(typesDisposable)

        val subtypesDisposable = pokemonTCGInteractor.getFilterSubtypesToShow()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe { filterSubtypesToShow.onNext(it) }
        subscriptions.add(subtypesDisposable)

        val supertypesDisposable = pokemonTCGInteractor.getFilterSupertypesToShow()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe { filterSupertypesToShow.onNext(it) }
        subscriptions.add(supertypesDisposable)
    }

    override fun getPokemonTCGCardsToShow(): Observable<List<PokemonTCGCard>> {
        return pokemonTCGCardsToShow
    }

    override fun getIsLoading(): ObservableBoolean {
        return isLoading
    }

    override fun getAllPokemonTCGCardsInASet(set: String) {
        isLoading.set(true)
        val disposable = pokemonTCGInteractor.getAllPokemonTCGCards(set, true)
                .doFinally { isLoading.set(false) }
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe ({ pokemonTCGCardsToShow.onNext(it)
                    allPokemonTCGCardsInASet = it
                }, {
                    getAllPokemonTCGCardsInASetOffline(set)
                })
        subscriptions.add(disposable)
    }

    override fun getFilterSupertypesToShow(): Observable<List<String>> {
        return filterSupertypesToShow
    }

    override fun getFilterSubtypesToShow(): Observable<List<String>> {
        return filterSubtypesToShow
    }

    override fun getFilterTypesToShow(): Observable<List<String>> {
        return filterTypesToShow
    }

    override fun onApplyClicked(filterCardType: List<String>, filterType: List<String>, filterSubtype: List<String>) {
        if (allPokemonTCGCardsInASet != null) {
            val cardTypeFiltered = applyCardTypeFilter(allPokemonTCGCardsInASet!!, filterCardType)
            val typeFiltered = applyTypeFilter(cardTypeFiltered, filterType)
            val subtypeFiltered = applySubTypeFilter(typeFiltered, filterSubtype)

            pokemonTCGCardsToShow.onNext(subtypeFiltered)
        }
    }

    override fun getStartDownloadService(): Observable<Unit> {
        return startDownloadService
    }

    override fun onDownloadButtonClicked() {
        startDownloadService.onNext(Unit)

    }

    private fun applyCardTypeFilter(pokemonTCGCards: List<PokemonTCGCard>, cardsTypeToShow: List<String>): List<PokemonTCGCard> {
        return pokemonTCGCards.filter {cardsTypeToShow.contains(it.supertype)}
    }

    private fun applyTypeFilter(pokemonTCGCards: List<PokemonTCGCard>, typeToShow: List<String>): List<PokemonTCGCard> {
        return pokemonTCGCards.filter { card ->
            // Empty for trainer and energy or type for pokemon
            card.types.isEmpty() || typeToShow.any{ card.types.contains(it) } }
    }

    private fun applySubTypeFilter(pokemonTCGCards: List<PokemonTCGCard>, cardsTypeToShow: List<String>): List<PokemonTCGCard> {
        return pokemonTCGCards.filter {cardsTypeToShow.contains(it.subtype)}
    }

    private fun getAllPokemonTCGCardsInASetOffline(set: String) {
        isLoading.set(true)
        val disposable = pokemonTCGInteractor.getAllPokemonTCGCards(set, false)
                .doFinally { isLoading.set(false) }
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe ({ pokemonTCGCardsToShow.onNext(it)
                    allPokemonTCGCardsInASet = it
                }, {
                    it.printStackTrace()
                })
        subscriptions.add(disposable)
    }
}
