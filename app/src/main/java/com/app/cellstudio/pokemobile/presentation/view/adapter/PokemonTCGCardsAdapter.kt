package com.app.cellstudio.pokemobile.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.cellstudio.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.databinding.ListItemPokemonTcgCardBinding
import io.reactivex.subjects.PublishSubject

class PokemonTCGCardsAdapter(private val models: MutableList<PokemonTCGCard>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var loading: Boolean = false
    private val selectedModel = PublishSubject.create<Int>()

    class ViewHolder : RecyclerView.ViewHolder {

        lateinit var binding: ListItemPokemonTcgCardBinding

        constructor(view: View) : super(view)

        constructor(binding: ListItemPokemonTcgCardBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil
                .inflate<ListItemPokemonTcgCardBinding>(layoutInflater, R.layout.list_item_pokemon_tcg_card, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(baseHolder: RecyclerView.ViewHolder, position: Int) {
        if (baseHolder is ViewHolder) {
            val model = models[position]
            baseHolder.binding.model = model
            baseHolder.binding.setListener {
                val pos = baseHolder.adapterPosition
                if (pos >= 0) {
                    selectedModel.onNext(pos)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getData(): List<PokemonTCGCard> {
        return models
    }

    fun updateData(models: List<PokemonTCGCard>) {
        val start = this.models.size
        this.models.addAll(models)
        val newItemCount = models.size
        notifyItemRangeInserted(start, newItemCount)
    }

    fun emptyData() {
        this.models.clear()
        notifyDataSetChanged()
    }

    fun refreshData(models: List<PokemonTCGCard>) {
        this.emptyData()
        this.models.addAll(models)
        notifyItemRangeInserted(0, models.size)
    }

    fun getSelectedModel(): PublishSubject<Int> {
        return selectedModel
    }

    companion object {
        const val VIEW_TYPE_DATA = 0
        const val VIEW_TYPE_LOADING = 1
    }
}
