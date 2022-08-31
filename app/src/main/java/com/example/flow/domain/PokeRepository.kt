package com.example.flow.domain

import com.example.flow.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokeRepository {

    fun pokeFlow(): Flow<List<Pokemon>>
    suspend fun pokeCoroutines(): List<Pokemon>
}