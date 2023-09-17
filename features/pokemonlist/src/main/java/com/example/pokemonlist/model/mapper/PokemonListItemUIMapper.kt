package com.example.pokemonlist.model.mapper

import com.example.core.constants.Constants.Companion.IMAGE_BASE_URL
import com.example.domain.model.PokemonListItem
import com.example.pokemonlist.model.PokemonListItemUI

class PokemonListItemUIMapper {

    fun toPokemonUIList(pokemonListItemList: List<PokemonListItem>): List<PokemonListItemUI> =
        pokemonListItemList.map {
            PokemonListItemUI(
                it.number,
                it.name,
                "$IMAGE_BASE_URL${it.number}.png"
            )
        }

}