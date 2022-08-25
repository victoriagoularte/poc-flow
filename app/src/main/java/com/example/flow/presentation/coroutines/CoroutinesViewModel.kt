package com.example.flow.presentation.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow.domain.usecase.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoroutinesViewModel(
    private val useCase: CoroutineUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokeUiState())
    val uiState: StateFlow<PokeUiState> = _uiState.asStateFlow()

    private val _uiAction = MutableSharedFlow<PokeUiAction>()
    val uiAction: SharedFlow<PokeUiAction> = _uiAction.asSharedFlow()

    fun getPokemons() {
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.showLoading() }
            try {
                val result = useCase()
                _uiState.update { it.showPokemons(result) }
            } catch (e: Exception) {
                _uiState.update { it.showError() }
            }
        }
    }


    fun onClickItemList(idPoke: Long) {
        viewModelScope.launch {
            _uiAction.emit(PokeUiAction.ShowDetail(idPoke))
        }
    }

}