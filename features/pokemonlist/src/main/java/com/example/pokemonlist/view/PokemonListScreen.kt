package com.example.pokemonlist.view


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.core.constants.Constants.Companion.GENERIC_ERROR
import com.example.core.constants.Constants.Companion.TWO
import com.example.core.navigation.Navigation.POKEMON_DETAIL_SCREEN
import com.example.pokemonlist.R
import com.example.pokemonlist.model.PokemonListItemUI
import com.example.pokemonlist.state.PokemonListState
import com.example.pokemonlist.viewModel.PokemonListViewModel


@Composable
fun PokemonListScreen(
    navController: NavController,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(
                    id = R.drawable.pokemon_logo
                ),
                contentDescription = stringResource(id = com.example.core.R.string.pokemon_logo_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController)
        }
    }
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    when (val viewState = state) {
        is PokemonListState.PokemonList -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(TWO),
                modifier = Modifier
                    .padding(16.dp, 0.dp, 16.dp, 0.dp)
            ) {
                if (viewState.pokemonListUI.isNotEmpty()) {
                    items(viewState.pokemonListUI.size) { item ->
                        PokemonItem(
                            pokemonListItemUI = viewState.pokemonListUI[item],
                            navController = navController,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
            if (viewState.pokemonListUI.isEmpty()) {
                Error()
            }
        }

        is PokemonListState.Error -> {
            Error()
        }

        else -> Unit
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
fun PokemonItem(
    pokemonListItemUI: PokemonListItemUI,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "$POKEMON_DETAIL_SCREEN/${pokemonListItemUI.name}"
                )
            }
    ) {
        Column {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemonListItemUI.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(
                    id = com.example.core.R.string.pokemon_image_description
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(120.dp)
                    .align(CenterHorizontally),
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.scale(0.5f)
                    )
                }
            )
            Text(
                text = pokemonListItemUI.name.capitalize(Locale.current),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
