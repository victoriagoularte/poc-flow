package com.example.flow.presentation.flowext

import androidx.lifecycle.viewModelScope
import com.example.flow.common.viewmodel.ViewModel
import com.example.flow.domain.usecase.FlowUseCase
import com.example.flow.presentation.flow.DetailPokemon
import com.example.flow.presentation.flow.PokeUiEffects
import com.example.flow.presentation.flow.PokeUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class FlowExtViewModel(
    private val useCase: FlowUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel<PokeUiState, PokeUiEffects>(PokeUiState()) {

    fun getPokemons() {
        useCase()
            .flowOn(dispatcher)
            .onStart { setState { it.showLoading() } }
            .catch {
                setState { it.showError() }
            }.onEach { pokeListResult ->
                setState { it.showPokemons(pokeListResult) }
            }.launchIn(viewModelScope)
    }

    fun detailPokemon(pokeName: String) {
        sendEffect { DetailPokemon(pokeName) }
    }
}