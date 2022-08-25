package com.example.flow.data.source

import com.example.flow.data.model.PokeResultResponse
import com.example.flow.data.service.PokeService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeDataSourceImpl(private val service: PokeService) : PokeDataSource {

    override fun pokeFlow(): Flow<PokeResultResponse> = flow {
        for(i in 0..2) {
            delay(3000)
            if(i == 2) {
                emit(PokeResultResponse(count = 0, emptyList()))
            } else {
                emit(service.pokemons())
            }
        }
    }

    override fun pokeFlow2(): Flow<PokeResultResponse>  = flow {
        emit(service.pokemons())
    }

    override suspend fun pokeCoroutines(): PokeResultResponse = service.pokemons()
}