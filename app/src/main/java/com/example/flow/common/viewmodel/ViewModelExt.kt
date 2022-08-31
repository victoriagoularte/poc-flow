package com.example.flow.common

import androidx.appcompat.app.AppCompatActivity
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
        this,
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
        this,
        operation = { state -> handleStates(state) },
        onRepeat = onRepeat
    )
}

inline fun <reified State : UIState, reified Effect : UIEffect> AppCompatActivity.onInteraction(
    viewModel: ViewModel<State, Effect>,
    crossinline handleEvents: (Effect) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) {
    viewModel.effect.launchAndCollectIn(this, operation = { event -> handleEvents(event) })
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