package com.example.flow.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class ViewModel<State : UIState, Interaction : UIInteraction>(
    initialState: State
) : ViewModel() {

    private val viewModelState = State(initialState)
    private val viewModelAction = Interaction<Interaction>()

    val state: StateFlow<State> = viewModelState.uiState
    val action: SharedFlow<Interaction> = viewModelAction.uiAction

    protected fun setState(state: (State) -> State) {
        viewModelState.updateState(state)
    }

    protected open fun sendInteraction(interaction: () -> Interaction) {
        viewModelScope.launch { viewModelAction.emitInteraction(interaction) }
    }
}