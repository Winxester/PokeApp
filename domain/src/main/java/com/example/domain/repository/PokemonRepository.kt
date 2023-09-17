package com.example.domain.repository

import com.example.core.resources.Resource
import com.example.domain.model.PokemonDetail
import com.example.domain.model.PokemonListItem
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemon(
        pokemonName: String
    ): Flow<Resource<PokemonDetail>>

    suspend fun getRemotePokemonList(
        limit: Int, offset: Int
    ): Flow<Resource<List<PokemonListItem>>>

}