package com.example.flow.presentation.components

import androidx.recyclerview.widget.RecyclerView
import com.example.flow.databinding.CardPokeItemBinding
import com.example.flow.domain.model.Pokemon

class CardPokeViewHolder(private val binding: CardPokeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Pokemon) = with(binding) {
        tvName.text = item.name
    }
}