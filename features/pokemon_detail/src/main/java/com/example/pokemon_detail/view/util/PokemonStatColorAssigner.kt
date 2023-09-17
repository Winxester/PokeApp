package com.example.pokemon_detail.view.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.example.pokemon_detail.model.StatXUI
import com.example.pokemon_detail.ui.theme.AtkColor
import com.example.pokemon_detail.ui.theme.DefColor
import com.example.pokemon_detail.ui.theme.HPColor
import com.example.pokemon_detail.ui.theme.SpAtkColor
import com.example.pokemon_detail.ui.theme.SpDefColor
import com.example.pokemon_detail.ui.theme.SpdColor

fun assignColorToStat(stat: StatXUI): Color =
    when (stat.name.toLowerCase(Locale.current)) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
