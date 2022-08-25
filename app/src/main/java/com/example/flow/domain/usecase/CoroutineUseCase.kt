package com.example.flow.domain.usecase

import com.example.flow.domain.PokeRepository
import com.example.flow.domain.model.Pokemon

class CoroutineUseCase(private val repository: PokeRepository) {

    suspend operator fun invoke(): List<Pokemon> = repository.pokeCoroutines()
}