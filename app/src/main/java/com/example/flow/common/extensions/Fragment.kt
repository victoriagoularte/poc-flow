package com.example.flow.common.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.flow.common.viewmodel.UIEffect
import com.example.flow.common.viewmodel.UIState
import com.example.flow.common.viewmodel.ViewModel

inline fun <reified State : UIState, reified Effect : UIEffect> Fragment.onStateChange(
    viewModel: ViewModel<State, Effect>,
    crossinline handleStates: (State) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.state.launchAndCollectIn(
        owner = viewLifecycleOwner,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) }
    )
}

inline fun <reified State : UIState, reified Effect : UIEffect> Fragment.onStateChange(
    viewModel: ViewModel<State, Effect>,
    crossinline handleStates: (State) -> Unit,
    crossinline onRepeat: () -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.state.repeatExecuteAndCollectIn(
        owner = viewLifecycleOwner,
        minActiveState = minActiveState,
        operation = { state -> handleStates(state) },
        onRepeat = onRepeat
    )
}

inline fun <reified State : UIState, reified Effect : UIEffect> Fragment.onInteraction(
    viewModel: ViewModel<State, Effect>,
    crossinline handleEvents: (Effect) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.effect.launchAndCollectIn(
        owner = viewLifecycleOwner,
        minActiveState = minActiveState,
        operation = { event -> handleEvents(event) })
}