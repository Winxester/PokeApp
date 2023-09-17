package com.example.pokemon_detail.model


data class PokemonDetailUI(
    val number: Int,
    val name: String,
    val imageUI: ImageUI,
    val statsUI: List<StatUI>,
    val typesUI: List<TypeUI>
)