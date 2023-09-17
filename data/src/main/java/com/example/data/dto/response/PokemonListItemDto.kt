package com.example.data.dto.response

import com.google.gson.annotations.SerializedName


data class PokemonListItemDto(
    @SerializedName("name") val name: String,
    @SerializedName("url") val imageUrl: String
)