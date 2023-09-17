package com.example.data.repository

import com.example.core.resources.Resource
import com.example.data.dto.mapper.PokemonDataMapper
import com.example.data.repository.dataSource.RemotePokemonDataSource
import com.example.domain.model.PokemonDetail
import com.example.domain.model.PokemonListItem
import com.example.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.Exception

class PokemonRepositoryImpl @Inject constructor(
    private val remotePokemonDataSource: RemotePokemonDataSource
) : PokemonRepository {

    override suspend fun getPokemon(
        pokemonName: String
    ): Flow<Resource<PokemonDetail>> = flow {
        try {
            emit(Resource.Loading())
            val pokemon = PokemonDataMapper().toPokemonDomain(
                remotePokemonDataSource.getRemotePokemon(pokemonName)
            )
            emit(Resource.Success(pokemon))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage))
        }
    }

    override suspend fun getRemotePokemonList(
        limit: Int,
        offset: Int
    ): Flow<Resource<List<PokemonListItem>>> = flow {
        try {
            emit(Resource.Loading())
            val pokemonList = PokemonDataMapper().toPokemonDomainList(
                remotePokemonDataSource.getRemotePokemonList(
                    limit,
                    offset
                )
            )
            emit(Resource.Success(pokemonList))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage))
        }
    }

}

