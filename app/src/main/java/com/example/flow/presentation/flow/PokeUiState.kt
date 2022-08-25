package com.example.flow.presentation.flow

import com.example.flow.common.UIState
import com.example.flow.domain.model.Pokemon

data class PokeUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val pokemons: List<Pokemon>? = null,
) : UIState {
    fun showLoading() =
        copy(isLoading = true, hasError = false, pokemons = null)

    fun showPokemons(pokemons: List<Pokemon>) =
        copy(isLoading = false, hasError = false, pokemons = pokemons)

    fun showError() = copy(isLoading = false, hasError = true, pokemons = null)
}
