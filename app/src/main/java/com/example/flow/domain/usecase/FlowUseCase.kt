package com.example.flow.domain.usecase

import com.example.flow.domain.PokeRepository
import com.example.flow.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip

class FlowUseCase(private val repository: PokeRepository) {

    operator fun invoke(): Flow<List<Pokemon>> {
        return repository.pokeFlow().combine(repository.pokeFlow2()) { first, second ->
            val resultList = mutableListOf<Pokemon>()
            resultList.addAll(first)
            resultList.addAll(second)
            resultList
        }
    }
}