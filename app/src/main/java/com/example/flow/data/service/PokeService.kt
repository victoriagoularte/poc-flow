package com.example.flow.data.service

import com.example.flow.data.model.PokeResultResponse
import retrofit2.http.GET

interface PokeService {

    @GET("/api/v2/pokemon")
    suspend fun pokemons(): PokeResultResponse
}