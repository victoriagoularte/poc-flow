package com.example.flow.data.repository

import com.example.flow.data.model.PokeResultResponse
import com.example.flow.data.source.PokeDataSource
import com.example.flow.domain.PokeRepository
import com.example.flow.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class PokeRepositoryImpl(private val dataSource: PokeDataSource) : PokeRepository {

    override fun pokeFlow(): Flow<List<Pokemon>> {
        val pokeListResponse = dataSource.pokeFlow()
        return pokeListResponse
            .map { response ->
                response.toDomain()
            }
    }

    override fun pokeFlow2(): Flow<List<Pokemon>> {
        val pokeListResponse = dataSource.pokeFlow2()
        return pokeListResponse
            .map { response ->
                response.toDomain().reversed()
            }
    }

    override suspend fun pokeCoroutines(): List<Pokemon> {
        return try {
            dataSource.pokeCoroutines().toDomain()
        } catch (e: IOException) {
            throw e
        }
    }
}

fun PokeResultResponse.toDomain() = pokemons.map { Pokemon(it.name, it.url) }
