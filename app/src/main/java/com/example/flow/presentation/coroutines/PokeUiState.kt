package com.example.flow.presentation.coroutines

import com.example.flow.domain.model.Pokemon

data class PokeUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val pokemons: List<Pokemon>? = null,
) {
    fun showLoading() =
        copy(isLoading = true, hasError = false, pokemons = null)

    fun showPokemons(result: List<Pokemon>) =
        copy(isLoading = false, hasError = false, pokemons = result)

    fun showError() = copy(isLoading = false, hasError = true, pokemons = null)
}
