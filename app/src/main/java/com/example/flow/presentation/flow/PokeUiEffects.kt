package com.example.flow.presentation.flow

import com.example.flow.common.viewmodel.UIEffect

sealed interface PokeUiEffects : UIEffect

class DetailPokemon(val name: String) : PokeUiEffects
