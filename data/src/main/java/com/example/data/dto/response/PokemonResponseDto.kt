package com.example.data.dto.response

import com.google.gson.annotations.SerializedName


data class PokemonResponseDto(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: Any?,
    @SerializedName("results") val pokemonList: List<PokemonListItemDto>?
)