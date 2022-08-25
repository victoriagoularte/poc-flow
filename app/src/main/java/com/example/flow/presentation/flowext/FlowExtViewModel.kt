package com.example.flow.presentation.flowext

import androidx.lifecycle.viewModelScope
import com.example.flow.common.ViewModel
import com.example.flow.domain.usecase.FlowUseCase
import com.example.flow.presentation.flow.DetailPokemon
import com.example.flow.presentation.flow.PokeUiInteraction
import com.example.flow.presentation.flow.PokeUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FlowExtViewModel(
    private val useCase: FlowUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel<PokeUiState, PokeUiInteraction>(PokeUiState()) {

    init {
        getPokemons()
    }

    fun getPokemons() {
        viewModelScope.launch {
            useCase()
                .flowOn(dispatcher)
                .onStart { setState { PokeUiState(isLoading = true) } }
                .catch {
                    setState {
                        PokeUiState(isLoading = false, pokemons = null, hasError = true)
                    }
                }.collect { pokeListResult ->
                    setState {
                        PokeUiState(isLoading = false, pokemons = pokeListResult, hasError = true)
                    }
                }
        }
    }

    fun detailPokemon(pokeName: String) {
        sendInteraction { DetailPokemon(pokeName) }
    }
}