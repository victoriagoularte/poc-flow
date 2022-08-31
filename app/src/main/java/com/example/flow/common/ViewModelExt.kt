package com.example.flow.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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

inline fun <T> Flow<T>.repeatExecuteAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline operation: suspend CoroutineScope.(T) -> Unit,
    crossinline onRepeat: () -> Unit,
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) {
        onRepeat.invoke()
        collect {
            operation(it)
        }
    }
}

inline fun <T> Flow<T>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline operation: suspend CoroutineScope.(T) -> Unit,
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) {
        collect {
            operation(it)
        }
    }
}

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
