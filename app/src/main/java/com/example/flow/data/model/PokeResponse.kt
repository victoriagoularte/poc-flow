package com.example.flow.data.model

import com.google.gson.annotations.SerializedName

data class PokeResultResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("results")
    val pokemons: List<PokeResponse>,
)

data class PokeResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String,
)
