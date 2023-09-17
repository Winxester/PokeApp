package com.example.domain.use_cases

import com.example.core.resources.Resource
import com.example.domain.model.PokemonListItem
import com.example.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonList @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(
        limit: Int,
        offset: Int
    ): Flow<Resource<List<PokemonListItem>>> =
        pokemonRepository.getRemotePokemonList(limit, offset)
}