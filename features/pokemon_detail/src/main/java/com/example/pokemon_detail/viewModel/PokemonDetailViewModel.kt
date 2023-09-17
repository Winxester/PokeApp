package com.example.pokemon_detail.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.constants.Constants.Companion.GENERIC_ERROR
import com.example.core.resources.Resource
import com.example.domain.model.PokemonDetail
import com.example.domain.use_cases.GetPokemonDetail
import com.example.pokemon_detail.model.mapper.PokemonDetailUIMapper
import com.example.pokemon_detail.state.PokemonDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetail: GetPokemonDetail
) : ViewModel() {

    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Loading)
    val state: StateFlow<PokemonDetailState> = _state.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PokemonDetailState.Loading
        )

    fun getPokemon(name: String) = viewModelScope.launch {
        getPokemonDetail(name)
            .collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = PokemonDetailState.Loading
                    }

                    is Resource.Success -> {
                        _state.value = PokemonDetailState.PokemonDetail(
                            PokemonDetailUIMapper().toPokemonDetailUI(
                                result.data as PokemonDetail
                            )
                        )
                    }

                    is Resource.Error -> {
                        _state.value = PokemonDetailState.Error(
                            result.message ?: GENERIC_ERROR
                        )
                    }
                }
            }
    }

}