package com.example.data.repository.dataSource

import com.example.data.dto.response.PokemonDto
import com.example.data.dto.response.PokemonResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RemotePokemonDataSource {

    @GET("pokemon/{name}")
    suspend fun getRemotePokemon(
        @Path("name") name: String
    ) : PokemonDto

    @GET("pokemon")
    suspend fun getRemotePokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResponseDto

}