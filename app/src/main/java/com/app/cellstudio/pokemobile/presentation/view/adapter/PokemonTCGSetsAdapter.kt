package com.app.cellstudio.pokemobile.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGSet
import com.app.cellstudio.pokemobile.R
import com.app.cellstudio.pokemobile.databinding.ListItemPokemonTcgSetBinding
import io.reactivex.subjects.PublishSubject

class PokemonTCGSetsAdapter(private val models: MutableList<PokemonTCGSet>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val selectedModel = PublishSubject.create<PokemonTCGSet>()

    class ViewHolder : RecyclerView.ViewHolder {

        lateinit var binding: ListItemPokemonTcgSetBinding

        constructor(view: View) : super(view)

        constructor(binding: ListItemPokemonTcgSetBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil
                .inflate<ListItemPokemonTcgSetBinding>(layoutInflater, R.layout.list_item_pokemon_tcg_set, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(baseHolder: RecyclerView.ViewHolder, position: Int) {
        if (baseHolder is ViewHolder) {
            baseHolder.binding.model = models[position]
            baseHolder.binding.setListener {
                val pos = baseHolder.adapterPosition
                if (pos >= 0) {
                    selectedModel.onNext(models[pos])
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

    fun updateData(models: List<PokemonTCGSet>) {
        val start = this.models.size
        this.models.addAll(models)
        val newItemCount = models.size
        notifyItemRangeInserted(start, newItemCount)
    }

    fun emptyData() {
        this.models.clear()
        notifyDataSetChanged()
    }

    fun refreshData(models: List<PokemonTCGSet>) {
        this.emptyData()
        this.models.addAll(models)
        notifyItemRangeInserted(0, models.size)
    }

    fun getSelectedModel(): PublishSubject<PokemonTCGSet> {
        return selectedModel
    }
}
