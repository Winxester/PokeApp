package com.example.domain.model


data class PokemonDetail(
    val number: Int,
    val name: String,
    val pokemonImage: Image,
    val stats: List<Stat>,
    val types: List<Type>
)