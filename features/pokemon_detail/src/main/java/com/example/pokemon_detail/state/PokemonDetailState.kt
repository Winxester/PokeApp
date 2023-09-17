package com.example.pokemon_detail.state

import com.example.pokemon_detail.model.PokemonDetailUI

sealed interface PokemonDetailState {
    object Loading : PokemonDetailState
    data class PokemonDetail(val pokemonDetail: PokemonDetailUI?) : PokemonDetailState
    data class Error(val message: String) : PokemonDetailState
}