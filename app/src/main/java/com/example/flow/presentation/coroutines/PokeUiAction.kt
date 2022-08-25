package com.example.flow.presentation.coroutines

sealed class PokeUiAction {
    data class ShowDetail(val idPoke: Long) : PokeUiAction()
}
