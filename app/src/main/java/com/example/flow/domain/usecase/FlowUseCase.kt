package com.example.flow.domain.usecase

import com.example.flow.domain.PokeRepository
import com.example.flow.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

class FlowUseCase(private val repository: PokeRepository) {

    operator fun invoke(): Flow<List<Pokemon>> = repository.pokeFlow()
}