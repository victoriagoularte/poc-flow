package com.example.flow.common

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface UIInteraction

class Interaction<Interaction : UIInteraction> {

    private val _uiInteraction = MutableSharedFlow<Interaction>()
    val uiAction: SharedFlow<Interaction> = _uiInteraction.asSharedFlow()

    suspend fun emitInteraction(action: () -> Interaction) {
        _uiInteraction.emit(action())
    }
}
