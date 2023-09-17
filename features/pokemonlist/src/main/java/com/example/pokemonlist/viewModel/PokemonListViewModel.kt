package com.example.pokemonlist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.constants.Constants.Companion.GENERIC_ERROR
import com.example.core.constants.Constants.Companion.LIMIT_SIZE
import com.example.core.constants.Constants.Companion.ZERO
import com.example.core.resources.Resource
import com.example.domain.use_cases.GetPokemonList
import com.example.pokemonlist.model.mapper.PokemonListItemUIMapper
import com.example.pokemonlist.state.PokemonListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonList: GetPokemonList
) : ViewModel() {

    private val _viewState = MutableStateFlow<PokemonListState>(PokemonListState.Loading)
    val viewState: StateFlow<PokemonListState> = _viewState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PokemonListState.Loading
        )

    init {
        getPokemons()
    }

    private fun getPokemons() = viewModelScope.launch {
        getPokemonList(LIMIT_SIZE, ZERO)
            .collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _viewState.value = PokemonListState.Loading
                    }

                    is Resource.Success -> {
                        _viewState.value = PokemonListState.PokemonList(
                            PokemonListItemUIMapper().toPokemonUIList(
                                result.data ?: emptyList()
                            )
                        )
                    }

                    is Resource.Error -> {
                        _viewState.value = PokemonListState.Error(
                            result.message ?: GENERIC_ERROR
                        )
                    }
                }
            }
    }

}