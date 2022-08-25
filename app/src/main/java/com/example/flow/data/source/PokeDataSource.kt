package com.example.flow.data.source

import com.example.flow.data.model.PokeResultResponse
import kotlinx.coroutines.flow.Flow

interface PokeDataSource {

    fun pokeFlow(): Flow<PokeResultResponse>
    fun pokeFlow2(): Flow<PokeResultResponse>
    suspend fun pokeCoroutines(): PokeResultResponse
}