package com.example.data.repository.dataSource

import com.example.data.dto.response.PokemonDto
import com.example.data.dto.response.PokemonResponseDto
import javax.inject.Inject


class RemotePokemonDataSourceImpl @Inject constructor(
    private val remotePokemonDataSource: RemotePokemonDataSource
) : RemotePokemonDataSource {

    override suspend fun getRemotePokemon(name: String): PokemonDto {
        return remotePokemonDataSource.getRemotePokemon(name)
    }

    override suspend fun getRemotePokemonList(limit: Int, offset: Int): PokemonResponseDto {
        return remotePokemonDataSource.getRemotePokemonList(limit, offset)
    }

}
