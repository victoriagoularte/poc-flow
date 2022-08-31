package com.example.flow.common.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.example.flow.common.viewmodel.UIEffect
import com.example.flow.common.viewmodel.UIState
import com.example.flow.common.viewmodel.ViewModel

inline fun <reified State : UIState, reified Effect : UIEffect> AppCompatActivity.onStateChange(
    viewModel: ViewModel<State, Effect>,
    crossinline handleStates: (State) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.state.launchAndCollectIn(
        owner = this,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) }
    )
}

inline fun <reified State : UIState, reified Effect : UIEffect> AppCompatActivity.onStateChange(
    viewModel: ViewModel<State, Effect>,
    crossinline handleStates: (State) -> Unit,
    crossinline onRepeat: () -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.state.repeatExecuteAndCollectIn(
        owner = this,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) },
        onRepeat = onRepeat
    )
}

inline fun <reified State : UIState, reified Effect : UIEffect> AppCompatActivity.onInteraction(
    viewModel: ViewModel<State, Effect>,
    crossinline handleEvents: (Effect) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.effect.launchAndCollectIn(
        owner = this,
        minActiveState = minActiveState,
        operation = { event -> handleEvents(event) })
}
