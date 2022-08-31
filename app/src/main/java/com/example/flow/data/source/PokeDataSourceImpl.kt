package com.example.flow.data.source

import com.example.flow.data.model.PokeResultResponse
import com.example.flow.data.service.PokeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeDataSourceImpl(private val service: PokeService) : PokeDataSource {

    override fun pokeFlow(): Flow<PokeResultResponse> = flow {
        emit(service.pokemons())
    }

    override suspend fun pokeCoroutines(): PokeResultResponse = service.pokemons()
}