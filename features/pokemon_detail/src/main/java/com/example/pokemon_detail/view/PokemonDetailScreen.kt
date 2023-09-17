package com.example.pokemon_detail.view

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.core.constants.Constants.Companion.EMPTY_STRING
import com.example.core.constants.Constants.Companion.GENERIC_ERROR
import com.example.pokemon_detail.model.PokemonDetailUI
import com.example.pokemon_detail.model.TypeUI
import com.example.pokemon_detail.state.PokemonDetailState
import com.example.pokemon_detail.view.util.assignColorToStat
import com.example.pokemon_detail.viewModel.PokemonDetailViewModel

@Composable
fun PokemonDetailScreen(
    navController: NavController,
    pokemonName: String,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemon(pokemonName)
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    when (val viewState = state.value) {
        is PokemonDetailState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(100.dp)
                )
            }
        }

        is PokemonDetailState.PokemonDetail -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (viewState.pokemonDetail != null) {
                    PokemonDetailTopSection(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.25f),
                        contentAlignment = Alignment.TopStart,
                        viewState.pokemonDetail
                    )
                    PokemonDetailSection(viewState.pokemonDetail)
                } else {
                    Error()
                }
            }
        }

        is PokemonDetailState.Error -> {
            Error()
        }
    }

}

@Composable
private fun Error() {
    Toast.makeText(
        LocalContext.current,
        GENERIC_ERROR, Toast.LENGTH_LONG
    ).show()
}

@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment,
    pokemonDetailUI: PokemonDetailUI,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
) {
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        Color.Transparent
                    )
                )
            )
            .padding(bottom = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(
                id = com.example.core.R.string.arrow_back_description
            ),
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(x = 16.dp, y = 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            pokemonDetailUI.imageUI.frontDefault.let {
                AsyncImage(
                    model = it,
                    contentDescription = stringResource(
                        id = com.example.core.R.string.pokemon_image_description
                    ),
                    modifier = Modifier
                        .size(pokemonImageSize)
                        .offset(y = topPadding)
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "${pokemonDetailUI.number} ${pokemonDetailUI.name.capitalize(Locale.current)}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = if (isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.surface
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }

}

@Composable
fun PokemonDetailSection(
    pokemon: PokemonDetailUI
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        PokemonTypeSection(types = pokemon.typesUI)
        PokemonBaseStatsSection(pokemon = pokemon)
    }
}


@Composable
fun PokemonTypeSection(types: List<TypeUI>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground)
                    .height(35.dp)
            ) {
                Text(
                    text = type.type.name.capitalize(Locale.current),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PokemonStatSection(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 30.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        ), label = EMPTY_STRING
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .height(height)
                .padding(start = 10.dp)
        ) {
            Box {
                Text(
                    text = statName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF5A5959)
                } else {
                    Color.LightGray
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(height)
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    text = (curPercent.value * statMaxValue).toInt().toString(),
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Composable
fun PokemonBaseStatsSection(
    pokemon: PokemonDetailUI,
    animDelayPerItem: Int = 100
) {
    val maxBaseStat = remember {
        pokemon.statsUI.maxOf { it.baseStat.toFloat() }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
    ) {
        for (item in pokemon.statsUI.indices) {
            val stat = pokemon.statsUI[item]
            PokemonStatSection(
                statName = stat.statXUI.name.capitalize(Locale.current),
                statValue = stat.baseStat,
                statMaxValue = maxBaseStat.toInt(),
                statColor = assignColorToStat(stat.statXUI),
                animDelay = item * animDelayPerItem
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

