package com.example.pokemonapp.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.constants.Constants.Companion.EMPTY_STRING
import com.example.core.navigation.Navigation.POKEMON_DETAIL_SCREEN
import com.example.core.navigation.Navigation.POKEMON_LIST_SCREEN
import com.example.pokemon_detail.view.PokemonDetailScreen
import com.example.pokemon_detail.ui.theme.PokemonAppCustomTheme
import com.example.pokemonlist.view.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonAppCustomTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = POKEMON_LIST_SCREEN
                ) {
                    composable(POKEMON_LIST_SCREEN) {
                        PokemonListScreen(navController = navController)
                    }
                    composable("$POKEMON_DETAIL_SCREEN/{pokemonName}") {
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                        PokemonDetailScreen(
                            navController = navController,
                            pokemonName = pokemonName?.toLowerCase(Locale.current)
                                ?: EMPTY_STRING
                        )
                    }
                }
            }
        }
    }
}

