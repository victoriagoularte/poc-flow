package com.example.flow.presentation.flow

import com.example.flow.common.UIInteraction

sealed interface PokeUiInteraction : UIInteraction

class DetailPokemon(val name: String) : PokeUiInteraction
