package com.example.data.dto.mapper

import com.example.core.constants.Constants.Companion.ONE
import com.example.data.dto.response.PokemonDto
import com.example.domain.model.PokemonListItem
import com.example.data.dto.response.PokemonResponseDto
import com.example.data.dto.response.ImageDto
import com.example.data.dto.response.StatDto
import com.example.data.dto.response.StatXDto
import com.example.data.dto.response.TypeDto
import com.example.data.dto.response.TypeXDto
import com.example.domain.model.PokemonDetail
import com.example.domain.model.Image
import com.example.domain.model.Stat
import com.example.domain.model.StatX
import com.example.domain.model.Type
import com.example.domain.model.TypeX


class PokemonDataMapper {

    fun toPokemonDomain(pokemonDto: PokemonDto) : PokemonDetail =
        PokemonDetail(
            pokemonDto.id,
            pokemonDto.name,
            toPokemonDomainSprites(pokemonDto.imageDto),
            toPokemonDomainStatList(pokemonDto.statDtos),
            toPokemonDomainTypeList(pokemonDto.typeDtos)
        )

    private fun toPokemonDomainSprites(imageDto: ImageDto) :Image =
        Image (
            imageDto.frontDefault
        )

    private fun toPokemonDomainStatList(statDtos: List<StatDto>): List<Stat> =
        statDtos.map {
            Stat(it.base_stat, it.effort, toPokemonDomainStatX(it.stat))
        }

    private fun toPokemonDomainStatX(statXDto: StatXDto) :StatX =
        StatX (
            statXDto.name,
            statXDto.url
        )

    private fun toPokemonDomainTypeList(typeDtos: List<TypeDto>): List<Type> =
        typeDtos.map {
            Type(
                it.slot,
                toPokemonDomainTypeX(it.type)
            )
        }

    private fun toPokemonDomainTypeX(typeXDto: TypeXDto) :TypeX =
        TypeX(
            typeXDto.name,
            typeXDto.url
        )

    fun toPokemonDomainList(pokemonResponseDto: PokemonResponseDto): List<PokemonListItem> =
        pokemonResponseDto.pokemonList?.mapIndexed { index, pokemon ->
            PokemonListItem(
                index + ONE,
                pokemon.name,
                pokemon.imageUrl
            )
        } ?: emptyList()

}