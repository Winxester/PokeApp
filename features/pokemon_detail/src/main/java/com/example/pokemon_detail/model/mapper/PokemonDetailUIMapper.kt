package com.example.pokemon_detail.model.mapper

import com.example.domain.model.PokemonDetail
import com.example.domain.model.Image
import com.example.domain.model.Stat
import com.example.domain.model.StatX
import com.example.domain.model.Type
import com.example.domain.model.TypeX
import com.example.pokemon_detail.model.PokemonDetailUI
import com.example.pokemon_detail.model.ImageUI
import com.example.pokemon_detail.model.StatUI
import com.example.pokemon_detail.model.StatXUI
import com.example.pokemon_detail.model.TypeUI
import com.example.pokemon_detail.model.TypeXUI


class PokemonDetailUIMapper {

    fun toPokemonDetailUI(
        pokemonDetail: PokemonDetail
    ): PokemonDetailUI =
        PokemonDetailUI(
            pokemonDetail.number,
            pokemonDetail.name,
            toPokemonSpritesUI(pokemonDetail.pokemonImage),
            toPokemonStatUIList(pokemonDetail.stats),
            toPokemonTypeListUI(pokemonDetail.types)
        )

    private fun toPokemonSpritesUI(pokemonImage: Image): ImageUI =
        ImageUI(
            pokemonImage.frontDefault
        )

    private fun toPokemonStatUIList(stats: List<Stat>): List<StatUI> =
        stats.map {
            StatUI(it.baseStat, it.effort, toPokemonStatXUI(it.stat))
        }

    private fun toPokemonStatXUI(statX: StatX): StatXUI =
        StatXUI(
            statX.name,
            statX.url
        )

    private fun toPokemonTypeListUI(types: List<Type>): List<TypeUI> =
        types.map {
            TypeUI(
                it.slot,
                toPokemonTypeXUI(it.type)
            )
        }

    private fun toPokemonTypeXUI(typeX: TypeX): TypeXUI =
        TypeXUI(
            typeX.name,
            typeX.url
        )

}