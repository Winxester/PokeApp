package com.example.data.dto.response

import com.google.gson.annotations.SerializedName


data class PokemonDto(
    val id: Int,
    val name: String,
    @SerializedName("sprites")
    val imageDto: ImageDto,
    @SerializedName("stats")
    val statDtos: List<StatDto>,
    @SerializedName("types")
    val typeDtos: List<TypeDto>,
)