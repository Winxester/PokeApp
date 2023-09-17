package com.example.domain.use_cases

import com.example.core.resources.Resource
import com.example.domain.model.PokemonDetail
import com.example.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonDetail @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(
        name: String
    ): Flow<Resource<PokemonDetail>> =
        pokemonRepository.getPokemon(name)

}