package com.example.flow.presentation.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow.domain.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FlowViewModel(
    private val useCase: FlowUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableSharedFlow<PokeUiState>()
    val uiState: SharedFlow<PokeUiState> = _uiState.asSharedFlow()

    init {
        getPokemons()
    }

    fun getPokemons() {
        viewModelScope.launch {
            useCase()
                .flowOn(dispatcher)
                .onStart { _uiState.emit(PokeUiState(isLoading = true)) }
                .catch { _uiState.emit(PokeUiState(isLoading = false, hasError = true)) }
                .collect { pokeListResult ->
                    _uiState.emit(
                        PokeUiState(
                            isLoading = false,
                            hasError = false,
                            pokemons = pokeListResult
                        )
                    )
                }
        }
    }

    fun clickButton() {

    }

}