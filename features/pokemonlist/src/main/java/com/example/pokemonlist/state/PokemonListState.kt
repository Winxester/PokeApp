package com.example.pokemonlist.state

import com.example.pokemonlist.model.PokemonListItemUI


sealed interface PokemonListState {
    object Loading : PokemonListState
    data class PokemonList(val pokemonListUI: List<PokemonListItemUI>) : PokemonListState
    data class Error(val message: String) : PokemonListState
}